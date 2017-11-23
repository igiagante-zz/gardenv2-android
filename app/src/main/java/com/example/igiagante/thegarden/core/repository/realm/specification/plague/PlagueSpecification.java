package com.example.igiagante.thegarden.core.repository.realm.specification.plague;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 15/6/16.
 */
public class PlagueSpecification implements RealmSpecification<PlagueRealm> {

    @Override
    public Flowable<RealmResults<PlagueRealm>> toFlowable(@NonNull Realm realm) {
        return realm.where(PlagueRealm.class).findAll().asFlowable();
    }

    @Override
    public RealmResults<PlagueRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(PlagueRealm.class).findAll();
    }
}
