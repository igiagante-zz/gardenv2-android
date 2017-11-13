package com.example.igiagante.thegarden.core.repository.realm.specification.irrigations;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class IrrigationSpecification  implements RealmSpecification<IrrigationRealm> {

    @Override
    public Flowable<RealmResults<IrrigationRealm>> toFlowable(@NonNull Realm realm) {
        return realm.where(IrrigationRealm.class).findAllAsync().asFlowable();
    }

    @Override
    public RealmResults<IrrigationRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(IrrigationRealm.class).findAllAsync();
    }
}
