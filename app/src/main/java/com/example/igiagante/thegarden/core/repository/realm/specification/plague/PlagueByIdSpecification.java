package com.example.igiagante.thegarden.core.repository.realm.specification.plague;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class PlagueByIdSpecification implements RealmSpecification {

    private final String id;

    public PlagueByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Flowable<RealmResults<PlagueRealm>> toFlowable(Realm realm) {
        return Flowable.just(realm.where(PlagueRealm.class).equalTo(Table.ID, id)
                .findAllAsync());
    }

    @Override
    public RealmResults toRealmResults(Realm realm) {
        return null;
    }
}
