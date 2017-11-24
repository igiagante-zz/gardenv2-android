package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.network.Settings;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.FlavorRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class FlavorToFlavorRealm implements MapToRealm<Flavor, FlavorRealm> {

    private final Realm realm;

    public FlavorToFlavorRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public FlavorRealm map(Flavor flavor, Realm realm) {

        FlavorRealm flavorRealm = realm.where(FlavorRealm.class)
                .equalTo(Table.ID, flavor.getId()).findFirst();

        if(flavorRealm == null) {
            flavorRealm = realm.createObject(FlavorRealm.class, flavor.getId());
        }

        copy(flavor, flavorRealm, realm);

        return flavorRealm;
    }

    @Override
    public FlavorRealm copy(Flavor flavor, FlavorRealm flavorRealm, Realm realm) {
        flavorRealm.setName(flavor.getName());
        flavorRealm.setImageUrl(flavor.getImageUrl().replace(Settings.DOMAIN, ""));
        return flavorRealm;
    }
}
