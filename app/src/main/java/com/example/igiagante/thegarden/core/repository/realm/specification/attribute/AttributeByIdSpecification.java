package com.example.igiagante.thegarden.core.repository.realm.specification.attribute;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class AttributeByIdSpecification implements RealmSpecification {

    private final String id;

    public AttributeByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Flowable<RealmResults<AttributeRealm>> toFlowable(Realm realm) {
        return Flowable.just(realm.where(AttributeRealm.class)
                .equalTo(Table.ID, id)
                .findAllAsync());
    }

    @Override
    public RealmResults toRealmResults(Realm realm) {
        return null;
    }

}
