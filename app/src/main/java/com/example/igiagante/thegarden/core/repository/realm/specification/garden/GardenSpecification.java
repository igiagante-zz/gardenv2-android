package com.example.igiagante.thegarden.core.repository.realm.specification.garden;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenSpecification implements RealmSpecification<GardenRealm> {

    @Override
    public Flowable<RealmResults<GardenRealm>> toFlowable(@NonNull Realm realm) {
        return realm.where(GardenRealm.class).findAll().asFlowable();
    }

    @Override
    public RealmResults<GardenRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(GardenRealm.class).findAll();
    }
}
