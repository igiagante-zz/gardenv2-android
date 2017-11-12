package com.example.igiagante.thegarden.home.irrigations.usecase;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.IrrigationRepositoryManager;
import com.example.igiagante.thegarden.core.repository.realm.specification.irrigations.IrrigationSpecification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public class SaveIrrigationUseCase extends UseCase<Irrigation, Irrigation> {

    private final IrrigationRepositoryManager irrigationRepositoryManager;

    @Inject
    public SaveIrrigationUseCase(IrrigationRepositoryManager irrigationRepositoryManager,
                                 ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.irrigationRepositoryManager = irrigationRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(Irrigation irrigation) {

        IrrigationSpecification specification = new IrrigationSpecification();
        return this.irrigationRepositoryManager.add(irrigation, specification);
    }
}
