package com.dat.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseRepository<T, K extends Serializable> {
    List<T> getAll(Map<String, String> params);

    Long count();

    boolean addOrUpdate(T t);

    T getById(K id);

    void delete(K id);
}
