package com.example.igiagante.thegarden.home.irrigations.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultObserver;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.irrigations.presentation.holders.NutrientHolder;
import com.example.igiagante.thegarden.home.irrigations.presentation.view.IrrigationDetailView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
@PerActivity
public class IrrigationDetailPresenter extends AbstractPresenter<IrrigationDetailView> {

    private final static String TAG = IrrigationDetailPresenter.class.getSimpleName();

    private final UseCase saveIrrigationUseCase;
    private final UseCase getNutrientsUseCase;
    private final UseCase updateGardenWithIrrigationUseCase;

    @Inject
    public IrrigationDetailPresenter(@Named("saveIrrigation") UseCase saveIrrigationUseCase,
                                     @Named("getNutrients") UseCase getNutrientsUseCase,
                                     @Named("updateGardenWithIrrigation") UseCase updateGardenWithIrrigationUseCase) {
        this.saveIrrigationUseCase = saveIrrigationUseCase;
        this.getNutrientsUseCase = getNutrientsUseCase;
        this.updateGardenWithIrrigationUseCase = updateGardenWithIrrigationUseCase;
    }

    public void destroy() {
        this.saveIrrigationUseCase.dispose();
        this.getNutrientsUseCase.dispose();
        this.updateGardenWithIrrigationUseCase.dispose();
        this.view = null;
    }

    public void saveIrrigation(Irrigation irrigation) {
        this.saveIrrigationUseCase.execute(irrigation, new SaveIrrigationObserver());
    }

    public void getNutrients() {
        this.getNutrientsUseCase.execute(null, new NutrientsObserver());
    }

    public void updateGarden(Garden garden) {
        this.updateGardenWithIrrigationUseCase.execute(garden, new UpdateGardenObserver());
    }

    private void loadNutrients(List<NutrientHolder> nutrients) {
        getView().loadNutrients(nutrients);
    }

    private void notifyIfIrrigationWasPersistedOrUpdated(Irrigation irrigation) {
        getView().notifyIfIrrigationWasPersistedOrUpdated(irrigation);
    }

    private void notifyIfGardenWasUpdated(Garden garden) {
        getView().notifyIfGardenWasUpdated(garden);
    }

    private final class SaveIrrigationObserver extends DefaultObserver<Irrigation> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Irrigation irrigation) {
            IrrigationDetailPresenter.this.notifyIfIrrigationWasPersistedOrUpdated(irrigation);
        }
    }

    private final class NutrientsObserver extends DefaultObserver<List<Nutrient>> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(List<Nutrient> nutrients) {
            IrrigationDetailPresenter.this.loadNutrients(createNutrientHolderList(nutrients));
        }
    }

    private final class UpdateGardenObserver extends DefaultObserver<Garden> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Garden garden) {
            IrrigationDetailPresenter.this.notifyIfGardenWasUpdated(garden);
        }
    }

    public ArrayList<NutrientHolder> createNutrientHolderList(List<Nutrient> nutrients) {
        ArrayList<NutrientHolder> nutrientHolders = new ArrayList<>();
        for (Nutrient nutrient : nutrients) {
            NutrientHolder nutrientHolder = new NutrientHolder();
            nutrientHolder.setModel(nutrient);
            nutrientHolders.add(nutrientHolder);
        }
        return nutrientHolders;
    }

}
