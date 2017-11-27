package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;

import io.reactivex.Observable;
import io.realm.Realm;


/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class AttributeRealmRepository extends RealmRepository<Attribute, AttributeRealm> {

    @Override
    MapToRealm<Attribute, AttributeRealm> initModelToRealmMapper(Realm realm) {
        return null;
    }

    @Override
    MapToModel<AttributeRealm, Attribute> initRealmToModelMapper(Context context) {
        return null;
    }

    public AttributeRealmRepository(@NonNull Context context) {
        super(context);
    }

    @Override
    Observable<Boolean> exists(@NonNull String id) {
        return super.checkIfRealmObjectExists(id);
    }

    @Override
    void setRealmClass() {
        this.realmClass = AttributeRealm.class;
    }

}
