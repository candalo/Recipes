package br.com.candalo.recipes.base.data;


import java.util.List;

public interface Database<T> {

    void saveAll(List<T> model);

    List<T> getList();

    T get(int id);

}
