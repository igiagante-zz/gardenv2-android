package com.example.igiagante.thegarden.creation.nutrients.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultObserver;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.nutrients.presentation.view.NutrientDetailView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public class NutrientDetailPresenter extends AbstractPresenter<NutrientDetailView> {

    private final UseCase existNutrientUseCase;

    @Inject
    public NutrientDetailPresenter(@Named("existNutrient") UseCase existNutrientUseCase) {
        this.existNutrientUseCase = existNutrientUseCase;
    }


    public void existNutrient(String nutrientName) {
        this.existNutrientUseCase.execute(nutrientName, new ExistNutrientObserver());
    }


    public void destroy() {
        this.existNutrientUseCase.dispose();
        this.view = null;
    }

    private void notifyIfNutrientExist(Boolean exist) {
        getView().notifyIfNutrientExist(exist);
    }

    private final class ExistNutrientObserver extends DefaultObserver<Boolean> {

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
            NutrientDetailPresenter.this.notifyIfNutrientExist(exist);
        }
    }
}
