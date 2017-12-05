package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.realm.mapper.AttributeRealmToAttribute;
import com.example.igiagante.thegarden.core.repository.realm.mapper.AttributeToAttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;

import io.reactivex.Observable;
import io.realm.Realm;


/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class AttributeRealmRepository extends RealmRepository<Attribute, AttributeRealm> {

    @Override
    MapToRealm<Attribute, AttributeRealm> initModelToRealmMapper(Realm realm) {
        return new AttributeToAttributeRealm(realm);
    }

    @Override
    MapToModel<AttributeRealm, Attribute> initRealmToModelMapper() {
        return new AttributeRealmToAttribute();
    }

    public AttributeRealmRepository(@NonNull Context context) {
        super(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = AttributeRealm.class;
    }

    @Override
    public Observable<Attribute> save(Attribute item, boolean update) {
        return null;
    }
}
