package com.example.compmath6.model;

import com.example.compmath6.model.equation.AlgebraicFunction;
import com.example.compmath6.model.equation.Equation;
import com.example.compmath6.model.equation.TranscendentalEquation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EquationFactoryImpl implements EquationFactory {
    private ArrayList<Equation> equations;

    public EquationFactoryImpl() {
        equations = new ArrayList<>();
        Equation equation = new Equation(0, new AlgebraicFunction(1, "y", 1, 0), new AlgebraicFunction(1, "x", 1, 0));
        Equation equation1 = new Equation(0, new AlgebraicFunction(1, "y", -2, 0), new AlgebraicFunction(2, "x", 1, 0, 0));
        Equation equation2 = new Equation(0, new TranscendentalEquation("cos", 1, new AlgebraicFunction(1, "x", 1, 0), "x"), new AlgebraicFunction(1, "y", 1, 0));
        equations.add(equation);
        equations.add(equation1);
        equations.add(equation2);
    }

    @Override
    public ArrayList<Equation> createEquations() {
        return equations;
    }

    @Override
    public Equation getCorrectEquation(int equationNumber, double x, double y) {
        if (equationNumber > equations.size() - 1) return null;
        Equation equation = null;
        double coefficient = 0;
        switch (equationNumber) {
            case 0:
                coefficient = (y + x + 1) / Math.exp(x);
                equation = new Equation(0, new TranscendentalEquation("e", coefficient, new AlgebraicFunction(1, "x", 1, 0), "x"), new AlgebraicFunction(1, "x", -1, -1));
                break;
            case 1:
                coefficient = (y - x * x / 2 + x / 2 - 0.25) * Math.exp(2 * x);
                equation = new Equation(0, new TranscendentalEquation("e", coefficient, new AlgebraicFunction(1, "x", -2, 0), "x"), new AlgebraicFunction(2, "x", 0.5, -0.5, 0.25));
                break;
            case 2:
                coefficient = (y - Math.sin(x)/2 + Math.cos(x) / 2)/ Math.exp(x);
                equation = new Equation(0, new TranscendentalEquation("e", coefficient, new AlgebraicFunction(1, "x", 1, 0), "x"), new TranscendentalEquation("sin", 0.5, new AlgebraicFunction(1, "x", 1, 0), "x"), new TranscendentalEquation("cos", -0.5, new AlgebraicFunction(1, "x", 1, 0), "x"));
                break;
        }
        return equation;
    }
}
