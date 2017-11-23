package com.example.igiagante.thegarden.core.repository.realm.specification.nutrient;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.NutrientTable;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientByNameSpecification implements RealmSpecification<NutrientRealm> {

    private final String name;

    public NutrientByNameSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Flowable<RealmResults<NutrientRealm>> toFlowable(Realm realm) {
        return Flowable.just(realm.where(NutrientRealm.class)
                .equalTo(Table.NAME, name)
                .findAll());
    }

    @Override
    public RealmResults<NutrientRealm> toRealmResults(Realm realm) {
        return null;
    }
}
