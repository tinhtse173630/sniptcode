package com.test.sniptcode.api;

import com.test.sniptcode.model.Model_1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Model_1Service {
    String MODEL_1 = "Sach";
    
    @GET(MODEL_1)
    Call<Model_1[]> getAllModel_1(); // Get a list of all Model_1

    @GET(MODEL_1 + "/{id}")
    Call<Model_1> getAllModel_1s(@Path("id") Object id); // Get details of a single Model_1 by ID

    @POST(MODEL_1)
    Call<Model_1> createModel_1s(@Body Model_1 model_1); // Create a new Model_1

    @PUT(MODEL_1 + "/{id}")
    Call<Model_1> updateModel_1s(@Path("id") Object id, @Body Model_1 model_1); // Update Model_1 details by ID

    @DELETE(MODEL_1 + "/{id}")
    Call<Void> deleteModel_1s(@Path("id") Object id); // Delete Model_1 by ID

}
