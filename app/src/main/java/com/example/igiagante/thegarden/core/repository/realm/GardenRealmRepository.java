package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.realm.mapper.GardenRealmToGarden;
import com.example.igiagante.thegarden.core.repository.realm.mapper.GardenToGardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.GardenTable;

import io.reactivex.Observable;
import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRealmRepository extends RealmRepository<Garden, GardenRealm> {

    public GardenRealmRepository(@NonNull Context context) {
        super(context);
    }

    // Mapper<GardenRealm, Garden>
    MapToRealm<Garden, GardenRealm> initModelToRealmMapper(Realm realm) {
        return new GardenToGardenRealm(realm);
    }

    // Mapper<Garden, GardenRealm>
    MapToModel<GardenRealm, Garden> initRealmToModelMapper(Context context) {
        return new GardenRealmToGarden(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = GardenRealm.class;
    }

    public Observable<Garden> getByNameAndUserId(@NonNull String gardenName, @NonNull String userId) {

        GardenRealm gardenRealm = realm.where(GardenRealm.class)
                .equalTo(GardenTable.NAME, gardenName)
                .equalTo(GardenTable.USER_ID, userId)
                .findFirst();

        return  Observable.just(realmToModel.map(gardenRealm));
    }
}
