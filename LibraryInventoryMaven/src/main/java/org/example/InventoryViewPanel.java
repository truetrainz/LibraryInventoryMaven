package org.example;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventoryViewPanel {

    private JPanel panel = new JPanel();
    private String[] columns = {"name", "amount", "description", "vendor id", "manufactor id", "reoder amount"};
    private ArrayList<Inventory> inventoryArrayList;
    private String[][] inventoryArray;
    private JTable inventoryTable;
    private JScrollPane scrollPane;
    private DatabaseControl control;

    /**
     * getter method for the coulumns so that if wanted they can be used else where
     * @return
     */
    public String[] getColumns() { return columns; }

    /**
     * used to get the inventory arraylist really only here incase it is needed not used with the code base
     * at this time
     * @return
     */
    public ArrayList<Inventory> getInventoryArrayList() { return inventoryArrayList; }

    /**
     * was originally used back when the JTable was set up differently no longer used
     * @return
     */
    public String[][] getInventoryArray() { return inventoryArray; }

    /**
     * This is used to retrieve the inventory table in other classes
     * will no longer be used in later iterations because of changes in with the JTable.
     * Like other methods it is kept here incase we want to use it later.
     * @return
     */
    public JTable getInventoryTable() { return inventoryTable; }

    /**
     * This is used at the time of writing the comment but will not be used in a later iteration
     * this is due to changes in the JTable
     * @return
     */
    public JScrollPane getScrollPane() { return scrollPane; }

    /**
     * Constructor only requires access to the DatabaseControl
     * @param databaseControl
     */
    public InventoryViewPanel(DatabaseControl databaseControl) {
        control = databaseControl;
        inventoryTable = new JTable(makeDefaultTableModel());
        scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setPreferredSize(new Dimension(700, 700));
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * used when changing the inventory table
     * @return
     */
    public DefaultTableModel makeDefaultTableModel() {
        ArrayList<Inventory> inventoryArrayList = control.getAll();
        DefaultTableModel model = new DefaultTableModel();
        for (int i = 0; i < columns.length; i++) {
            model.addColumn(columns[i]);
        }
        model.addRow(columns);
        for (int i = 0; i < inventoryArrayList.size(); i++) {
            addRow(inventoryArrayList.get(i), model);
        }
        return model;
    }

    /**
     * Used to add rows to the JTabel in the inventory panel made public because it will be used in other classes
     * @param item
     */
    public void addRow(Inventory item) {
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.addRow(new Object[]{item.getName(), Integer.toString(item.getAmount()), item.getDescription(), item.getVendorId(), item.getManufactorId(), Integer.toString(item.getReorderAmount())});
    }

    public void addRow(Inventory item, DefaultTableModel model) {
        model.addRow(new Object[]{item.getName(), Integer.toString(item.getAmount()), item.getDescription(), item.getVendorId(), item.getManufactorId(), Integer.toString(item.getReorderAmount())});
    }

    /**
     * This was used before the JTable setup was changed kept here incase we want to use it again
     * @param inventory
     * @return
     */
    public String[][] transformArrayList(ArrayList<Inventory> inventory) {
        String[][] result = new String[inventory.size() + 1][7];
        for (int i = 0; i < columns.length; i++) {
            result[0][i] = columns[i];
        }
        for (int i = 0; i < inventory.size(); i++) {
            Inventory currentInventory = inventory.get(i);
            result[i + 1][0] = currentInventory.getName();
            result[i + 1][1] = Integer.toString(currentInventory.getAmount());
            result[i + 1][2] = currentInventory.getDescription();
            result[i + 1][3] = currentInventory.getVendorId();
            result[i + 1][4] = currentInventory.getManufactorId();
            result[i + 1][5] = Integer.toString(currentInventory.getReorderAmount());
        }
        return result;
    }

    /**
     * Used to retreive the JPanel in other classes
     * @return
     */
    public JPanel getPanel() {
        return panel;
    }
}
