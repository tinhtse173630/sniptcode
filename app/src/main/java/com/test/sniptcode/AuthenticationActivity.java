package com.test.sniptcode;

import android.content.Intent;
import android.credentials.Credential;
import android.os.Bundle;
import android.security.identity.IdentityCredential;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.ThreadLocalRandom;

public class AuthenticationActivity extends AppCompatActivity {

    private static final int REQ_ONE_TAP = 2;

    private AppCompatButton btnRegister, btnLogin, btnGoogle;
    private EditText editTextTextEmailAddress, editTextPassword;

    private FirebaseAuth mAuth;
    private  GoogleSignInClient gClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);
        init();


        btnRegister.setOnClickListener(v -> {
            signUpUser(editTextTextEmailAddress.getText().toString(), editTextPassword.getText().toString());
        });

        btnLogin.setOnClickListener(v -> {
            signInUser(editTextTextEmailAddress.getText().toString(), editTextPassword.getText().toString());
        });
        btnGoogle.setOnClickListener(v -> {
            signInWithGoogle();
        });
    }

    private void signUpUser(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()){
            Toast.makeText(AuthenticationActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            user.sendEmailVerification()
                                    .addOnCompleteListener(emailTask -> {
                                        if (emailTask.isSuccessful()) {
                                            //Process with backend
                                            Toast.makeText(AuthenticationActivity.this, "Successfully to send verification email.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AuthenticationActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(AuthenticationActivity.this, "Authentication failed. (This email might be used)", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void signInUser(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()){
            Toast.makeText(AuthenticationActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user.isEmailVerified()){
                            Toast.makeText(AuthenticationActivity.this, "Successfully Logined", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AuthenticationActivity.this, "Please verify your email first", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(AuthenticationActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void signInWithGoogle(){
        Intent intent = gClient.getSignInIntent();
        activityResultLauncher.launch(intent);
    }
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == RESULT_OK){
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(o.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    //AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    firebaseAuthWithGoogle(signInAccount.getIdToken());
                } catch (ApiException e){
                    Toast.makeText(AuthenticationActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-in successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user.isEmailVerified()){
                            Toast.makeText(AuthenticationActivity.this, "Successfully Logined", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AuthenticationActivity.this, "Please verify your email first", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Sign-in fails
                        Toast.makeText(AuthenticationActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void init() {
        mAuth = FirebaseAuth.getInstance();
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogle = findViewById(R.id.btnGoogle);

        GoogleSignInOptions options =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        gClient = GoogleSignIn.getClient(this,options);

    }

}