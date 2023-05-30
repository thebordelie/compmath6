package com.example.compmath6.model;

import com.example.compmath6.model.equation.Equation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputData {
    private double approximation;
    private double step;
    private double startX;
    private double endX;
    private double startY;
    private Equation equation;
    private Equation correctEquation;

}
