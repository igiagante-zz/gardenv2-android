package com.example.igiagante.thegarden.core.repository;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 26/4/16.
 */
public interface MapToRealm<From, To> {

    To map(From from, Realm realm);

    To copy(From from, To to, Realm realm);
}
