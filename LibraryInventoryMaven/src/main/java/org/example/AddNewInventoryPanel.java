package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class AddNewInventoryPanel {

    // class variables including the panel the DatabaseControl variable and other constants
    public JPanel panel = new JPanel();
    private String nonApplicable = "N/A";
    private String applicable = "";
    private Color editable = Color.WHITE;
    private Color notEditable = Color.GRAY;
    private InventoryViewPanel inventoryPanel;
    private DatabaseControl control;

    public AddNewInventoryPanel(final DatabaseControl control, final InventoryViewPanel inventoryPanel) {
        this.control = control;
        this.inventoryPanel = inventoryPanel;
        // The following will be used when we get a real web cam
        // JPanel webcam = new WebcamPanel(Webcam.getDefault());
        // adding the UI compents that are neccesary
        final JCheckBox newCheckBox = new JCheckBox("Non Cataloged Inventory", false);
        final JTextField incomingAmount = new JTextField(applicable);
        final JTextField nameTextField = new JTextField(applicable);
        //nameTextField.setEditable(false);
        //nameTextField.setBackground(notEditable);
        // The next three lines are for demonstration purposes
        final JTextField barcodeTextField = new JTextField(applicable);
        barcodeTextField.setEditable(true);
        barcodeTextField.setBackground(editable);
        final JTextField description = new JTextField(nonApplicable);
        description.setEditable(false);
        description.setBackground(notEditable);
        final JTextField vendorId = new JTextField(nonApplicable);
        vendorId.setEditable(false);
        vendorId.setBackground(notEditable);
        final JTextField manufactorId = new JTextField(nonApplicable);
        manufactorId.setEditable(false);
        manufactorId.setBackground(notEditable);
        final JTextField reorderAmount = new JTextField(nonApplicable);
        reorderAmount.setEditable(false);
        reorderAmount.setBackground(notEditable);
        final JCheckBox resin = new JCheckBox("Resin");
        resin.setEnabled(false);
        final JCheckBox speciality = new JCheckBox("Special");
        speciality.setEnabled(false);
        JButton addInventory = new JButton("Add Inventory");
        // used to change the editable and background color and text of different checkboxes and
        // textfields based upon the
        newCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    /**
                     nameTextField.setEditable(true);
                     nameTextField.setBackground(editable);
                     nameTextField.setText(applicable);
                     */
                    description.setEditable(true);
                    description.setBackground(editable);
                    description.setText(applicable);
                    vendorId.setEditable(true);
                    vendorId.setBackground(editable);
                    vendorId.setText(applicable);
                    manufactorId.setEditable(true);
                    manufactorId.setBackground(editable);
                    manufactorId.setText(applicable);
                    reorderAmount.setEditable(true);
                    reorderAmount.setBackground(editable);
                    reorderAmount.setText(applicable);
                    resin.setEnabled(true);
                    speciality.setEnabled(true);
                } else {
                    /**
                     nameTextField.setEditable(false);
                     nameTextField.setText(nonApplicable);
                     nameTextField.setBackground(notEditable);
                     */
                    description.setEditable(false);
                    description.setText(nonApplicable);
                    description.setBackground(notEditable);
                    vendorId.setEditable(false);
                    vendorId.setText(nonApplicable);
                    vendorId.setBackground(notEditable);
                    vendorId.setText(nonApplicable);
                    manufactorId.setEditable(false);
                    manufactorId.setText(nonApplicable);
                    manufactorId.setBackground(notEditable);
                    reorderAmount.setEditable(false);
                    reorderAmount.setText(nonApplicable);
                    reorderAmount.setBackground(notEditable);
                    resin.setEnabled(false);
                    speciality.setEnabled(false);
                }
            }
        });
        // the logical for the button to add inventory to the database and then repopulate the inventory view panel
        // this also includes the logic associated with add rows of data to the inventory_in table
        // all database functions are held in DatabaseControl
        addInventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newCheckBox.isSelected()) {
                    String name = nameTextField.getText();
                    String descriptionText = description.getText();
                    String vendorIdText = vendorId.getText();
                    String manufactorIdText = manufactorId.getText();
                    int reorderAmountValue = Integer.parseInt(reorderAmount.getText());
                    String resinValue = "";
                    if(resin.isSelected()) {
                        resinValue = "Y";
                    } else {
                        resinValue = "N";
                    }
                    String specialityValue = "";
                    if (speciality.isSelected()) {
                        specialityValue = "Y";
                    } else {
                        specialityValue = "N";
                    }
                    int incomingAmountValue = Integer.parseInt(incomingAmount.getText());
                    String barcode = barcodeTextField.getText();
                    if (!barcode.equals("")) {
                        control.addInventory(incomingAmountValue, descriptionText, name, vendorIdText, manufactorIdText, reorderAmountValue, resinValue, specialityValue);
                        newCheckBox.setSelected(false);
                        incomingAmount.setText("");
                        int id = control.getIdFromName(name);
                        if (id != -1) {
                            inventoryPanel.addRow(new Inventory(id, incomingAmountValue, descriptionText, name, vendorIdText, manufactorIdText, reorderAmountValue, resin.isSelected(), speciality.isSelected()));
                        } else {
                            JLabel idErrorMessage = new JLabel("There was an error. Please try again later.");
                            panel.add(idErrorMessage);
                        }
                        control.addNewInventoryIn(barcode, incomingAmountValue, name);
                        nameTextField.setText("");
                        barcodeTextField.setText("");
                        inventoryPanel.getInventoryTable().repaint();
                        inventoryPanel.getPanel().repaint();
                    }
                } else {
                    String barcode = barcodeTextField.getText();
                    String name = nameTextField.getText();
                    Inventory inventory = control.getInventoryUsingName(name);
                    System.out.println("past first spot");
                    if (inventory != null) {
                        int amount = Integer.parseInt(incomingAmount.getText());
                        control.addNewInventoryIn(barcode, amount, name);
                        System.out.println("past second spot");
                        int currentAmount = control.getInventoryAmountFromInventoryUsingName(name);
                        if (currentAmount != -1) {
                            System.out.println("right before suspected spot");
                            control.updateInventoryAmountUsingName(name, currentAmount + amount);
                            System.out.println("made it past the spot");
                            inventoryPanel.getInventoryTable().setModel(inventoryPanel.makeDefaultTableModel());
                            nameTextField.setText(applicable);
                            incomingAmount.setText(applicable);
                            barcodeTextField.setText(applicable);
                        } else {
                            System.out.println("Something went wrong with the name and barcode");
                            createBadBarcodeAndName();
                        }
                    } else {
                        createBadBarcodeAndName();
                    }
                }
            }
        });
        // creating JLabels
        JLabel incomingAmountLabel = new JLabel("Incoming Amount:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel descriptionLabel = new JLabel("Description:");
        JLabel vendorIdLabel = new JLabel("Vendor ID:");
        JLabel manufactorIdLabel = new JLabel("Manufactor ID:");
        JLabel reoderAmountLabel = new JLabel("Reoder amount:");
        // The next line is for demonstartion purposes
        JLabel barcodeLabel = new JLabel("Barcode:");
        // adding components to the panel
        panel.add(newCheckBox);
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(descriptionLabel);
        panel.add(description);
        panel.add(incomingAmountLabel);
        panel.add(incomingAmount);
        panel.add(vendorIdLabel);
        panel.add(vendorId);
        panel.add(manufactorIdLabel);
        panel.add(manufactorId);
        panel.add(reoderAmountLabel);
        panel.add(reorderAmount);
        // The next 2 lines are for demonstration purposes
        panel.add(barcodeLabel);
        panel.add(barcodeTextField);
        panel.add(resin);
        panel.add(speciality);
        panel.add(addInventory);
        //panel.add(webcam);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    // used to get the panel to add it to the tabbed panel in ControlScreen class
    public JPanel getPanel() { return panel; }

    // used to create randomized barcodes was used in testing not being used currently
    public String randomizeBarcode() {
        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
                "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String result = "";
        for (int i = 0; i < 10; i++) {
            if(ThreadLocalRandom.current().nextInt(0, 2) == 1) {
                if (ThreadLocalRandom.current().nextInt(0, 2) == 1) {
                    result += alphabet[ThreadLocalRandom.current().nextInt(0, alphabet.length)];
                } else {
                    result += alphabet[ThreadLocalRandom.current().nextInt(0, alphabet.length)].toUpperCase(Locale.ROOT);
                }
            } else {
                result += digits[ThreadLocalRandom.current().nextInt(0, digits.length)];
            }
        }
        return result;
    }

    // used to create an error message if soemthing goes wrong
    public void createBadBarcodeAndName() {
        JLabel badSomething = new JLabel("Either the barcode or name is not in the system");
        panel.add(badSomething);
    }
}
