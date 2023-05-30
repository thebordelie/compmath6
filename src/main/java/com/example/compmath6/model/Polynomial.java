package com.example.compmath6.model;

import com.example.compmath6.model.equation.AbstractFunction;
import lombok.Data;

import java.util.ArrayList;

public class Polynomial extends AbstractFunction {

    private ArrayList<Block> functions;
    private ArrayList<Double> coefficients;
    public String information;

    public Polynomial(String information) {
        functions = new ArrayList<>();
        coefficients = new ArrayList<>();
    }

    @Override
    public double calculateValue(double x) {
        return 0;
    }

    public void addBlock(double coefficient, ArrayList<AbstractFunction> functions) {
        this.functions.add(new Block(coefficient, functions));
        this.coefficients.add(coefficient);
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public double calculateValue(double x, double y) {
        double value = 0;
        for (Block block : functions) {
            value += block.calculateValue(x, y);
        }
        return value;
    }

    @Override
    public ArrayList<AbstractFunction> findDeclarative() {
        return null;
    }

    @Override
    public ArrayList<Double> getCoefficients() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        functions.forEach(function -> {
            builder.append(function.toString());
        });
        return builder.toString();
    }

    @Data
    public class Block {
        private double coefficient;
        private ArrayList<AbstractFunction> functions;
//        private String arg;

        public Block(double coefficient, ArrayList<AbstractFunction> functions) {
            this.coefficient = coefficient;
            this.functions = functions;
//            this.arg = arg;
        }

        public double calculateValue(double x, double y) {
            double value = 1;
            for (AbstractFunction function : functions) {
                if (function.getArg().equals("x")) {
                    value *= function.calculateValue(x);
                }
                else value *= function.calculateValue(y);
            }
            return coefficient * value;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (coefficient == 0) return "";
            if (coefficient > 0) builder.append(" + ");
            else builder.append(" - ");
            if (coefficient != 1) builder.append(String.format("%.3f", Math.abs(coefficient))).append(" * ");
            for (int function = 0; function < functions.size(); function++) {
                builder.append(" (").append(functions.get(function)).append(" ) ");
                if (function != functions.size() - 1) builder.append(" * ");
            }
            return builder.toString();
        }
    }


}
