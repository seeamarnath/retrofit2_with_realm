package com.amarnath.deliverytest.utils.webservice;

import com.amarnath.deliverytest.App;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;

public class DeliveryAPIManager {

    private static DeliveryAPIManager instance;

    public static synchronized DeliveryAPIManager getInstance() {

        if (instance == null)
            instance = new DeliveryAPIManager();
        return instance;
    }

    public void getDeliveries(int offset, int limit, Callback<JsonElement> callback) {

        DeliveryService service = App.getInstance().getRESTClient().create(DeliveryService.class);
        Call<JsonElement> call = service.getDeliveries(offset, limit);
        call.enqueue(callback);
    }
}
