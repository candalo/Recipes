package br.com.candalo.recipes.base.data;


public interface DataSource<T> {

    void execute(T listener);

}
