package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.realm.PlagueRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiPlagueRepository;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public class PlagueRepositoryManager
        extends BaseRepositoryManager<Plague, PlagueRealm, PlagueRealmRepository, RestApiPlagueRepository> {

    @Inject
    public PlagueRepositoryManager(Context context) {
        super(context, new PlagueRealmRepository(context), new RestApiPlagueRepository());
    }
}
