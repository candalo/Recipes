package br.com.candalo.recipes.base.view;


public interface Presenter<T> {
    void attachTo(T view);
    void destroy();
}
