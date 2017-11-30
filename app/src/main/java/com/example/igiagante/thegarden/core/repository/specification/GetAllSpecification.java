package com.example.igiagante.thegarden.core.repository.specification;

import com.example.igiagante.thegarden.core.repository.Specification;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by igiagante on 30/11/17.
 */

public class GetAllSpecification<T> implements Specification<T> {

    private Class clazz;

    public GetAllSpecification(Class clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public RealmResults<T> toRealmResults(Realm realm){
        return realm.where(this.clazz).findAll();
    }
}
