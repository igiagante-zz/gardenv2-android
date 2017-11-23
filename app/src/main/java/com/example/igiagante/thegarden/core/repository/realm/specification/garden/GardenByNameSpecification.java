package com.example.igiagante.thegarden.core.repository.realm.specification.garden;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.GardenTable;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 5/7/16.
 */
public class GardenByNameSpecification implements RealmSpecification<GardenRealm> {

    private final String name;

    public GardenByNameSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Flowable<RealmResults<GardenRealm>> toFlowable(@NonNull Realm realm) {
        return Flowable.just(realm.where(GardenRealm.class)
                .equalTo(Table.NAME, name)
                .findAll());
    }

    @Override
    public RealmResults<GardenRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }
}
