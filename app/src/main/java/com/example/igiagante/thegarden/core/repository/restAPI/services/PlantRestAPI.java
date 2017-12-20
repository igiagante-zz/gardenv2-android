package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.network.Message;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * @author Ignacio Giagante, on 19/4/16.
 */
public interface PlantRestAPI {

    @GET("plants/{id}")
    Observable<Plant> getPlant(@Path("id") String id);

    @GET("plants/")
    Observable<List<Plant>> getPlants();

    @POST("plants/")
    Observable<Plant> createPlant(@Body RequestBody body);

    @PUT("plants/{id}")
    Observable<Plant> updatePlant(@Path("id") String id, @Body RequestBody body);

    @DELETE("plants/{id}")
    Observable<Response<Message>> deletePlant(@Path("id") String id);
}
