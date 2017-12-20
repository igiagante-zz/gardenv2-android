package com.example.igiagante.thegarden.creation.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultObserver;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.views.MainDataView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
@PerActivity
public class MainDataPresenter extends AbstractPresenter<MainDataView> {

    private final UseCase existPlantUseCase;

    @Inject
    public MainDataPresenter(@Named("existPlant") UseCase existPlantUseCase) {
        this.existPlantUseCase = existPlantUseCase;
    }

    public void existPlant(String plantName) {
        Plant plant = new Plant();
        plant.setName(plantName);
        existPlantUseCase.execute(plant, new MainDataPresenterObserver());
    }

    public void destroy() {
        this.existPlantUseCase.dispose();
        this.view = null;
    }

    private void notifyIfPlantExist(Boolean exist) {
        getView().informIfPlantExist(exist);
    }

    private final class MainDataPresenterObserver extends DefaultObserver<Boolean> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("ErrorResponse", e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Boolean exist) {
            MainDataPresenter.this.notifyIfPlantExist(exist);
        }
    }
}
