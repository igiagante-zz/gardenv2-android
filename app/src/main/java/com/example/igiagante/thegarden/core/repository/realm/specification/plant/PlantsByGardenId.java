package com.example.igiagante.thegarden.core.repository.realm.specification.plant;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 16/8/16.
 */
public class PlantsByGardenId implements RealmSpecification<PlantRealm> {

    private final String gardenId;

    public PlantsByGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    @Override
    public Flowable<RealmResults<PlantRealm>> toFlowable(Realm realm) {
        return Flowable.just(realm.where(PlantRealm.class)
                .equalTo(PlantTable.GARDEN_ID, gardenId)
                .findAll());
    }

    @Override
    public RealmResults<PlantRealm> toRealmResults(Realm realm) {
        return null;
    }
}
