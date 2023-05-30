package com.example.compmath6.model.equation;

import lombok.Data;

import java.util.ArrayList;

@Data
abstract public class AbstractFunction {
    private String arg;

    public AbstractFunction(String argument) {
        this.arg = argument;
    }

    public AbstractFunction(){
        this.arg = "x";
    }

    abstract public double calculateValue(double x);

    abstract public ArrayList<AbstractFunction> findDeclarative();

    abstract public ArrayList<Double> getCoefficients();


}
