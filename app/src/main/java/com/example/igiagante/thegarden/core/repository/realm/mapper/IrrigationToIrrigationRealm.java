package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class IrrigationToIrrigationRealm implements MapToRealm<Irrigation, IrrigationRealm> {

    private final Realm realm;
    private final DoseToDoseRealm toDoseRealm;

    public IrrigationToIrrigationRealm(Realm realm) {
        this.realm = realm;
        this.toDoseRealm = new DoseToDoseRealm(realm);
    }

    @Override
    public IrrigationRealm map(Irrigation irrigation, Realm realm) {

        IrrigationRealm irrigationRealm = realm.where(IrrigationRealm.class)
                .equalTo(Table.ID, irrigation.getId()).findFirst();

        if(irrigationRealm == null) {
            irrigationRealm = realm.createObject(IrrigationRealm.class, irrigation.getId());
        }

        return copy(irrigation, irrigationRealm, realm);
    }

    @Override
    public IrrigationRealm copy(Irrigation irrigation, IrrigationRealm irrigationRealm, Realm realm) {

        irrigationRealm.setIrrigationDate(irrigation.getIrrigationDate());
        irrigationRealm.setGardenId(irrigation.getGardenId());
        irrigationRealm.setQuantity(irrigation.getQuantity());

        irrigationRealm.setDose(toDoseRealm.map(irrigation.getDose(), realm));

        return irrigationRealm;
    }
}
