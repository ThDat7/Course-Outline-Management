package com.dat.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T, K extends Serializable> {

    List<T> getAll(Map<String, String> params);

    Long count(Map<String, String> params);

    boolean addOrUpdate(T t);

    T getById(K id);

    void delete(K id);
}
