package com.example.igiagante.thegarden.core.repository.realm.specification.irrigations;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class IrrigationByIdSpecification implements RealmSpecification {

    private final String id;

    public IrrigationByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Flowable<RealmResults<IrrigationRealm>> toFlowable(@NonNull Realm realm) {
        return Flowable.just(realm.where(IrrigationRealm.class).equalTo(Table.ID, id)
                .findAll());
    }

    @Override
    public RealmResults<IrrigationRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }
}
