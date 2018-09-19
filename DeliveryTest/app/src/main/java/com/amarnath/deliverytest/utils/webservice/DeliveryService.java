package com.amarnath.deliverytest.utils.webservice;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface DeliveryService {

    @Headers({"Content-Type: application/json"})

    @GET("/deliveries")
    Call<JsonElement> getDeliveries(
            @Query("offset") int offset,
            @Query("limit") int limit
    );

}
