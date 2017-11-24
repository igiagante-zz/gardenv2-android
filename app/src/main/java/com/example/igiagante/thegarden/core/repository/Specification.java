package com.example.igiagante.thegarden.core.repository;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author Ignacio Giagante, on 15/4/16.
 */
public interface Specification<T> {

    RealmResults<T> toRealmResults(Realm realm);
}
