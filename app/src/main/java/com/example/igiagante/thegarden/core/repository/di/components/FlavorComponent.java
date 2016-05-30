package com.example.igiagante.thegarden.core.repository.di.components;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.FlavorRepositoryManager;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, FlavorModule.class})
public interface FlavorComponent {

    FlavorRepositoryManager flavorRepositoryManager();

}