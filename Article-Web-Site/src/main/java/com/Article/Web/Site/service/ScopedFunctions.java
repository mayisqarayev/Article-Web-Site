package com.Article.Web.Site.service;

@FunctionalInterface
public interface ScopedFunctions<T> {
    T apply(T t);
}
