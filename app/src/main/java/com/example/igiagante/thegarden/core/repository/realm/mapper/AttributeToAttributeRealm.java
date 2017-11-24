package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class AttributeToAttributeRealm implements MapToRealm<Attribute, AttributeRealm> {

    private final Realm realm;

    public AttributeToAttributeRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public AttributeRealm map(Attribute attribute, Realm realm) {

        AttributeRealm attributeRealm = realm.where(AttributeRealm.class)
                .equalTo(Table.ID, attribute.getId()).findFirst();

        if(attributeRealm == null) {
            attributeRealm = realm.createObject(AttributeRealm.class, attribute.getId());
        }

        copy(attribute, attributeRealm, realm);

        return attributeRealm;
    }

    @Override
    public AttributeRealm copy(Attribute attribute, AttributeRealm attributeRealm, Realm realm) {

        attributeRealm.setName(attribute.getName());
        attributeRealm.setType(attribute.getType());
        attributeRealm.setPercentage(attribute.getPercentage());

        return attributeRealm;
    }

}
