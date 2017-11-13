package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import java.util.UUID;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class AttributeToAttributeRealm implements Mapper<Attribute, AttributeRealm> {

    private final Realm realm;

    public AttributeToAttributeRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public AttributeRealm map(Attribute attribute) {

        AttributeRealm attributeRealm = realm.where(AttributeRealm.class)
                .equalTo(Table.ID, attribute.getId()).findFirst();

        if(attributeRealm == null) {
            attributeRealm = realm.createObject(AttributeRealm.class, attribute.getId());
        }

        copy(attribute, attributeRealm);

        return attributeRealm;
    }

    @Override
    public AttributeRealm copy(Attribute attribute, AttributeRealm attributeRealm) {

        attributeRealm.setName(attribute.getName());
        attributeRealm.setType(attribute.getType());
        attributeRealm.setPercentage(attribute.getPercentage());

        return attributeRealm;
    }

}
