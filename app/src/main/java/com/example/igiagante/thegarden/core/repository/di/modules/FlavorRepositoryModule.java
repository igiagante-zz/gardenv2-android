package com.example.igiagante.thegarden.core.repository.di.modules;

import android.content.Context;

import com.example.igiagante.thegarden.core.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
@Module
public class FlavorRepositoryModule {

    @Provides
    @PerActivity
    FlavorRepositoryManager provideFlavorRepositoryManager(Context context) {
        return new FlavorRepositoryManager(context);
    }
}
