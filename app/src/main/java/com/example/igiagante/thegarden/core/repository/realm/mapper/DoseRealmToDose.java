package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class DoseRealmToDose implements Mapper<DoseRealm, Dose> {

    private final NutrientRealmToNutrient toNutrient;

    public DoseRealmToDose(){
        this.toNutrient = new NutrientRealmToNutrient();
    }

    @Override
    public Dose map(DoseRealm doseRealm) {
        Dose dose = new Dose();
        dose.setId(doseRealm.getId());
        return copy(doseRealm, dose);
    }

    @Override
    public Dose copy(DoseRealm doseRealm, Dose dose) {
        dose.setWater(doseRealm.getWater());
        dose.setPh(doseRealm.getPh());
        dose.setEc(doseRealm.getEc());
        dose.setEditable(doseRealm.isEditable());

        ArrayList<Nutrient> nutrients = new ArrayList<>();

        // add images
        if(doseRealm.getNutrients() != null) {
            for (NutrientRealm nutrientRealm : doseRealm.getNutrients()) {
                nutrients.add(toNutrient.map(nutrientRealm));
            }
        }

        dose.setNutrients(nutrients);

        return dose;
    }
}