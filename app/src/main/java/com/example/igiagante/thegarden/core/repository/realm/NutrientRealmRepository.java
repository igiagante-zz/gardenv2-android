package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.realm.mapper.NutrientRealmToNutrient;
import com.example.igiagante.thegarden.core.repository.realm.mapper.NutrientToNutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.GardenTable;

import io.reactivex.Observable;
import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class NutrientRealmRepository extends RealmRepository<Nutrient, NutrientRealm> {

    @Override
    MapToRealm<Nutrient, NutrientRealm> initModelToRealmMapper(Realm realm) {
        return new NutrientToNutrientRealm(realm);
    }

    @Override
    MapToModel<NutrientRealm, Nutrient> initRealmToModelMapper() {
        return new NutrientRealmToNutrient();
    }

    public NutrientRealmRepository(@NonNull Context context) {

        super(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = NutrientRealm.class;
    }

    public Observable<Nutrient> getNutrientsByUserId(@NonNull String userId) {

        NutrientRealm nutrientRealm = realm.where(NutrientRealm.class)
                .equalTo(GardenTable.USER_ID, userId)
                .findFirst();

        return  Observable.just(realmToModel.map(nutrientRealm));
    }

}
