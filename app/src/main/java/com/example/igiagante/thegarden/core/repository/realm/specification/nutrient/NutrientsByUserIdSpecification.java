package com.example.igiagante.thegarden.core.repository.realm.specification.nutrient;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.NutrientTable;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 28/8/16.
 */
public class NutrientsByUserIdSpecification implements RealmSpecification<NutrientRealm> {

    private final String userId;

    public NutrientsByUserIdSpecification(final String userId) {
        this.userId = userId;
    }

    @Override
    public Flowable<RealmResults<NutrientRealm>> toFlowable(Realm realm) {
        return Flowable.just(realm.where(NutrientRealm.class).equalTo(NutrientTable.USER_ID, userId)
                .findAllAsync());
    }

    @Override
    public RealmResults<NutrientRealm> toRealmResults(Realm realm) {
        return null;
    }
}
