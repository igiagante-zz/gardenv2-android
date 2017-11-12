package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDao;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDaoRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author Ignacio Giagante, on 30/5/16.
 */
public class FlavorRepositoryManager {

    private FlavorDaoRepository daoRepository;

    @Inject
    public FlavorRepositoryManager(Context context){
        daoRepository = new FlavorDaoRepository(new FlavorDao(context));
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {
        return daoRepository.query(specification);
    }
}
