package com.example.igiagante.thegarden.home.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultObserver;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.plants.presentation.view.PlantListView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 5/5/16.
 */
@PerActivity
public class PlantListPresenter extends AbstractPresenter<PlantListView> {

    private final static String TAG = PlantListPresenter.class.getSimpleName();

    private final UseCase getPlantListUseCase;

    private final UseCase deletePlantUseCase;

    @Inject
    public PlantListPresenter(@Named("plantList") UseCase getPlantListUseCase,
                              @Named("deletePlant") UseCase deletePlantUseCase) {
        this.getPlantListUseCase = getPlantListUseCase;
        this.deletePlantUseCase = deletePlantUseCase;
    }

    public void destroy() {
        this.getPlantListUseCase.dispose();
        this.deletePlantUseCase.dispose();
        this.view = null;
    }

    /**
     * Delete a plant from one garden
     *
     * @param plantId Plant Id
     */
    public void deletePlant(String plantId) {
        this.deletePlantUseCase.execute(plantId, new DeletePlantObserver());
    }

    private void notifyPlantWasDeleted(String plantId) {
        getView().notifyPlantWasDeleted(plantId);
    }


    private final class DeletePlantObserver extends DefaultObserver<Object> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(Object result) {
            if (result instanceof String) {
                PlantListPresenter.this.notifyPlantWasDeleted((String) result);
            }
        }
    }
}
