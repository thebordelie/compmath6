package com.example.compmath6;

import com.example.compmath6.config.Lab6Config;
import com.example.compmath6.gui.GUI;
import com.example.compmath6.method.AdamsMethod;
import com.example.compmath6.method.ImprovedEulerMethod;
import com.example.compmath6.method.RungeKuttaMethod;
import com.example.compmath6.model.InputData;
import com.example.compmath6.model.Polynomial;
import com.example.compmath6.model.equation.AbstractFunction;
import com.example.compmath6.model.equation.AlgebraicFunction;
import com.example.compmath6.model.equation.Equation;
import com.example.compmath6.model.equation.TranscendentalEquation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

@SpringBootApplication
public class Compmath6Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Lab6Config.class);
//        SpringApplication.run(Compmath6Application.class, args);
        GUI gui = context.getBean(GUI.class);

    }


}
