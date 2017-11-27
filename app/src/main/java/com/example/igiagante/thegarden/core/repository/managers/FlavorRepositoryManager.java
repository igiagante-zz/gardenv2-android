package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.FlavorRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.FlavorRealm;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiFlavorRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class FlavorRepositoryManager
        extends BaseRepositoryManager<Flavor, FlavorRealm, FlavorRealmRepository, RestApiFlavorRepository>{

    @Inject
    public FlavorRepositoryManager(Context context) {
        super(context, new FlavorRealmRepository(context), new RestApiFlavorRepository());
    }
}
