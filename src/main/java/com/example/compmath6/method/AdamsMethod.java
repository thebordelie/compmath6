package com.example.compmath6.method;

import com.example.compmath6.model.TableOfValues;
import com.example.compmath6.model.equation.Equation;
import org.springframework.stereotype.Component;


@Component
public class AdamsMethod extends MultistepMethod {
    @Override
    public TableOfValues findTable(double startX, double endX, double startY, double step, Equation equation) {
        TableOfValues values = rungeKuttaMethod.findTable(startX, startX + 3 * step, startY, step, equation);
        if (values.getTableSize() != 4)
            values = rungeKuttaMethod.findTable(startX, startX + 4 * step, startY, step, equation);

        for (double currentX = values.getXValues().get(values.getXValues().size() - 1); currentX <= endX; currentX += step) {
            currentX = ((double) Math.round(currentX * 100000)) / 100000;
            double currentY = values.getYValues().get(values.getYValues().size() - 1);
            double[] difference = new double[4];
            for (int index = 1; index < 5; index++) {
                difference[index - 1] = equation.calculateEquationValue(values.getXValues().get(values.getXValues().size() - index), values.getYValues().get(values.getYValues().size() - index));
            }
            double deltaFi = difference[0] - difference[1];
            double delta2Fi = difference[0] - 2 * difference[1] + difference[2];
            double delta3Fi = difference[0] - 3 * difference[1] + 3 * difference[2] - difference[3];

            currentY = currentY + step * difference[0] + Math.pow(step, 2) * deltaFi / 2 + 5 * Math.pow(step, 3) * delta2Fi / 12 + 3 * Math.pow(step, 4) * delta3Fi / 8;
            values.addPoint(currentX, currentY);
        }
        return values;
    }
}
