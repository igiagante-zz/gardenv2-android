package com.example.igiagante.thegarden.core.repository.realm.specification.nutrient;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientSpecification implements RealmSpecification<NutrientRealm> {

    @Override
    public Flowable<RealmResults<NutrientRealm>> toFlowable(Realm realm) {
        return realm.where(NutrientRealm.class).findAll().asFlowable();
    }

    @Override
    public RealmResults<NutrientRealm> toRealmResults(Realm realm) {
        return realm.where(NutrientRealm.class).findAll();
    }
}
