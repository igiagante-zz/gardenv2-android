package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class PlagueToPlagueRealm implements MapToRealm<Plague, PlagueRealm> {

    private final Realm realm;

    public PlagueToPlagueRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public PlagueRealm map(Plague plague, Realm realm) {

        PlagueRealm plagueRealm = realm.where(PlagueRealm.class).equalTo(Table.ID, plague.getId()).findFirst();

        if(plagueRealm == null) {
            plagueRealm = realm.createObject(PlagueRealm.class, plague.getId());
        }

        return copy(plague, plagueRealm, realm);
    }

    @Override
    public PlagueRealm copy(Plague plague, PlagueRealm plagueRealm, Realm Realm) {

        plagueRealm.setName(plague.getName());
        plagueRealm.setImageUrl(plague.getImageUrl());

        return plagueRealm;
    }

}
