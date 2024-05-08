package tn.esprit.services;

import java.util.List;
public interface IService <T> {
    public void add (T t,int x );
    public List<T> getAll ();
    public void update (T t);
    public void delete (T t);}
