package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.creation.nutrients.presentation.NutrientDetailActivity;
import com.example.igiagante.thegarden.creation.plants.di.components.GalleryComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CarouselActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.GalleryAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.PhotoGalleryPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.views.PhotoGalleryView;
import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Ignacio Giagante, on 10/5/16.
 */
public class PhotoGalleryFragment extends CreationBaseFragment implements PhotoGalleryView, GalleryAdapter.OnShowImages {

    /**
     * It's the request code used to start the carousel intent.
     */
    public static final int CAROUSEL_REQUEST_CODE = 23;

    public static final String IMAGES_KEY = "IMAGES";

    @BindView(R.id.recycler_view_plant_photo_gallery)
    RecyclerView mGallery;

    @Inject
    PhotoGalleryPresenter mPhotoGalleryPresenter;

    private GalleryAdapter mAdapter;

    /**
     * List of images which should share between the gallery and teh carousel
     */
    private ArrayList<Image> mImages = new ArrayList<>();

    /**
     * List of files' paths from files which were added using the android's gallery
     */
    private List<String> imagesFilesPaths = new ArrayList<>();

    public static PhotoGalleryFragment newInstance(ArrayList<Image> images) {
        PhotoGalleryFragment myFragment = new PhotoGalleryFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(IMAGES_KEY, images);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.getComponent(GalleryComponent.class).inject(this);

        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.plant_gallery_fragment, container, false);
        ButterKnife.bind(this, containerView);

        if (savedInstanceState != null) {
            mImages = savedInstanceState.getParcelableArrayList(IMAGES_KEY);
        }

        // load images from instance
        Bundle arguments = getArguments();
        if (arguments != null) {
            mImages = arguments.getParcelableArrayList(IMAGES_KEY);
        }

        // check if the user is trying to edit a plant
        if (mPlant != null) {
            mImages = (ArrayList<Image>) mPlant.getImages();
        }

        if (mImages != null) {
            loadResourcesIds(mImages);
        }

        mGallery.setHasFixedSize(true);

        //Two columns for portrait
        GridLayoutManager manager;
        if (isLandScape() && !(getActivity() instanceof NutrientDetailActivity)) {
            manager = new GridLayoutManager(getActivity(), 3);
        } else {
            manager = new GridLayoutManager(getActivity(), 2);
        }

        if (getActivity() instanceof NutrientDetailActivity) {
            manager.setOrientation(RecyclerView.HORIZONTAL);
        }

        mGallery.setLayoutManager(manager);

        mAdapter = new GalleryAdapter(getContext(), this::pickImages, this::deleteImage, this::onShowImages);
        mAdapter.setImagesPath(getImagesFilesPaths(mImages));
        mGallery.setAdapter(mAdapter);

