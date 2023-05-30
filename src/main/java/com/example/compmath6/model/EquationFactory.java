package com.example.compmath6.model;

import com.example.compmath6.model.equation.Equation;

import java.util.ArrayList;

public interface EquationFactory {
    ArrayList<Equation> createEquations();

    Equation getCorrectEquation(int equationNumber, double x, double y);
}
