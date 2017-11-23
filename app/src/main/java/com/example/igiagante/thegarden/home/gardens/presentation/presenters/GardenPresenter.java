package com.example.igiagante.thegarden.home.gardens.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultObserver;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.gardens.presentation.view.GardenView;
import com.example.igiagante.thegarden.home.gardens.usecase.GetGardenTempAndHumd;
import com.example.igiagante.thegarden.home.plants.presentation.dataHolders.GardenHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
@PerActivity
public class GardenPresenter extends AbstractPresenter<GardenView> {

    private final static String TAG = GardenPresenter.class.getSimpleName();

    private final UseCase getGardenTempAndHumd;

    private final UseCase getGetGardensByUserUseCase;
    private final UseCase getGardensUseCase;
    private final UseCase getGardenUseCase;
    private final UseCase saveGardenUseCase;
    private final UseCase deleteGardenUseCase;
    private final UseCase existGardenUseCase;

    private final UseCase updateUserUseCase;

    @Inject
    public GardenPresenter(GetGardenTempAndHumd getGardenTempAndHumd,
                           @Named("gardens") UseCase getGardensUseCase,
                           @Named("getGardensByUser") UseCase getGetGardensByUserUseCase,
                           @Named("getGarden") UseCase getGardenUseCase,
                           @Named("saveGarden") UseCase saveGardenUseCase,
                           @Named("deleteGarden") UseCase deleteGardenUseCase,
                           @Named("existGarden") UseCase existGardenUseCase,
                           @Named("updateUser") UseCase saveUserUseCase) {

        this.getGardenTempAndHumd = getGardenTempAndHumd;

        this.getGardensUseCase = getGardensUseCase;
        this.getGetGardensByUserUseCase = getGetGardensByUserUseCase;
        this.saveGardenUseCase = saveGardenUseCase;
        this.deleteGardenUseCase = deleteGardenUseCase;
        this.getGardenUseCase = getGardenUseCase;
        this.existGardenUseCase = existGardenUseCase;

        this.updateUserUseCase = saveUserUseCase;
    }

    public void destroy() {
        this.getGardenTempAndHumd.dispose();
        this.getGardensUseCase.dispose();
        this.getGetGardensByUserUseCase.dispose();
        this.getGardenUseCase.dispose();
        this.saveGardenUseCase.dispose();
        this.deleteGardenUseCase.dispose();
        this.updateUserUseCase.dispose();
        this.view = null;
    }

    public void getGardensByUser(User user) {
        this.showViewLoading();
        this.getGetGardensByUserUseCase.execute(user, new GetGardensByUserObserver());
    }

    public void saveGarden(Garden garden) {
        this.saveGardenUseCase.execute(garden, new SaveGardenObserver());
    }

    public void deleteGarden(Garden garden) {
        this.deleteGardenUseCase.execute(garden, new DeleteGardenObserver());
    }

    public void getGarden(String gardenId) {
        this.showViewLoading();
        this.getGardenUseCase.execute(gardenId, new GetGardenObserver());
    }

    public void existsGarden(Garden garden) {
        this.existGardenUseCase.execute(garden, new ExistsGardenObserver());
    }

    public void updateUser(User user) {
        this.showViewLoading();
        this.updateUserUseCase.execute(user, new UpdateUserObserver());
    }

    public void getActualTempAndHumidity() {
        this.getGardenTempAndHumd.execute(null, new GetGardenTempAndHumidity());
    }

    public ArrayList<Garden> createGardenListFromGardenHolderList(List<GardenHolder> gardenHolders) {

        ArrayList<Garden> gardens = new ArrayList<>();

        if (gardenHolders != null) {
            for (int i = 0; i < gardenHolders.size(); i++) {
                gardens.add(gardenHolders.get(i).getModel());
            }
        }

        return gardens;
    }

    public GardenHolder createGardenHolder(Garden garden, int position) {
        GardenHolder gardenHolder = new GardenHolder();
        gardenHolder.setModel(garden);
        gardenHolder.setPosition(position);
        return gardenHolder;
    }

    private GardenHolder createGardenHolder(Garden garden) {
        GardenHolder gardenHolder = new GardenHolder();
        gardenHolder.setModel(garden);
        return gardenHolder;
    }

    private void showGardens(List<GardenHolder> gardens) {
        getView().loadGardens(gardens);
    }

    private void notifyIfGardenWasPersistedOrUpdated(Garden garden) {
        getView().notifyIfGardenWasPersistedOrUpdated(garden);
    }

    private void notifyIfGardenWasDeleted(Integer result) {
        getView().notifyIfGardenWasDeleted();
    }

    private void loadGarden(Garden garden) {
        getView().loadGarden(createGardenHolder(garden));
    }

    private void notifyIfGardenExists(boolean exists) {
        getView().notifyIfGardenExists(exists);
    }

    private void notifyIfUserWasUpdated(User user) {
        getView().notifyIfUserWasUpdated(user);
    }

    private void updateTemp(SensorTemp sensorTemp) {
        getView().updateTemp(sensorTemp);
    }

    private void showViewLoading() {
        getView().showLoading();
    }

    private void hideViewLoading() {
        getView().hideLoading();
    }

    private final class GetGardenTempAndHumidity extends DefaultObserver<SensorTemp> {

        @Override
        public void onComplete() {
            GardenPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(SensorTemp sensorTemp) {
            GardenPresenter.this.updateTemp(sensorTemp);
        }
    }

    private final class GetGardensByUserObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            GardenPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            GardenPresenter.this.hideViewLoading();
            Log.d("GardenPresenter: ", Thread.currentThread().getName());
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(User user) {
            GardenPresenter.this.showGardens(createGardenHolderList(user.getGardens()));
        }
    }

    /**
     * Create garden holder list
     *
     * @param gardens Gardens
     * @return gardenHolders
     */
    private ArrayList<GardenHolder> createGardenHolderList(List<Garden> gardens) {

        ArrayList<GardenHolder> gardenHolders = new ArrayList<>();

        if (gardens != null) {
            for (int i = 0; i < gardens.size(); i++) {
                gardenHolders.add(createGardenHolder(gardens.get(i), i));
            }
        }

        return gardenHolders;
    }

    private final class ExistsGardenObserver extends DefaultObserver<Boolean> {

        @Override
        public void onComplete() {
            GardenPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            GardenPresenter.this.hideViewLoading();
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Boolean exist) {
            GardenPresenter.this.notifyIfGardenExists(exist);
        }
    }

    private final class SaveGardenObserver extends DefaultObserver<Garden> {

        @Override
        public void onComplete() {
            GardenPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            GardenPresenter.this.hideViewLoading();
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Garden garden) {
            GardenPresenter.this.notifyIfGardenWasPersistedOrUpdated(garden);
        }
    }

    private final class DeleteGardenObserver extends DefaultObserver<Integer> {

        @Override
        public void onComplete() {
            GardenPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            GardenPresenter.this.hideViewLoading();
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(Integer result) {
            GardenPresenter.this.notifyIfGardenWasDeleted(result);
        }
    }

    private final class GetGardenObserver extends DefaultObserver<Garden> {

        @Override
        public void onComplete() {
            GardenPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            GardenPresenter.this.hideViewLoading();
            Log.d("GardenPresenter: ", Thread.currentThread().getName());
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Garden garden) {
            GardenPresenter.this.loadGarden(garden);
        }
    }

    private final class UpdateUserObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            GardenPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            GardenPresenter.this.hideViewLoading();
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(User user) {
            GardenPresenter.this.notifyIfUserWasUpdated(user);
        }
    }
}
