package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.repository.realm.IrrigationRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiIrrigationRepository;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public class IrrigationRepositoryManager extends
        BaseRepositoryManager<Irrigation, IrrigationRealmRepository, RestApiIrrigationRepository> {

    @Inject
    public IrrigationRepositoryManager(Context context, Session session) {
        super(context, new IrrigationRealmRepository(context), new RestApiIrrigationRepository(context, session));
    }
}
