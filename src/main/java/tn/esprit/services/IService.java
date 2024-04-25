package tn.esprit.services;

import java.util.List;
public interface IService <T> {
    public void add (T t);
    public List<T> getAll ();
    public void update (T t);
    public void delete (T t);}
