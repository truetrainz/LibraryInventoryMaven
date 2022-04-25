package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EstimateViewPanel {
    private JPanel jPanel = new JPanel();
    private static final double standardFilament = 0.12;
    private static final double specialFilament = 0.25;
    private static final double setupModel = 1.00;
    private static final double standardResin = 0.32;
    private static final double specialResin = 0.37;
    private static final double resinSetupAndCleaningAverageTime = 10.00;
    private static final double resinSetupAndCleaningPer5Minutes = 2.00;
    private static final double resinSupplyCharge = 1.00;

    public EstimateViewPanel(final DatabaseControl control) {
        ArrayList<String> names = control.getMaterialNames();
        final JComboBox comboBox = new JComboBox(transformArrayList(names));
        final JLabel issueLabel = new JLabel();
        issueLabel.setVisible(false);
        JLabel gramLabel = new JLabel("Grams:");
        final JLabel totalLabel = new JLabel("Total:");
        totalLabel.setVisible(false);
        final JTextField gramTextField = new JTextField();
        gramTextField.setPreferredSize(new Dimension(200, 24));
        //gramTextField.setSize(new Dimension(50, 30));
        jPanel.add(comboBox, BorderLayout.CENTER);
        jPanel.add(gramLabel, BorderLayout.CENTER);
        jPanel.add(gramTextField, BorderLayout.CENTER);
        jPanel.add(totalLabel, BorderLayout.CENTER);
        JButton calculate = new JButton("Calculate");
        calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String gramsString = gramTextField.getText();
                    gramsString.trim();
                    double grams = Double.parseDouble(gramsString);
                    String component = comboBox.getSelectedItem().toString();
                    System.out.println("component -> " + component);
                    Inventory inventory = control.getInventoryUsingName(component);
                    boolean special = inventory.getSpecaility();
                    boolean resin = inventory.getResin();
                    if (!special && !resin) {
                        double total = 0;
                        total += grams * standardFilament;
                        total += setupModel;
                        totalLabel.setText("Total: " + total + "$");
                        totalLabel.setVisible(true);
                    } else if(special && !resin) {
                        double total = 0;
                        total += grams * specialFilament;
                        total += setupModel;
                        totalLabel.setText("Total: " + total + "$");
                        totalLabel.setVisible(true);
                    } else if (!special && resin) {
                        double total = 0;
                        total += resinSetupAndCleaningAverageTime / 5 * resinSetupAndCleaningPer5Minutes;
                        total += grams * standardResin;
                        total += resinSupplyCharge;
                        totalLabel.setText("Total: " + total + "$");
                        totalLabel.setVisible(true);
                    } else if (special && resin) {
                        double total = 0;
                        total += total += resinSetupAndCleaningAverageTime / 5 * resinSetupAndCleaningPer5Minutes;
                        total += grams * specialResin;
                        total += resinSupplyCharge;
                        totalLabel.setText("Total: " + total + "$");
                        totalLabel.setVisible(true);
                    }
                    issueLabel.setVisible(false);
                } catch (Exception exception){
                    exception.printStackTrace();
                    System.out.println(gramTextField.getText());
                    issueLabel.setText("Something went wrong, please try again");
                    issueLabel.setVisible(true);
                }
            }
        });
        jPanel.add(calculate, BorderLayout.CENTER);
        jPanel.add(issueLabel);
        jPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
    }

    public String[] transformArrayList(ArrayList<String> arrayList) {
        String[] result = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            result[i] = arrayList.get(i);
        }
        return result;
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    public Double parseToDouble(String input) {
        String working = input.trim();
        int position = working.indexOf('.');
        double value = 0;
        if (position != -1) {
            for (int i = 0; i < input.length(); i++) {
                if (i == 0 && Character.isDigit(working.charAt(i))) {
                    value += Character.getNumericValue(working.charAt(i));
                } else if (i != 0 && Character.isDigit(working.charAt(i))){
                    value *= 10;
                    value += Character.getNumericValue(working.charAt(i));
                } else {
                    return -1.0;
                }
            }
        } else {
            boolean found = false;
            int positionAfterFound = 0;
            for (int i = 0; i < input.length(); i++) {
                if (i == 0 && Character.isDigit(working.charAt(i))) {
                    value += Character.getNumericValue(working.charAt(i));
                } else if (i == 0 && working.charAt(i) == '.') {
                    found = true;
                    positionAfterFound += 1;
                } else if (i != 0 && Character.isDigit(working.charAt(i)) && found == false) {
                    value *= 10;
                    value += Character.getNumericValue(working.charAt(i));
                } else if (i != 0 && Character.isDigit(working.charAt(i)) && found == true) {
                    value += Character.getNumericValue(working.charAt(i)) / Math.pow(10, positionAfterFound);
                    positionAfterFound += 1;
                } else if (i != 0 && working.charAt(i) == '.') {
                    found = true;
                    positionAfterFound += 1;
                } else {
                    return -1.0;
                }
            }
        }
        return value;
    }
}
