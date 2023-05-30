package com.example.compmath6.method;

import com.example.compmath6.model.InputData;
import com.example.compmath6.model.TableOfValues;
import com.example.compmath6.model.equation.Equation;

import java.util.HashMap;

abstract public class AbstractMethod implements Method{
    private int rank;

    public AbstractMethod(int rank) {
        this.rank = rank;
    }

    @Override
    public TableOfValues solveCauchyProblem(InputData data) {
        double approximation = data.getApproximation();
        double step = data.getStep();
        double startX = data.getStartX();
        double endX = data.getEndX();
        double startY = data.getStartY();
        Equation equation = data.getEquation();
        while (true) {
            TableOfValues valuesForCurrentStep = findTable(startX, endX, startY, step, equation);
            TableOfValues valuesForSmallerStep = findTable(startX, endX, startY, step / 2, equation);
            HashMap<Double, Double> firstTable = valuesForCurrentStep.getMap();
            HashMap<Double, Double> secondTable = valuesForSmallerStep.getMap();
            double maxDifference = Double.MIN_VALUE;
            for (Double xValue : firstTable.keySet()) {
                try {
                    double y1 = firstTable.get(xValue);
                    double y2 = secondTable.get(xValue);
                    maxDifference = Math.max(Math.abs((y1 - y2) / (Math.pow(2, rank) - 1)), maxDifference);
                }
                catch (NullPointerException e) {continue;}

            }
            if (maxDifference <= approximation) return valuesForCurrentStep;
            step = step / 2;
        }
    }
    abstract public TableOfValues findTable(double startX, double endX, double startY, double step, Equation equation);
}
