package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.realm.mapper.PlagueRealmToPlague;
import com.example.igiagante.thegarden.core.repository.realm.mapper.PlagueToPlagueRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;

import io.realm.Realm;


/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class PlagueRealmRepository extends RealmRepository<Plague, PlagueRealm> {


    @Override
    MapToRealm<Plague, PlagueRealm> initModelToRealmMapper(Realm realm) {
        return new PlagueToPlagueRealm(realm);
    }

    @Override
    MapToModel<PlagueRealm, Plague> initRealmToModelMapper() {
        return new PlagueRealmToPlague();
    }

    public PlagueRealmRepository(@NonNull Context context) {

        super(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = PlagueRealm.class;
    }
}
