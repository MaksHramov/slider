package com.example;

public class ImageAggregateFactory extends AbstractAggregateFactory {
    @Override
    public Aggregate createAggregate(String format) {
        return new ConcreteAggregate(format);
    }
}