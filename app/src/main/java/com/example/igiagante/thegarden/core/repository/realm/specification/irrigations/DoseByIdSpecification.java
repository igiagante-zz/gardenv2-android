package com.example.igiagante.thegarden.core.repository.realm.specification.irrigations;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class DoseByIdSpecification implements RealmSpecification {

    private final String id;

    public DoseByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Flowable<RealmResults<DoseRealm>> toFlowable(@NonNull Realm realm) {
        return Flowable.just(realm.where(DoseRealm.class).equalTo(Table.ID, id)
                .findAll());
    }

    @Override
    public RealmResults<DoseRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }
}
