package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.SensorTempRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.specification.plant.PlantByNameSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiPlantRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiSensorTempRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

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
