package com.example.igiagante.thegarden.core.repository.realm.specification.garden;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.GardenTable;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 13/8/16.
 */
public class GardenByNameAndUserIdSpecification implements RealmSpecification<GardenRealm> {

    private final String gardenName;
    private final String userId;

    public GardenByNameAndUserIdSpecification(final String gardenName, final String userId) {
        this.gardenName = gardenName;
        this.userId = userId;
    }

    @Override
    public Flowable<RealmResults<GardenRealm>> toFlowable(Realm realm) {
        return Flowable.just(realm.where(GardenRealm.class)
                .equalTo(GardenTable.NAME, gardenName)
                .equalTo(GardenTable.USER_ID, userId)
                .findAllAsync());
    }

    @Override
    public RealmResults<GardenRealm> toRealmResults(Realm realm) {
        return null;
    }
}
