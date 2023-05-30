package com.example.compmath6.model.equation;

import lombok.Data;

import java.util.ArrayList;


@Data
public class AlgebraicFunction extends AbstractFunction {
    private ArrayList<Double> coefficients;
    private int rank;
    private String arg;

    public AlgebraicFunction() {
        super("x");
    }

    public AlgebraicFunction(int rank, ArrayList<Double> coefficients, String arg) {
        super(arg);
        this.coefficients = coefficients;
        this.rank = rank;
        this.arg = arg;
    }

    public AlgebraicFunction(int rank, String arg, double... coefficients) {
        super(arg);
        this.rank = coefficients.length != rank + 1 ? 0 : rank;
        if (rank != 0) {
            this.coefficients = new ArrayList<>();
            for (Double coefficient : coefficients) {
                this.coefficients.add(coefficient);
            }
        }
        this.arg = arg;
    }

    @Override
    public double calculateValue(double x) {
        int rank = this.rank;
        double value = 0;
        for (Double coefficient : coefficients) {
            if (rank <= 0) value += coefficient;
            else value += coefficient * Math.pow(x, rank);
            rank--;
        }
        return value;
    }

    @Override
    public ArrayList<AbstractFunction> findDeclarative() {
        AlgebraicFunction derivativeEquation;
        double[] coef = new double[rank];
        for (int counter = 0; counter < coef.length; counter++) {
            coef[counter] = coefficients.get(counter) * (rank - counter);
        }
        if (rank == 1) {
            derivativeEquation = new AlgebraicFunction(rank, arg, 0, coef[0]);
        } else derivativeEquation = new AlgebraicFunction(rank - 1, arg, coef);
        ArrayList<AbstractFunction> arrayList = new ArrayList<>();
        arrayList.add(derivativeEquation);
        return arrayList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int coefficientNumber = 0; coefficientNumber < rank + 1; coefficientNumber++) {
            if (coefficients.get(coefficientNumber) == 0) continue;
            if (coefficients.get(coefficientNumber) >= 0) {
                builder.append(" + ");
            } else {
                if (coefficients.get(coefficientNumber) != 0) builder.append(" - ");

            }
            switch (String.valueOf(Math.abs(coefficients.get(coefficientNumber)))) {
                case "1.0":
                    if (rank - coefficientNumber == 0) builder.append(Math.abs(coefficients.get(coefficientNumber)));
                    break;
                default:
                    builder.append(String.format("%.3f", Math.abs(coefficients.get(coefficientNumber))));
                    if (rank - coefficientNumber > 0) builder.append("*");

            }
            if (rank - coefficientNumber > 0) {
                builder.append(arg);
                if (rank - coefficientNumber != 1) {
                    builder.append("^").append(rank - coefficientNumber);
                }

            }


        }
        return builder.toString();
    }
}
