package com.example.igiagante.thegarden.core.repository.realm.specification.plant;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author giagante on 5/5/16.
 */
public class PlantByIdSpecification implements RealmSpecification {

    private final String id;

    public PlantByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Flowable<RealmResults<PlantRealm>> toFlowable(@NonNull Realm realm) {

        return Flowable.just(realm.where(PlantRealm.class).equalTo(Table.ID, id)
                .findAllAsync());
    }

    @Override
    public RealmResults<PlantRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }
}
