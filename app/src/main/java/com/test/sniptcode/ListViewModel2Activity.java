package com.test.sniptcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.location.Address;
import android.location.Geocoder;
import androidx.annotation.NonNull;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.test.sniptcode.api.ModelRepository;
import com.test.sniptcode.api.Model_2Service;
import com.test.sniptcode.model.Model_1;
import com.test.sniptcode.model.Model_2;
import com.test.sniptcode.model.Model_2Adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ListViewModel2Activity extends AppCompatActivity implements View.OnClickListener {

    Model_2Service
            model_2Service; // Service to interact with the API
    ListView listView2;
    
    Model_2Adapter model_2Adapter;

    List<Model_2> model_2List = new ArrayList<>(); // Holds the model_2 data

    Model_2 selectedmodel_2; // Stores the currently selected model_2

    EditText
            edText5_list2,
            edText6_list2,
            edText7_list2,
            edText8_list2,
            edText9_list2;

    Button
            buttonEdit,
            buttonDelete,
            buttonReturn,
            buttonMap;


    private MapView mapView;
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view_model2);


        // Linking UI elements with the code
        listView2 = findViewById(R.id.listView2);
        model_2Service = ModelRepository.getModel_2Service(); // Get service model 2 to interact with API

        edText5_list2 = findViewById(R.id.edText5_list2); // id Tac Gia
        edText6_list2 = findViewById(R.id.edText6_list2); // sdt
        edText7_list2 = findViewById(R.id.edText7_list2); // Email
        edText8_list2 = findViewById(R.id.edText8_list2); // Dia Chi
        edText9_list2 = findViewById(R.id.edText9_list2); // Ten Tac Gia


        buttonEdit   = findViewById(R.id.buttonEdit);   // Sua Data
        buttonDelete = findViewById(R.id.buttonDelete); // Xoa Data
        buttonReturn = findViewById(R.id.buttonReturn); // Quay Lai
        buttonMap = findViewById(R.id.buttonMap); // Quay Lai

        buttonEdit.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonReturn.setOnClickListener(this);
        buttonMap.setOnClickListener(this);

        listAllModel_2();  // Load the list of Model_2



    }

    // Handles button clicks
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonEdit) {
            updateModel(); // Handle update
        } else if (view.getId() == R.id.buttonDelete) {
            removeModel(); // Handle remove
        } else if (view.getId() == R.id.buttonReturn) {
            returnToMainActivity();
        } else if (view.getId() == R.id.buttonMap) {
            goToMapActivity();
        }
    }

    // Returns to the main activity screen
    private void returnToMainActivity() {
        Intent intent = new Intent(ListViewModel2Activity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish this activity
    }

    // go to map  screen
    private void goToMapActivity() {
        String address = edText8_list2.getText().toString();
        if (!address.isEmpty()) {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("address", address); // Pass the address to MapActivity
            startActivity(intent);
        } else {
            // Optionally, show a message if the address is empty
            Toast.makeText(this, "Please enter an address", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    //     Method to list all trainees by calling API
    private void listAllModel_2() {
        
        try {
            Call<Model_2[]> call = model_2Service.getAllModel_2(); // API call to get all Model_2

            call.enqueue(new Callback<Model_2[]>() {
                @Override
                public void onResponse(Call<Model_2[]> call, Response<Model_2[]> response) {
                    if (!response.isSuccessful()) {
                        Log.d("API Error", "Error code: " + response.code());
                        Toast.makeText(ListViewModel2Activity.this, "Failed to load Model_2, code: " + response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    Model_2[] model_2s = response.body(); // Get the response body
                    if (model_2s == null) {
                        Log.d("API Error", "Empty response");
                        return;
                    }

                    // Add trainees to list and update UI with adapter
                    model_2List.addAll(Arrays.asList(model_2s));
                    model_2Adapter = new Model_2Adapter(ListViewModel2Activity.this, model_2List, model_2 -> loadModel_2Info(model_2));
                    listView2.setAdapter(model_2Adapter);
                }

                @Override
                public void onFailure(Call<Model_2[]> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(ListViewModel2Activity.this, "Failed to load Model_2" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch(Exception ex) {
            Log.d("Loi", ex.getMessage());
        }

    }

    // Loads selected trainee details into the input fields
    private void loadModel_2Info(Model_2 model_2) {
        selectedmodel_2 = model_2;
        edText5_list2.setText(String.valueOf(model_2.getIDTacgia()));// Id Tac Gia
        edText6_list2.setText(model_2.getDienthoai());// sdt
        edText7_list2.setText(model_2.getEmail());// Email
        edText8_list2.setText(model_2.getDiachi());// Dia Chi
        edText9_list2.setText(model_2.getTenTacgia());// Ten Tac Gia

    }
    // Update selected trainee's info
    private void updateModel() {
        String phone      = edText6_list2.getText().toString();  // sdt
        String email      = edText7_list2.getText().toString();  // Email
        String address    = edText8_list2.getText().toString();  // Dia Chi
        String nameAuthor = edText9_list2.getText().toString();  // Ten Tac Gia

        if (selectedmodel_2 != null) {
            // Set new details to trainee
            selectedmodel_2.setDienthoai(phone);
            selectedmodel_2.setEmail(email);
            selectedmodel_2.setDiachi(address);
            selectedmodel_2.setTenTacgia(nameAuthor);

            Call<Model_2> call = model_2Service.updateModel_2s(selectedmodel_2.getIDTacgia(),selectedmodel_2);
            call.enqueue(new Callback<Model_2>() {
                @Override
                public void onResponse(Call<Model_2> call, Response<Model_2> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ListViewModel2Activity.this, "Update successful", Toast.LENGTH_LONG).show();

                        model_2Adapter.notifyDataSetChanged();

                        clearFields(); // Clear the input fields after update
                    }
                }

                @Override
                public void onFailure(Call<Model_2> call, Throwable t) {
                    Toast.makeText(ListViewModel2Activity.this, "Update failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    // Remove the selected trainee
    private void removeModel() {
        if (selectedmodel_2 != null) {
            Call<Void> call = model_2Service.deleteModel_2s(selectedmodel_2.getIDTacgia());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        model_2List.remove(selectedmodel_2); // Remove from local list
                        model_2Adapter.notifyDataSetChanged(); // Update the UI

                        Toast.makeText(ListViewModel2Activity.this, "Remove successful", Toast.LENGTH_LONG).show();
                        clearFields(); // Clear the input fields after remove
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ListViewModel2Activity.this, "Remove failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    // Clear the input fields after saving or returning to the main screen
    private void clearFields() {
        edText5_list2.setText("");
        edText6_list2.setText("");
        edText7_list2.setText("");
        edText8_list2.setText("");
        edText9_list2.setText("");
    }


}