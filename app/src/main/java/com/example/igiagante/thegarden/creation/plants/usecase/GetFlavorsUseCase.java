package com.example.igiagante.thegarden.creation.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.FlavorRepositoryManager;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * @author Ignacio Giagante, on 30/5/16.
 */
@PerActivity
public class GetFlavorsUseCase extends UseCase<Flavor, Void> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final FlavorRepositoryManager flavorRepositoryManager;

    @Inject
    public GetFlavorsUseCase(@NonNull FlavorRepositoryManager flavorRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.flavorRepositoryManager = flavorRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        return flavorRepositoryManager.query(new Specification() {
            @Override
            public Flowable<RealmResults> toFlowable(Realm realm) {
                return null;
            }
        });
    }
}
