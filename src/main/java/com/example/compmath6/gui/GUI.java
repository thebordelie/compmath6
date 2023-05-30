package com.example.compmath6.gui;

import com.example.compmath6.method.Method;
import com.example.compmath6.model.EquationFactory;
import com.example.compmath6.model.EquationFactoryImpl;
import com.example.compmath6.model.InputData;
import com.example.compmath6.model.TableOfValues;
import com.example.compmath6.model.equation.Equation;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class GUI extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox<String> comboBox1;
    private JComboBox<String> comboBox2;
    private EquationFactory factory;
    private List<Method> methods;

    @Autowired
    public GUI(EquationFactory equationFactory, Method... methods) {
        factory = equationFactory;
        this.methods = Arrays.asList(methods);

        setTitle("Лабораторная работа №6");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание панели содержимого
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));

        // Создание компонентов
        JLabel label1 = new JLabel("x0 =  ");
        textField1 = new JTextField();
        JLabel xn = new JLabel("xn =  ");
        textField5 = new JTextField();
        JLabel label2 = new JLabel("y(x0) = ");
        textField2 = new JTextField();
        JLabel label3 = new JLabel("h (Шаг) = ");
        textField3 = new JTextField();

        JLabel eps = new JLabel("e = ");
        textField4 = new JTextField();

        JLabel label4 = new JLabel("Уравнение y' = ");
        ArrayList<Equation> equations = factory.createEquations();
        String[] options1 = new String[equations.size()];
        int counter = 0;
        for (Equation equation : equations) {
            options1[counter] = equation.toString();
            counter++;
        }

        comboBox1 = new JComboBox<>(options1);

        JLabel label5 = new JLabel("Метод решения:");
        String[] options2 = {" Усовершенствованный метод Эйлера", "Метод Рунге-Кутта 4- го порядка", "Метод Адамса"};
        comboBox2 = new JComboBox<>(options2);

        // Добавление компонентов на панель
        panel.add(label1);
        panel.add(textField1);
        panel.add(xn);
        panel.add(textField5);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(eps);
        panel.add(textField4);
        panel.add(label4);
        panel.add(comboBox1);
        panel.add(label5);
        panel.add(comboBox2);

        JButton button = new JButton("Выполнить");
        button.addActionListener(e -> createTable());
        panel.add(button);
        add(panel);
        setVisible(true);
    }

    private void createTable() {
        try {
            double x = Double.parseDouble(textField1.getText().replaceAll(",", "."));
            double xn = Double.parseDouble(textField5.getText().replaceAll(",", "."));
            double y = Double.parseDouble(textField2.getText().replaceAll(",", "."));
            double step = Double.parseDouble(textField3.getText().replaceAll(",", "."));
            double approximation = Double.parseDouble(textField4.getText().replaceAll(",", "."));
            if (step <= 0 || approximation <= 0 || x > xn) throw new NumberFormatException();
            TableOfValues values = new TableOfValues();
            values.addPoint(x, y);
            Equation correctEquation = factory.getCorrectEquation(comboBox1.getSelectedIndex(), x, y);
            processData(new InputData(approximation, step, x, xn, y, factory.createEquations().get(comboBox1.getSelectedIndex()), correctEquation));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(GUI.this, "Некорректно введены данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void processData(InputData data) {
        SwingWorker<TableOfValues, Void> worker = new SwingWorker<>() {
            private int index;
            @Override
            protected TableOfValues doInBackground() {
                index = comboBox2.getSelectedIndex();
                return methods.get(index).solveCauchyProblem(data);
            }

            @Override
            protected void done() {
                TableOfValues values;
                try {
                    values = get();
                    setDefaultConfiguration(new Table(values));
                    ArrayList<Equation> equations = new ArrayList<>();
                    data.getCorrectEquation().setInformation("График точного решения");
                    equations.add(data.getCorrectEquation());
                    setDefaultConfiguration(new Graph("Вывод", "Графики", values, equations));
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(GUI.this, "Ошибка при выполнении, попробуйте уменьшить точность", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void setDefaultConfiguration(JFrame frame) {
                frame.pack();
                frame.setDefaultCloseOperation(ApplicationFrame.DISPOSE_ON_CLOSE);
                RefineryUtilities.centerFrameOnScreen(frame);
                frame.setVisible(true);
            }
        };
        worker.execute();
    }
}
