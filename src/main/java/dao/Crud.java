package dao;

import java.util.List;

public interface Crud <T, ID> {
    T findbyID(ID id);
    List<T> findAll();
    T save();
    void deleteByID(T entity);
}
