package com.test.sniptcode.api;

import com.test.sniptcode.model.Model_2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Model_2Service {
    String MODEL_2 = "Tacgia";

    @GET(MODEL_2)
    Call<Model_2[]> getAllModel_2(); // Get a list of all Model_2

    @GET(MODEL_2 + "/{id}")
    Call<Model_2> getAllModel_2s(@Path("id") Object id); // Get details of a single Model_2 by ID

    @POST(MODEL_2)
    Call<Model_2> createModel_2s(@Body Model_2 model_2); // Create a new Model_2

    @PUT(MODEL_2 + "/{id}")
    Call<Model_2> updateModel_2s(@Path("id") Object id, @Body Model_2 model_2); // Update Model_2 details by ID

    @DELETE(MODEL_2 + "/{id}")
    Call<Void> deleteModel_2s(@Path("id") Object id); // Delete Model_2 by ID
}
