package dao;

import java.util.List;

public interface DAO<T> {

    /**
     * This method save record to database
     * @param object
     */

    void save(T object);

    /**
     * This method delete record from database by id
     * @param id
     */

    void deleteById(int id);

    /**
     * This method delete all records from database
     */

    void deleteAll();

    /**
     * This method return a random record by id
     */

    T getRandomId();

    /**
     * This method return list of random id's
     * @param
     */

    List<Integer> getRandomIds(int randomCount);

    /**
     * This method return the count of all records is the table
     */

    int getRecordsCount();

    /**
     * This method extract a single object from database by id
     * @return
     */

    T getById(int id);

    /**
     * This method extract a list of objects from the database by a List of Id's
     */

    List<T> getByIds(List<Integer> ids);
}
