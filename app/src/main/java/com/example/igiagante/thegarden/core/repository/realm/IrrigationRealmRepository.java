package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.realm.mapper.IrrigationRealmToIrrigation;
import com.example.igiagante.thegarden.core.repository.realm.mapper.IrrigationToIrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.IrrigationTable;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class IrrigationRealmRepository extends RealmRepository<Irrigation, IrrigationRealm> {

    @Override
    MapToRealm<Irrigation, IrrigationRealm> initModelToRealmMapper(Realm realm) {
        return new IrrigationToIrrigationRealm(realm);
    }

    @Override
    MapToModel<IrrigationRealm, Irrigation> initRealmToModelMapper() {
        return new IrrigationRealmToIrrigation();
    }

    public IrrigationRealmRepository(@NonNull Context context) {

        super(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = IrrigationRealm.class;
    }

    public void removeIrrigationsByGardenId(String gardenId) {

        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            RealmResults<IrrigationRealm> result = realm.where(IrrigationRealm.class)
                    .equalTo(IrrigationTable.GARDEN_ID, gardenId).findAll();
            result.deleteAllFromRealm();
        });
        realm.close();
    }

}
