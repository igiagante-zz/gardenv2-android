package com.example.igiagante.thegarden.show_plant.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultObserver;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.show_plant.view.ShowPlantView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 18/8/16.
 */
@PerActivity
public class GetAttributesPresenter extends AbstractPresenter<ShowPlantView> {

    private final static String TAG = GetAttributesPresenter.class.getSimpleName();

    private final UseCase getAttributesUseCase;

    @Inject
    public GetAttributesPresenter(@Named("attributes") UseCase getAttributesUseCase) {
        this.getAttributesUseCase = getAttributesUseCase;
    }

    public void destroy() {
        this.getAttributesUseCase.dispose();
        this.view = null;
    }

    public void getAttributes() {
        this.getAttributesUseCase.execute(null, new GetAttributesObserver());
    }

    private void showAttributes(List<Attribute> attributes) {
        getView().loadAttributes(attributes);
    }

    private final class GetAttributesObserver extends DefaultObserver<List<Attribute>> {

        @Override
        public void onComplete() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(List<Attribute> attributes) {
            GetAttributesPresenter.this.showAttributes(attributes);
        }
    }
}
