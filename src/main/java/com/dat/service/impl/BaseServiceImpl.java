package com.dat.service.impl;

import com.dat.repository.BaseRepository;
import com.dat.service.BaseService;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public abstract class BaseServiceImpl<T, K extends Serializable> implements BaseService<T, K> {
    private BaseRepository<T, K> repository;

    @Override
    public List<T> getAll(Map<String, String> params) {
        return repository.getAll(params);
    }

    @Override
    public Long count(Map<String, String> params) {
        return repository.count(params);
    }

    @Override
    public boolean addOrUpdate(T t) {
        return repository.addOrUpdate(t);
    }

    @Override
    public T getById(K id) {
        return repository.getById(id);
    }

    @Override
    public void delete(K id) {
        repository.delete(id);
    }
}
