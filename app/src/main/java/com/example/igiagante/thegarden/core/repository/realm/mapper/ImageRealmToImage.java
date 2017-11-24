package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.network.Settings;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;

/**
 * @author Ignacio Giagante, on 28/4/16.
 */
public class ImageRealmToImage implements MapToModel<ImageRealm, Image> {

    @Override
    public Image map(@NonNull ImageRealm imageRealm) {

        Image image = new Image();
        image.setId(imageRealm.getId());
        copy(imageRealm, image);

        return image;
    }

    @Override
    public Image copy(@NonNull ImageRealm imageRealm, @NonNull Image image) {

        image.setName(imageRealm.getName());
        image.setUrl(Settings.DOMAIN + imageRealm.getUrl());
        image.setThumbnailUrl(Settings.DOMAIN + imageRealm.getThumbnailUrl());
        image.setType(imageRealm.getType());
        image.setSize(imageRealm.getSize());
        image.setMain(imageRealm.isMain());

        return image;
    }

}
