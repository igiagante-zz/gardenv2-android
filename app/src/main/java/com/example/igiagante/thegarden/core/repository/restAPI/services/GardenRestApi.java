package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.network.Message;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public interface GardenRestApi {

    @GET("gardens/{id}")
    Observable<Garden> getGarden(@Path("id") String id);

    @GET("gardens/user/{username}")
    Observable<List<Garden>> getGardensByUserName(@Path("username") String username);

    @GET("gardens/")
    Observable<List<Garden>> getGardens();

    @POST("gardens/")
    Observable<Garden> createGarden(@Body Garden body);

    @PUT("gardens/{id}")
    Observable<Garden> updateGarden(@Path("id") String id, @Body Garden body);

    @DELETE("gardens/{id}/{userId}")
    Observable<Response<Message>> deleteGarden(@Path("id") String id, @Path("userId") String userId);

}
