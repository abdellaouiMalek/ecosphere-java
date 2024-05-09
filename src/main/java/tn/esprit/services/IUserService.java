package tn.esprit.services;

import tn.esprit.models.User;

import java.util.List;

public interface IUserService <T> {
    public void add(T t);

    public List<T> getAll();

    public T getById(int id);

    public void update(T t);

    public void delete(T t);

}

