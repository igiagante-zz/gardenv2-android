package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.realm.NutrientRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiNutrientRepository;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientRepositoryManager extends
        BaseRepositoryManager<Nutrient, NutrientRealmRepository, RestApiNutrientRepository> {

    @Inject
    public NutrientRepositoryManager(Context context, Session session) {
        super(context, new NutrientRealmRepository(context), new RestApiNutrientRepository(context, session));
    }
}
