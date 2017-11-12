package com.example.igiagante.thegarden.core.repository.realm.specification.attribute;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
public class AttributeSpecification  implements RealmSpecification<AttributeRealm> {

    @Override
    public Flowable<RealmResults<AttributeRealm>> toFlowable(@NonNull Realm realm) {
        return realm.where(AttributeRealm.class).findAll().asFlowable();
    }

    @Override
    public RealmResults<AttributeRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(AttributeRealm.class).findAll();
    }
}
