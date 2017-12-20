package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.network.Message;

import java.util.List;

import okhttp3.RequestBody;
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
public interface NutrientRestApi {

    @GET("nutrients/{id}")
    Observable<Nutrient> getNutrient(@Path("id") String id);

    @GET("nutrients/")
    Observable<List<Nutrient>> getNutrients();

    @GET("nutrients/user/{username}")
    Observable<List<Nutrient>> getNutrientsByUserName(@Path("username") String username);

    @POST("nutrients/")
    Observable<Nutrient> createNutrient(@Body RequestBody body);

    @PUT("nutrients/{id}")
    Observable<Nutrient> updateNutrient(@Path("id") String id, @Body RequestBody body);

    @DELETE("nutrients/{id}")
    Observable<Response<Message>> deleteNutrient(@Path("id") String id);
}
