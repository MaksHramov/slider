package com.example;

public abstract class AbstractAggregateFactory {
    public abstract Aggregate createAggregate(String format);  // Фабричный метод
}