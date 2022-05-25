package service;

import java.sql.SQLException;
import java.util.List;

public interface GenaraDAO<T> {
    public void add(T t) throws SQLException;

    public T findById(int id);

    public List<T> findAll();
    public List<T> findByName(String key);

    public boolean delete(int id) throws SQLException;

    public boolean update(T t) throws SQLException;
}
