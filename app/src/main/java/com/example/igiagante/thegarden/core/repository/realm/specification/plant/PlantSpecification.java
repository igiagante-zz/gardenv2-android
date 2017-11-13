package com.example.igiagante.thegarden.core.repository.realm.specification.plant;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author giagante on 5/5/16.
 */
public class PlantSpecification implements RealmSpecification<PlantRealm> {

    @Override
    public Flowable<RealmResults<PlantRealm>> toFlowable(@NonNull Realm realm) {
        return realm.where(PlantRealm.class).findAllAsync().asFlowable();
    }

    @Override
    public RealmResults<PlantRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(PlantRealm.class).findAllAsync();
    }
}
