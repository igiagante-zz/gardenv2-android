package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.network.Settings;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 28/4/16.
 */
public class ImageToImageRealm implements MapToRealm<Image, ImageRealm> {

    private final Realm realm;

    public ImageToImageRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public ImageRealm map(@NonNull Image image, Realm realm) {

        ImageRealm imageRealm = realm.where(ImageRealm.class)
                .equalTo(Table.ID, image.getId()).findFirst();

        if(imageRealm == null) {
            imageRealm = realm.createObject(ImageRealm.class, image.getId());
        }

        copy(image, imageRealm, realm);

        return imageRealm;
    }

    @Override
    public ImageRealm copy(@NonNull Image image, @NonNull ImageRealm imageRealm, Realm realm) {

        imageRealm.setName(image.getName());
        imageRealm.setUrl(image.getUrl().replace(Settings.DOMAIN, ""));
        imageRealm.setThumbnailUrl(image.getThumbnailUrl().replace(Settings.DOMAIN, ""));
        imageRealm.setType(image.getType());
        imageRealm.setSize(image.getSize());
        imageRealm.setMain(image.isMain());

        return imageRealm;
    }
}
