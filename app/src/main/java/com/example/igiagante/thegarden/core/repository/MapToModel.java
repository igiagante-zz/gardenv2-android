package com.example.igiagante.thegarden.core.repository;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * @author Ignacio Giagante, on 26/4/16.
 */
public interface MapToModel<From extends RealmObject & RealmModel, To> {

    To map(From from);

    To copy(From from, To to);
}
