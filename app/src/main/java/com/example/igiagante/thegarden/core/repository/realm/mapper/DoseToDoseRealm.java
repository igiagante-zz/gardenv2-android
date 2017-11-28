package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientPerDoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class DoseToDoseRealm implements MapToRealm<Dose, DoseRealm> {

    private final Realm realm;
    private final NutrientToNutrientPerDoseRealm toNutrientRealm;

    public DoseToDoseRealm(Realm realm) {
        this.realm = realm;
        this.toNutrientRealm = new NutrientToNutrientPerDoseRealm(realm);
    }

    @Override
    public DoseRealm map(Dose dose, Realm realm) {

        DoseRealm doseRealm = realm.where(DoseRealm.class)
                .equalTo(Table.ID, dose.getId()).findFirst();

        if(doseRealm == null) {
            doseRealm = realm.createObject(DoseRealm.class, UUID.randomUUID().toString());
        }

        return copy(dose, doseRealm, realm);
    }

    @Override
    public DoseRealm copy(Dose dose, DoseRealm doseRealm, Realm realm) {

        doseRealm.setWater(dose.getWater());
        doseRealm.setPh(dose.getPh());
        doseRealm.setEc(dose.getEc());
        doseRealm.setPhDose(dose.getPhDose());

        // nutrients realm list
        RealmList<NutrientPerDoseRealm> nutrientRealms = new RealmList<>();

        // add nutrients
        if (dose.getNutrients() != null) {
            for (Nutrient nutrient : dose.getNutrients()) {
                NutrientPerDoseRealm nutrientRealm = realm.createObject(NutrientPerDoseRealm.class, UUID.randomUUID().toString());
                nutrientRealms.add(toNutrientRealm.copy(nutrient, nutrientRealm, realm));
            }
        }

        doseRealm.setNutrients(nutrientRealms);

        return doseRealm;
    }
}
