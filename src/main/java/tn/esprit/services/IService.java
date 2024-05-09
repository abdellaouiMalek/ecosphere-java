package tn.esprit.services;

import java.util.List;
public interface IService <T> {
    public void add (T t,int x );
    public List<T> getAll ();
    public void update (T t);
    public void delete (T t);

    public void add(T t) ;



    void update(int id, Object o);




    public T getOne(int id);
}
