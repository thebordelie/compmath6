package com.example.compmath6.method;

import com.example.compmath6.model.TableOfValues;
import com.example.compmath6.model.equation.Equation;
import org.springframework.stereotype.Component;

@Component
public class ImprovedEulerMethod extends AbstractMethod {

    public ImprovedEulerMethod() {
        super(2);
    }

    @Override
    public TableOfValues findTable(double startX, double endX, double startY, double step, Equation equation) {
        TableOfValues values = new TableOfValues();
        double currentY = startY;

        for (double currentX = startX; currentX <= endX; currentX += step) {
            values.addPoint(currentX, currentY);
            currentY = currentY + step * (equation.calculateEquationValue(currentX, currentY) + equation.calculateEquationValue(currentX + step, currentY + step * equation.calculateEquationValue(currentX, currentY))) / 2;
            currentX = ((double) Math.round(currentX * 100000)) / 100000;
        }
        return values;
    }


}
