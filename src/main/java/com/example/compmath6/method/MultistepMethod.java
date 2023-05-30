package com.example.compmath6.method;

import com.example.compmath6.model.InputData;
import com.example.compmath6.model.TableOfValues;
import com.example.compmath6.model.equation.Equation;
import lombok.Data;

import java.util.List;

@Data
abstract public class MultistepMethod implements Method {
    protected final RungeKuttaMethod rungeKuttaMethod;

    public MultistepMethod() {
        this.rungeKuttaMethod = new RungeKuttaMethod();
    }

    abstract public TableOfValues findTable(double startX, double endX, double startY, double step, Equation equation);

    @Override
    public TableOfValues solveCauchyProblem(InputData data) {
        double approximation = data.getApproximation();
        double step = data.getStep();
        double startX = data.getStartX();
        double endX = data.getEndX();
        double startY = data.getStartY();
        Equation equation = data.getEquation();
        Equation correctEquation = data.getCorrectEquation();
        while (true) {
            double maxDifference = Double.MIN_VALUE;
            TableOfValues values = findTable(startX, endX, startY, step, equation);
            List<Double> xValues = values.getXValues();
            List<Double> yValues = values.getYValues();
            for (int index = 0; index < values.getTableSize(); index++) {
                maxDifference = Math.max(Math.abs(correctEquation.calculateEquationValue(xValues.get(index)) - yValues.get(index)), maxDifference);
            }
            if (maxDifference <= approximation) return values;
            step /= 2;
        }
    }
}
