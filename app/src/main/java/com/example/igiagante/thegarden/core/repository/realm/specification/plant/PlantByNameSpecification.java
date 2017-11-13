package com.example.igiagante.thegarden.core.repository.realm.specification.plant;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author giagante on 5/5/16.
 */
public class PlantByNameSpecification implements RealmSpecification<PlantRealm> {

    private final String name;

    public PlantByNameSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Flowable<RealmResults<PlantRealm>> toFlowable(@NonNull Realm realm) {
        return Flowable.just(realm.where(PlantRealm.class)
                .equalTo(PlantTable.NAME, name)
                .findAllAsync());
    }

    @Override
    public RealmResults<PlantRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }
}
