package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.FlavorRealm;

import io.realm.Realm;


/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class FlavorRealmRepository extends RealmRepository<Flavor, FlavorRealm> {

    @Override
    MapToRealm<Flavor, FlavorRealm> initModelToRealmMapper(Realm realm) {
        return null;
    }

    @Override
    MapToModel<FlavorRealm, Flavor> initRealmToModelMapper(Context context) {
        return null;
    }

    public FlavorRealmRepository(@NonNull Context context) {
        super(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = AttributeRealm.class;
    }

}
