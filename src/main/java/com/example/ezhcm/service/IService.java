package com.example.ezhcm.service;

import java.util.List;
import java.util.Optional;

public interface IService <T,ID>{
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T t);
    void delete(ID id);
}
