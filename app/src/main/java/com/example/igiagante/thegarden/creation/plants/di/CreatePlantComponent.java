package com.example.igiagante.thegarden.creation.plants.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ActivityComponent;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.MainDataFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.PhotoGalleryFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.presenter.PhotoGalleryPresenter;
import com.example.igiagante.thegarden.home.plants.presentation.PlantModule;
import com.example.igiagante.thegarden.home.plants.repository.restAPI.RestAPIRepository;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

/**
 * @author igiagante on 6/5/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CreationPlantModule.class})
public interface CreatePlantComponent extends ActivityComponent {

    void inject(PhotoGalleryFragment photoGalleryFragment);

    PhotoGalleryPresenter photoGalleryPresenter();
}
