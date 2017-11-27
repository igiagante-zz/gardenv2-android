package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiPlantRepository;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
public class PlantRepositoryManager extends
        BaseRepositoryManager<Plant, PlantRealm, PlantRealmRepository, RestApiPlantRepository> {

    @Inject
    public PlantRepositoryManager(Context context, Session session) {
        super(context, new PlantRealmRepository(context), new RestApiPlantRepository(context, session));
    }
}
