package com.example.compmath6.method;

import com.example.compmath6.model.TableOfValues;
import com.example.compmath6.model.equation.Equation;
import org.springframework.stereotype.Component;

@Component
public class RungeKuttaMethod extends AbstractMethod {

    public RungeKuttaMethod() {
        super(4);
    }

    @Override
    public TableOfValues findTable(double startX, double endX, double startY, double step, Equation equation) {
        TableOfValues values = new TableOfValues();
        double currentY = startY;
        for (double currentX = startX; currentX <= endX; currentX += step) {
            currentX = ((double) Math.round(currentX * 100000)) / 100000;
            values.addPoint(currentX, currentY);
            double k1 = step * equation.calculateEquationValue(currentX, currentY);
            double k2 = step * equation.calculateEquationValue(currentX + step / 2, currentY + k1 / 2);
            double k3 = step * equation.calculateEquationValue(currentX + step / 2, currentY + k2 / 2);
            double k4 = step * equation.calculateEquationValue(currentX + step, currentY + k3);
            currentY = currentY + (k1 + 2 * k2 + 2 * k3 + k4) / 6;

        }
        return values;
    }
}
