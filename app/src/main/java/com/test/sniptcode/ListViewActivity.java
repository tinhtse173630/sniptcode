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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.test.sniptcode.api.ModelRepository;
import com.test.sniptcode.api.Model_1Service;
import com.test.sniptcode.api.Model_2Service;
import com.test.sniptcode.model.Model_1;
import com.test.sniptcode.model.Model_1Adapter;
import com.test.sniptcode.model.Model_2;
import com.test.sniptcode.model.Model_2Adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewActivity extends AppCompatActivity implements View.OnClickListener {

    Model_1Service
            model_1Service; // Service to interact with the API
    ListView listView;

    Model_1Adapter model_1Adapter;

    List<Model_1> model_1List = new ArrayList<>(); // Holds the model_1 data

    Model_1 selectedmodel_1; // Stores the currently selected model_1

    EditText
            edText1_list,
            edText2_list,
            edText3_list,
            edText4_list,
            edText5_list;

    Button
           buttonEdit,
           buttonDelete,
           buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view);


        // Linking UI elements with the code
        listView = findViewById(R.id.listView);
        model_1Service = ModelRepository.getModel_1Service(); // Get service model 1 to interact with API

        edText1_list = findViewById(R.id.edText1_list); // Id Book
        edText2_list = findViewById(R.id.edText2_list); // Ngay XB
        edText3_list = findViewById(R.id.edText3_list); // The loai
        edText4_list = findViewById(R.id.edText4_list); // Ten Sach
        edText5_list = findViewById(R.id.edText5_list); // Id Tac Gia

        buttonEdit   = findViewById(R.id.buttonEdit);   // Sua Data
        buttonDelete = findViewById(R.id.buttonDelete); // Xoa Data
        buttonReturn = findViewById(R.id.buttonReturn); // Quay Lai

        buttonEdit.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonReturn.setOnClickListener(this);

        listAllModel_1();  // Load the list of Model

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
        }
    }

    // Returns to the main activity screen
    private void returnToMainActivity() {
        Intent intent = new Intent(ListViewActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish this activity
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

//     Method to list all trainees by calling API
    private void listAllModel_1() {
        try {
            Call<Model_1[]> call = model_1Service.getAllModel_1(); // API call to get all trainees

            call.enqueue(new Callback<Model_1[]>() {
                @Override
                public void onResponse(Call<Model_1[]> call, Response<Model_1[]> response) {
                    if (!response.isSuccessful()) {
                        Log.d("API Error", "Error code: " + response.code());
                        Toast.makeText(ListViewActivity.this, "Failed to load Model_1, code: " + response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    Model_1[] model_1s = response.body(); // Get the response body
                    if (model_1s == null) {
                        Log.d("API Error", "Empty response");
                        return;
                    }

                    // Add trainees to list and update UI with adapter
                    model_1List.addAll(Arrays.asList(model_1s));
                    model_1Adapter = new Model_1Adapter(ListViewActivity.this, model_1List, model_1 -> loadModel_1Info(model_1));
                    listView.setAdapter(model_1Adapter);
                }

                @Override
                public void onFailure(Call<Model_1[]> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(ListViewActivity.this, "Failed to load Model_1" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch(Exception ex) {
            Log.d("Loi", ex.getMessage());
        }

    }


    // Loads selected trainee details into the input fields
    private void loadModel_1Info(Model_1 model_1) {
       selectedmodel_1 = model_1;
       edText1_list.setText(String.valueOf(model_1.getIDsach())); // Id Book
       edText2_list.setText(model_1.getNgayXB());// Ngay XB
       edText3_list.setText(model_1.getTheloai());// The loai
       edText4_list.setText(model_1.getTensach());// Ten Sach
       edText5_list.setText(model_1.getIdTacgia());// id Tac Gia
    }

    // Update selected trainee's info
    private void updateModel() {
        String datePublish   = edText2_list.getText().toString();  // Ngay XB
        String catagory      = edText3_list.getText().toString();  // The loai
        String nameBook      = edText4_list.getText().toString();  // Ten Sach
        String idAuthor      = edText5_list.getText().toString();  // id Tac Gia

        if (selectedmodel_1 != null) {
            // Set new details to trainee
            selectedmodel_1.setNgayXB(datePublish);
            selectedmodel_1.setTheloai(catagory);
            selectedmodel_1.setTensach(nameBook);
            selectedmodel_1.setIdTacgia(idAuthor);

            Call<Model_1> call = model_1Service.updateModel_1s(selectedmodel_1.getIDsach(), selectedmodel_1);
            call.enqueue(new Callback<Model_1>() {
                @Override
                public void onResponse(Call<Model_1> call, Response<Model_1> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ListViewActivity.this, "Update successful", Toast.LENGTH_LONG).show();

                        model_1Adapter.notifyDataSetChanged();

                        clearFields(); // Clear the input fields after update
                    }
                }

                @Override
                public void onFailure(Call<Model_1> call, Throwable t) {
                    Toast.makeText(ListViewActivity.this, "Update failed", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    // Remove the selected trainee
    private void removeModel() {
        if (selectedmodel_1 != null) {
            Call<Void> call = model_1Service.deleteModel_1s(selectedmodel_1.getIDsach());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        model_1List.remove(selectedmodel_1); // Remove from local list
                        model_1Adapter.notifyDataSetChanged(); // Update the UI

                        Toast.makeText(ListViewActivity.this, "Remove successful", Toast.LENGTH_LONG).show();
                        clearFields(); // Clear the input fields after remove
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ListViewActivity.this, "Remove failed", Toast.LENGTH_LONG).show();
                }
            });
        }
        
    }

    // Clear the input fields after saving or returning to the main screen
    private void clearFields() {
        edText1_list.setText("");
        edText2_list.setText("");
        edText3_list.setText("");
        edText4_list.setText("");
        edText5_list.setText("");
    }


}
