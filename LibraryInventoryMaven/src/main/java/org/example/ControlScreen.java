package org.example;

import javax.swing.*;
import java.awt.*;

public class ControlScreen {

    JFrame frame;

    public ControlScreen(DatabaseControl databaseControl) {
        // creating overall pane
        frame = new JFrame("");
        // making tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        // creating panels that will go into the tabbed pane
        InventoryViewPanel inventoryViewPanel = new InventoryViewPanel(databaseControl);
        JPanel inventory = inventoryViewPanel.getPanel();
        JPanel estimateViewPanel = new EstimateViewPanel(databaseControl).getJPanel();
        JPanel addNewInventoryPanel = new AddNewInventoryPanel(databaseControl, inventoryViewPanel).getPanel();
        JPanel removeInventoryPanel = new RemoveInventoryPanel(databaseControl, inventoryViewPanel).getPanel();
        // adding panels to the tabbed pane
        tabbedPane.add("Inventory", inventory);
        tabbedPane.add("Estimate", estimateViewPanel);
        tabbedPane.add("New Inventory", addNewInventoryPanel);
        tabbedPane.add("Remove Inventory", removeInventoryPanel);
        // adding tabbed pane to the frame
        frame.add(tabbedPane, BorderLayout.CENTER);
        // taking care of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setVisible(true);
    }

    public JFrame getFrame() { return frame; }
}
