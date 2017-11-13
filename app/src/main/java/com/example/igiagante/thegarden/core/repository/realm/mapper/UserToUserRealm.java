package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.UserRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserToUserRealm implements Mapper<User, UserRealm> {

    private final Realm realm;
    private final GardenToGardenRealm toGardenRealm;
    private final NutrientToNutrientRealm toNutrientRealm;

    public UserToUserRealm(Realm realm) {
        this.realm = realm;
        this.toGardenRealm = new GardenToGardenRealm(realm);
        this.toNutrientRealm = new NutrientToNutrientRealm(realm);
    }

    @Override
    public UserRealm map(User user) {

        // create user realm object and set id
        UserRealm userRealm = realm.where(UserRealm.class).equalTo(Table.ID, user.getId()).findFirst();
        if(userRealm == null) {
            userRealm = realm.createObject(UserRealm.class, user.getId());
        }
        return copy(user, userRealm);
    }

    @Override
    public UserRealm copy(User user, UserRealm userRealm) {

        userRealm.setUsername(user.getUserName());

        // add gardens
        ArrayList<Garden> gardens = user.getGardens();
        RealmList<GardenRealm> gardenRealms = new RealmList<>();

        if(gardens != null && !gardens.isEmpty()) {
            for(Garden garden : gardens) {
                GardenRealm gardenRealm = realm.where(GardenRealm.class).equalTo(Table.ID, garden.getId()).findFirst();
                if (gardenRealm == null) {
                    // create garden realm object and set id
                    gardenRealm = realm.createObject(GardenRealm.class, garden.getId());
                }
                gardenRealms.add(toGardenRealm.copy(garden, gardenRealm));
            }
        }

        userRealm.setGardens(gardenRealms);

        // add nutrients
        ArrayList<Nutrient> nutrients = user.getNutrients();
        RealmList<NutrientRealm> nutrientRealms = new RealmList<>();

        if(nutrients != null && !nutrients.isEmpty()) {
            for(Nutrient nutrient : nutrients) {
                NutrientRealm nutrientRealm = realm.where(NutrientRealm.class).equalTo(Table.ID, nutrient.getId()).findFirst();
                if (nutrientRealm == null) {
                    // create nutrient realm object and set id
                    nutrientRealm = realm.createObject(NutrientRealm.class, nutrient.getId());
                    nutrientRealm.setId(nutrient.getId());
                }
                nutrientRealms.add(toNutrientRealm.copy(nutrient, nutrientRealm));
            }
        }

        userRealm.setNutrients(nutrientRealms);

        return userRealm;
    }
}
