package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.util.Log;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.UserRealm;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserRealmToUser implements MapToModel<UserRealm, User> {

    private final GardenRealmToGarden toGarden;
    private final NutrientRealmToNutrient toNutrient;

    public UserRealmToUser() {
        this.toGarden = new GardenRealmToGarden();
        this.toNutrient = new NutrientRealmToNutrient();
    }

    @Override
    public User map(UserRealm userRealm) {
        User user = new User();
        user.setId(userRealm.getId());
        return copy(userRealm, user);
    }

    @Override
    public User copy(UserRealm userRealm, User user) {

        Log.d("UserRealmToUserTHREAD: ", Thread.currentThread().getName());

        user.setUserName(userRealm.getUsername());

        // add gardens
        ArrayList<Garden> gardens = new ArrayList<>();
        RealmList<GardenRealm> gardenRealmList = userRealm.getGardens();

        if(gardenRealmList != null && !gardenRealmList.isEmpty()) {
            for(GardenRealm gardenRealm : gardenRealmList){
                gardens.add(toGarden.map(gardenRealm));
            }
        }

        user.setGardens(gardens);

        // add nutrients
        ArrayList<Nutrient> nutrients = user.getNutrients();
        RealmList<NutrientRealm> nutrientRealms = userRealm.getNutrients();

        if(nutrientRealms != null && !nutrientRealms.isEmpty()) {
            for(NutrientRealm nutrientRealm : nutrientRealms){
                nutrients.add(toNutrient.map(nutrientRealm));
            }
        }

        user.setNutrients(nutrients);

        return user;
    }
}