        return containerView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(IMAGES_KEY, mImages);
    }

    /**
     * This method should implemented by the button Add Image
     */
    private void pickImages() {
        createImagePickerDialog();
    }

    /**
     * Call the {@link GalleryAdapter.OnDeleteImage#deleteImage(int)} method. The list of images
     * is updated too.
     *
     * @param positionSelected represent the image's position inside the image's list
     */
    private void deleteImage(int positionSelected) {
        Image image = mImages.get(positionSelected);
        resourcesIds.remove(image.getId());
        mAdapter.deleteImage(positionSelected);
        this.mImages.remove(positionSelected);
        // double check in case some image was deleted at the carousel. So, the builder needs to be updated.
        if (getActivity() instanceof CreatePlantActivity) {
            updateImagesFromBuilder(mImages, true);
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mPhotoGalleryPresenter.destroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPhotoGalleryPresenter.setView(new WeakReference<>(this));
    }

    @Override
    public void onShowImages(int pictureSelected) {

        Intent intent = new Intent(getActivity(), CarouselActivity.class);
        intent.putExtra(CarouselActivity.PICTURE_SELECTED_KEY, pictureSelected);
        intent.putParcelableArrayListExtra(IMAGES_KEY, mImages);

        this.startActivityForResult(intent, CAROUSEL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAROUSEL_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            if (data != null) {

                ArrayList<Image> images = data.getParcelableArrayListExtra(IMAGES_KEY);
                imagesFilesPaths = getImagesFilesPaths(images);

                //update adapter gallery
                mAdapter.setImagesPath(imagesFilesPaths);

                //update images from builder
                this.mImages = getImagesFromFilesPaths(imagesFilesPaths);

                if (getActivity() instanceof CreatePlantActivity) {
                    updateImagesFromBuilder(images, false);
                }
            }
        }
    }

    @Override
    public void loadImages(Collection<Image> images) {
        this.mImages.addAll(addFilesToImages(images));
        if (getActivity() instanceof CreatePlantActivity) {
            updateImagesFromBuilder(images, false);
        }
    }

    public ArrayList<Image> getImages() {
        return mImages;
    }

    /**
     * Create file using file's paths
     *
     * @param images List of images
     * @return List of images with its file
     */
    private Collection<Image> addFilesToImages(Collection<Image> images) {
        for (Image image : images) {
            image.setFile(new File(image.getUrl()));
        }
        return images;
    }

    public ArrayList<String> getResourcesIds() {
        return (ArrayList<String>) resourcesIds;
    }

    @Override
    protected void move() {
        super.move();
    }

    private ArrayList<Image> getImagesFromFilesPaths(List<String> paths) {
        ArrayList<Image> images = new ArrayList<>();

        for (String path : paths) {
            Image image = new Image();
            image.setUrl(path);
            image.setFile(new File(path));
            images.add(image);
        }
        return images;
    }

    /**
     * Get all the images path from the parcelable image list.
     *
     * @param images Images
     * @return paths images folder path
     */
    private ArrayList<String> getImagesFilesPaths(List<Image> images) {

        ArrayList<String> paths = new ArrayList<>();

        for (Image image : images) {
            if (!TextUtils.isEmpty(image.getThumbnailUrl())) {
                // if the image is retrieved from DB or Api, there should have an Thumbnail url set
                paths.add(image.getThumbnailUrl());
            } else {
                paths.add(image.getUrl());
            }
        }
        return paths;
    }

    /**
     * Create a dialog with a Chooser Options in order to take a picture or pick some images
     */
    private void createImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.select_image_source_tittle)
                .setItems(R.array.image_source_array, (dialog, which) -> {
                            if (which == 0) {
                                takePicture();
                            } else {
                                takePhotosFromGallery();
                            }
                        }
                );
        builder.create().show();
    }

    /**
     * Take pictures using RxPaparazzo lib.
     */
    private void takePicture() {
        RxPaparazzo.takeImage(this)
                .size(new CustomMaxSize())
                .usingCamera()
                .subscribe(response -> {
                    if (response.resultCode() != Activity.RESULT_OK) {
                        response.targetUI().showUserCanceled();
                        return;
                    }

                    ArrayList<String> list = new ArrayList<>();
                    list.add(response.data());
                    response.targetUI().loadImages(list);
                });
    }

    /**
     * Pick some images from gallery using RxPaparazzo lib.
     */
    private void takePhotosFromGallery() {
        RxPaparazzo.takeImages(this)
                .size(new CustomMaxSize())
                .usingGallery()
                .subscribe(response -> {
                    if (response.resultCode() != Activity.RESULT_OK) {
                        response.targetUI().showUserCanceled();
                        return;
                    }
                    response.targetUI().loadImages(response.data());
                });
    }

    /**
     * Notify to the gallery's adapter about the files paths
     *
     * @param filesPaths files paths
     */
    public void loadImages(List<String> filesPaths) {

        //update list of images files paths
        this.imagesFilesPaths.addAll(filesPaths);

        //update gallery with new images
        mGallery.setVisibility(View.VISIBLE);
        mAdapter.addImagesPaths(filesPaths);

        //add images to the builder plant
        mPhotoGalleryPresenter.getImagesList(filesPaths);
    }

    public void showUserCanceled() {
        Toast.makeText(getActivity(), getString(R.string.user_canceled), Toast.LENGTH_SHORT).show();
    }
}
