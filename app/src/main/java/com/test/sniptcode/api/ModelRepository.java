package com.test.sniptcode.api;

import com.test.sniptcode.model.Model_1;
import com.test.sniptcode.model.Model_2;

public class ModelRepository {
    public static Model_1Service getModel_1Service() {
        return APIClient.getClient().create(Model_1Service.class); // Creates the service from APIClient
    }
    public static Model_2Service getModel_2Service() {
        return APIClient.getClient().create(Model_2Service.class); // Creates the service from APIClient
    }
}
