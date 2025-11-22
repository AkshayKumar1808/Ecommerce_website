package com.akshay.project.project.service;

public interface CRUDOperation<T> {

	T addModel(T model);

	T updateModel(T model, Long ID);

	T getModel(Long Id);

	T deleteModel(Long Id);
}
