package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.network.Settings;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.FlavorRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import java.util.UUID;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class FlavorToFlavorRealm implements Mapper<Flavor, FlavorRealm> {

    private final Realm realm;

    public FlavorToFlavorRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public FlavorRealm map(Flavor flavor) {

        FlavorRealm flavorRealm = realm.where(FlavorRealm.class)
                .equalTo(Table.ID, flavor.getId()).findFirst();

        if(flavorRealm == null) {
            flavorRealm = realm.createObject(FlavorRealm.class, flavor.getId());
        }

        copy(flavor, flavorRealm);

        return flavorRealm;
    }

    @Override
    public FlavorRealm copy(Flavor flavor, FlavorRealm flavorRealm) {
        flavorRealm.setName(flavor.getName());
        flavorRealm.setImageUrl(flavor.getImageUrl().replace(Settings.DOMAIN, ""));
        return flavorRealm;
    }
}
