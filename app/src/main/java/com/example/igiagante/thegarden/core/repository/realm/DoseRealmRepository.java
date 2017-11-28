package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class DoseRealmRepository extends RealmRepository<Dose, DoseRealm> {

    @Override
    MapToRealm<Dose, DoseRealm> initModelToRealmMapper(Realm realm) {
        return null;
    }

    @Override
    MapToModel<DoseRealm, Dose> initRealmToModelMapper(Context context) {
        return null;
    }

    public DoseRealmRepository(@NonNull Context context) {

        super(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = DoseRealm.class;
    }

}
