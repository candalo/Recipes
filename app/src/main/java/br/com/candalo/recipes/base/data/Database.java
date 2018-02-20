package br.com.candalo.recipes.base.data;


public interface Database<T> {

    void save(T model);

    T get();
}
