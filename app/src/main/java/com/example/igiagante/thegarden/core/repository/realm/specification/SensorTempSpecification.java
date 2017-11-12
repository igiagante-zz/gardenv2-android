package com.example.igiagante.thegarden.core.repository.realm.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.SensorTempRealm;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class SensorTempSpecification implements RealmSpecification<SensorTempRealm> {

    @Override
    public Flowable<RealmResults<SensorTempRealm>> toFlowable(Realm realm) {
        return Flowable.just(realm.where(SensorTempRealm.class).findAll());
    }

    @Override
    public RealmResults<SensorTempRealm> toRealmResults(Realm realm) {
        return realm.where(SensorTempRealm.class).findAll();
    }
}
