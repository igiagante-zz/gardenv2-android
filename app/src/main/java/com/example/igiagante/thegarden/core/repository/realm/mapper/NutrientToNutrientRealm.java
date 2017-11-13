package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class NutrientToNutrientRealm implements Mapper<Nutrient, NutrientRealm> {

    private final Realm realm;
    private final ImageToImageRealm toImageRealm;

    public NutrientToNutrientRealm(Realm realm) {
        this.realm = realm;
        this.toImageRealm = new ImageToImageRealm(realm);
    }

    @Override
    public NutrientRealm map(Nutrient nutrient) {

        NutrientRealm nutrientRealm = realm.where(NutrientRealm.class).equalTo(Table.ID, nutrient.getId()).findFirst();

        if(nutrientRealm == null) {
            nutrientRealm = realm.createObject(NutrientRealm.class, nutrient.getId());
        }

        return copy(nutrient, nutrientRealm);
    }

    @Override
    public NutrientRealm copy(Nutrient nutrient, NutrientRealm nutrientRealm) {
        nutrientRealm.setUserId(nutrient.getUserId());
        nutrientRealm.setName(nutrient.getName());
        nutrientRealm.setPh(nutrient.getPh());
        nutrientRealm.setNpk(nutrient.getNpk());
        nutrientRealm.setDescription(nutrient.getDescription());
        nutrientRealm.setQuantityUsed(nutrient.getQuantityUsed());

        // images realm list
        RealmList<ImageRealm> imagesRealm = new RealmList<>();

        // add images
        if (nutrient.getImages() != null) {
            for (Image image : nutrient.getImages()) {
                ImageRealm imageRealm = realm.where(ImageRealm.class).equalTo(Table.ID, image.getId()).findFirst();
                if (imageRealm == null) {
                    // create image realm object and set id
                    imageRealm = realm.createObject(ImageRealm.class, image.getId());
                }
                imagesRealm.add(toImageRealm.copy(image, imageRealm));
            }
        }

        nutrientRealm.setImages(imagesRealm);

        return nutrientRealm;
    }
}
