package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
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
public interface IrrigationRestApi {

    @GET("irrigations/{id}")
    Observable<Irrigation> getIrrigation(@Path("id") String id);

    @GET("irrigations/")
    Observable<List<Irrigation>> getIrrigations();

    @POST("irrigations/")
    Observable<Irrigation> createIrrigation(@Body RequestBody body);

    @PUT("irrigations/{id}")
    Observable<Irrigation> updateIrrigation(@Path("id") String id, @Body RequestBody body);

    @DELETE("irrigations/{id}")
    Observable<Response<Message>> deleteIrrigation(@Path("id") String id);
}
