package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveInventoryPanel {

    private JPanel panel = new JPanel();

    public RemoveInventoryPanel(final DatabaseControl control, final InventoryViewPanel inventoryPanel) {
        JLabel nameLabel = new JLabel("Name:");
        final JTextField nameTextField = new JTextField("");
        JLabel barcodeLabel = new JLabel("Barcode:");
        final JTextField barcodeTextField = new JTextField("");
        JLabel amountLabel = new JLabel("Amount:");
        final JTextField amountTextField = new JTextField("");
        JButton removeInventoryButton = new JButton("Remove Inventory");
        removeInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameTextField.getText().equals("") && !barcodeTextField.getText().equals("") && !amountTextField.getText().equals("")) {
                    String barcode = barcodeTextField.getText();
                    String name = nameTextField.getText();
                    InventoryIn inventoryIn = control.getInventoryInFromBarcode(barcode);
                    if (inventoryIn != null) {
                        Inventory inventory = control.getInventoryUsingName(name);
                        control.updateInventoryAmountUsingName(name, inventory.getAmount() - inventoryIn.getAmount());
                        control.removeFromInventoryIn(inventory.getId());
                        inventoryPanel.getInventoryTable().setModel(inventoryPanel.makeDefaultTableModel());
                        nameTextField.setText("");
                        barcodeTextField.setText("");
                        amountTextField.setText("");
                    }
                }
            }
        });
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(barcodeLabel);
        panel.add(barcodeTextField);
        panel.add(amountLabel);
        panel.add(amountTextField);
        panel.add(removeInventoryButton);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public JPanel getPanel() {
        return panel;
    }
}
