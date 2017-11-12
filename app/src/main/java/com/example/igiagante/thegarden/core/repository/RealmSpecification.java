package com.example.igiagante.thegarden.core.repository;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


/**
 * @author Ignacio Giagante, on 5/5/16.
 */
public interface RealmSpecification<T extends RealmObject> extends Specification {

    Flowable<RealmResults<T>> toFlowable(Realm realm);

    RealmResults<T> toRealmResults(Realm realm);
}
