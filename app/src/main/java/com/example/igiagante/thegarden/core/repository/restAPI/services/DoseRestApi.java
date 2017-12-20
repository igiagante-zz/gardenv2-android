package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
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
public interface DoseRestApi {

    @GET("doses/{id}")
    Observable<Dose> getDose(@Path("id") String id);

    @GET("doses/")
    Observable<List<Dose>> getDoses();

    @POST("doses/")
    Observable<Dose> createDose(@Body RequestBody body);

    @PUT("doses/{id}")
    Observable<Dose> updateDose(@Path("id") String id, @Body RequestBody body);

    @DELETE("doses/{id}")
    Observable<Response<Message>> deleteIrrigation(@Path("id") String id);
}
