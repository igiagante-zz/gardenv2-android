package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.PlagueRealmRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public class PlagueRepositoryManager {

    private Context mContext;

    @Inject
    public PlagueRepositoryManager(Context context) {
        this.mContext = context;
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {
        PlagueRealmRepository plagueRealmRepository = new PlagueRealmRepository(mContext);
        return plagueRealmRepository.query(specification);
    }
}
