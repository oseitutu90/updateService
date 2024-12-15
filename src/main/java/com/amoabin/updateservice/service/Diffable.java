package com.amoabin.updateservice.service;


@FunctionalInterface
public interface Diffable<T> {
    boolean hasChanges(T other);
}
