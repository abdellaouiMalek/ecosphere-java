package tn.esprit.services;

import java.util.List;
public interface IService <T> {
    public void add (T t,int x );
    public List<T> getAll ();
    public void update (T t);
    public void delete (T t);}
import java.util.Set;

public interface IService <T>{
    public void add(T t) ;

    public void update(T t);

    void update(int id, Object o);

    public void delete(T t);


    public List<T> getAll();
    public T getOne(int id);
}
