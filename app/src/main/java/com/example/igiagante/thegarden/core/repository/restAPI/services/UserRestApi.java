package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.restAPI.authentication.RestAuthApi;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public interface UserRestApi {

    @GET("users/{username}")
    Observable<User> getUser(@Path("username") String userName);

    @FormUrlEncoded
    @POST("users/refreshToken")
    Observable<Response<RestAuthApi.InnerResponse>> refreshToken(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("users/signup")
    Observable<Response<RestAuthApi.InnerResponse>> createUser(@Field("username") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST("users/login")
    Observable<Response<RestAuthApi.InnerResponse>> loginUser(@Field("username") String userName, @Field("password") String password);
}
