package com.example.igiagante.thegarden.core.repository;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author Ignacio Giagante, on 15/4/16.
 */
public interface Repository<T> {

    String DATABASE_NAME_DEV = "garden.realm";
    String DATABASE_NAME_TEST = "garden-test.realm";

    /**
     * Return a resource using the id
     *
     * @param id Object id
     * @return Observable<T>
     */
    Observable<T> getById(String id);

    /**
     * Return a resource using the name
     *
     * @param name Name of the resource
     * @return Observable<T>
     */
    Observable<T> getByName(String name);

    /**
     * Return an Object's id which was added
     *
     * @param item Object to be inserted into the repository
     * @return Observable<T> The Observable contains an object
     */
    Observable<T> save(T item, boolean update);

    /**
     * Return the number of objects which were added.
     *
     * @param items Objects to be inserted into the repository
     * @return Observable<Integer> The Observable contains the number of objects added
     */
    Observable<List<T>> add(Iterable<T> items);


    /**
     * Return an observable with the integer, which indicates if the resource was deleted or not.
     *
     * @param id Id from Object to be deleted into the repository
     * @return Observable<Integer>
     */
    Observable<Boolean> remove(String id);


    void removeAll();

    /**
     * Return an observable a list of resources.
     *
     * @return Observable<List<T>>
     */
    Observable<List<T>> getAll();

    /**
     * Return an observable a list of resources.
     *
     * @param specification {@link Specification}
     * @return Observable<List<T>>
     */
    Observable<List<T>> query(Specification specification);
}