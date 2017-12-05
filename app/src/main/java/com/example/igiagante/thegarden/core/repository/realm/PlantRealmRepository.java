package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.realm.mapper.PlantRealmToPlant;
import com.example.igiagante.thegarden.core.repository.realm.mapper.PlantToPlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;

import io.reactivex.Observable;
import io.realm.Realm;


/**
 * @author Ignacio Giagante, on 5/5/16.
 */
public class PlantRealmRepository extends RealmRepository<Plant, PlantRealm> {


    @Override
    MapToRealm<Plant, PlantRealm> initModelToRealmMapper(Realm realm) {
        return new PlantToPlantRealm(realm);
    }

    @Override
    MapToModel<PlantRealm, Plant> initRealmToModelMapper() {
        return new PlantRealmToPlant();
    }

    public PlantRealmRepository(@NonNull Context context) {

       super(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = PlantRealm.class;
    }

    public Observable<Plant> getPlantByGardenId(@NonNull String gardenId) {

        PlantRealm plantRealm = realm.where(PlantRealm.class)
                .equalTo(PlantTable.GARDEN_ID, gardenId)
                .findFirst();

        return  Observable.just(realmToModel.map(plantRealm));
    }
}
