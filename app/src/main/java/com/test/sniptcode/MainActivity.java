package com.test.sniptcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.test.sniptcode.api.ModelRepository;
import com.test.sniptcode.api.Model_1Service;
import com.test.sniptcode.api.Model_2Service;
import com.test.sniptcode.model.Model_1;
import com.test.sniptcode.model.Model_2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Model_1Service
            model_1Service; // Service to interact with the API
    Model_2Service
            model_2Service; // Service to interact with the API

    EditText
            edtext1,
            edtext2,
            edtext3,
            edtext4,
            edtext5,
            edtext6,
            edtext7,
            edtext8;

    Button
            buttonSaveData,
            buttonFullList,
            buttonFullList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model_1Service = ModelRepository.getModel_1Service(); // Get service model 1 to interact with API
        model_2Service = ModelRepository.getModel_2Service(); // Get service model 2 to interact with API

        edtext1 = (EditText) findViewById(R.id.edtext1);
        edtext2 = (EditText) findViewById(R.id.edtext2);
        edtext3 = (EditText) findViewById(R.id.edtext3);
        edtext4 = (EditText) findViewById(R.id.edtext4);
        edtext5 = (EditText) findViewById(R.id.edtext5);
        edtext6 = (EditText) findViewById(R.id.edtext6);
        edtext7 = (EditText) findViewById(R.id.edtext7);
        edtext8 = (EditText) findViewById(R.id.edtext8);


        buttonSaveData = (Button) findViewById(R.id.buttonSaveData);
        buttonFullList = (Button) findViewById(R.id.buttonFullList);
        buttonFullList2 = (Button) findViewById(R.id.buttonFullList2);

        // Set click listeners for the buttons
        buttonSaveData.setOnClickListener(this);
        buttonFullList.setOnClickListener(this);
        buttonFullList2.setOnClickListener(this);

    }

    // Handle button clicks
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonSaveData) {
            save(); // Save trainee data
        } else if (view.getId() == R.id.buttonFullList) {
            directToFullList(); // Go to the full list of Model_1
        } else if (view.getId() == R.id.buttonFullList2) {
            directToFullList2(); // Go to the full list of Model_1
        }
    }

    // Redirect to ListViewActivity to show the list of Model_1
    private void directToFullList() {
        Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
        startActivity(intent); // Start the ListViewActivity
        finish(); // Finish this activity
    }

    // Redirect to ListViewActivity to show the list of Model_1
    private void directToFullList2() {
        Intent intent = new Intent(MainActivity.this, ListViewModel2Activity.class);
        startActivity(intent); // Start the ListViewActivity
        finish(); // Finish this activity
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    // Method to save the Model data
    private void save() {
        // Get input values for Model_1
        String nameBook = edtext1.getText().toString();
        String datePublish = edtext2.getText().toString();
        String categoryBook = edtext3.getText().toString();
        String idAuthor = edtext4.getText().toString();

        // Check if Model_1 fields are filled
        boolean isModel1Filled = !nameBook.isEmpty() && !datePublish.isEmpty() && !categoryBook.isEmpty() && !idAuthor.isEmpty();

        // Get input values for Model_2
        String nameAuthor = edtext5.getText().toString();
        String email = edtext6.getText().toString();
        String address = edtext7.getText().toString();
        String phone = edtext8.getText().toString();

        // Check if Model_2 fields are filled
        boolean isModel2Filled = !nameAuthor.isEmpty() && !email.isEmpty() && !address.isEmpty() && !phone.isEmpty();

        // Flags to track if both models are saved
        final boolean[] model1Saved = {false};
        final boolean[] model2Saved = {false};

        // Save Model_1 if filled
        if (isModel1Filled) {
            Model_1 model_1 = new Model_1(nameBook, datePublish, categoryBook, idAuthor);

            try {
                Call<Model_1> call = model_1Service.createModel_1s(model_1);
                call.enqueue(new Callback<Model_1>() {
                    @Override
                    public void onResponse(Call<Model_1> call, Response<Model_1> response) {
                        if (response.body() != null) {
                            model1Saved[0] = true;
                            if (model1Saved[0] && model2Saved[0]) {
                                Toast.makeText(MainActivity.this, "Lưu dữ liệu sách và tác giả thành công", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Lưu Sách Thành Công!", Toast.LENGTH_LONG).show();
                            }
                            clearFields();
                        }
                    }

                    @Override
                    public void onFailure(Call<Model_1> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Lưu Sách Thất Bại!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception ex) {
                Log.d("Lỗi", ex.getMessage());
            }
        }

        // Save Model_2 if filled
        if (isModel2Filled) {
            Model_2 model_2 = new Model_2(nameAuthor, email, address, phone);

            try {
                Call<Model_2> call = model_2Service.createModel_2s(model_2);
                call.enqueue(new Callback<Model_2>() {
                    @Override
                    public void onResponse(Call<Model_2> call, Response<Model_2> response) {
                        if (response.body() != null) {
                            model2Saved[0] = true;
                            if (model1Saved[0] && model2Saved[0]) {
                                Toast.makeText(MainActivity.this, "Lưu dữ liệu sách và tác giả thành công", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Lưu Tác Giả Thành Công!", Toast.LENGTH_LONG).show();
                            }
                            clearFields();
                        }
                    }

                    @Override
                    public void onFailure(Call<Model_2> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Lưu Tác Giả Thất Bại!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception ex) {
                Log.d("Lỗi", ex.getMessage());
            }
        }

        // Show a message if no data was entered for either model
        if (!isModel1Filled && !isModel2Filled) {
            Toast.makeText(this, "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    // Clear the input fields after saving or returning to the main screen
    private void clearFields() {
       edtext1.setText("");
       edtext2.setText("");
       edtext3.setText("");
       edtext4.setText("");
       edtext5.setText("");
       edtext6.setText("");
       edtext7.setText("");
       edtext8.setText("");
    }
}