package com.example.igiagante.thegarden.core.repository.realm.specification.garden;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class GardenByIdSpecification implements RealmSpecification {

    private final String id;

    public GardenByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Flowable<RealmResults<GardenRealm>> toFlowable(@NonNull Realm realm) {
        return Flowable.just(realm.where(GardenRealm.class).equalTo(Table.ID, id)
                .findAllAsync());
    }

    @Override
    public RealmResults<GardenRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }
}
