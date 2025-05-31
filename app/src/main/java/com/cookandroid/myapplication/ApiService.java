package com.cookandroid.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
        @POST("/api/station")
        Call<Void> sendStation(@Body RequestData data);

        @GET("/api/status")
        Call<StatusResponse> getElevatorStatusForElevator(@Query("elevator") String elevator);
}
