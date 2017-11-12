package com.example.igiagante.thegarden.creation.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultObserver;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.views.PhotoGalleryView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * This presenter has the logic related to the photos gallery
 *
 * @author Ignacio Giagante, on 11/5/16.
 */
@PerActivity
public class PhotoGalleryPresenter extends AbstractPresenter<PhotoGalleryView> {

    private final UseCase getImagesUserCase;

    @Inject
    public PhotoGalleryPresenter(@Named("images") UseCase getImagesUserCase) {
        this.getImagesUserCase = getImagesUserCase;
    }

    public void destroy() {
        this.getImagesUserCase.dispose();
        view = null;
    }

    /**
     * Get the list of images for the photos gallery
     *
     * @param imagesPathFiles files paths from images
     */
    public void getImagesList(Collection<String> imagesPathFiles) {
        this.getImagesUserCase.execute(imagesPathFiles, new PhotoGalleryObserver());
    }

    private void addImagesToBuilderInView(Collection<Image> images) {
        getView().loadImages(images);
    }

    private final class PhotoGalleryObserver extends DefaultObserver<List<Image>> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(List<Image> images) {
            PhotoGalleryPresenter.this.addImagesToBuilderInView(images);
        }
    }
}
