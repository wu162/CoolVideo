package com.example.coolvideo.utils;

public interface MyObservable {
    public void addObserver(MyObserver observer);
    public void notifyObservers(Object o);
}
