package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public interface AttributeRestApi {

    @GET("attributes/")
    Observable<List<Attribute>> getAttributes();
}
