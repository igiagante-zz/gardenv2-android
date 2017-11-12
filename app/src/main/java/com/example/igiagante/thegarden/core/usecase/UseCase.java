package com.example.igiagante.thegarden.core.usecase;

import android.support.annotation.IntDef;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ignacio Giagante on 15/4/16.
 * Param Use case input
 */
public abstract class UseCase<T, Param> {

    /**
     * Determine the order of repository
     */
    @IntDef({LOCAL_REPOSITORY, REMOTE_REPOSITORY})
    public @interface RepositoryOrder {}
    public static final int LOCAL_REPOSITORY = 0;
    public static final int REMOTE_REPOSITORY = 1;

    protected List<Integer> repositoryOrder = new ArrayList<>();

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    protected UseCase(ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    protected List<Integer> getRepositoryOrder() {
        return repositoryOrder;
    }

    /**
     * Builds an {@link rx.Observable} which will be used when executing the current {@link UseCase}.
     */
    protected abstract Observable<T> buildUseCaseObservable(Param param);


    /**
     * Executes the current use case.
     *
     * @param params Parameters (Optional) used to build/execute this use case.
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     * by {@link #buildUseCaseObservable(Param)} ()} method.
     */
    @SuppressWarnings("unchecked")
    public void execute(Param params, DisposableObserver<T> observer) {

        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());

        disposables.add((observable.subscribeWith(observer)));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
