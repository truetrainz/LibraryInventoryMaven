package org.example;

public class InventoryIn {

    private int id;
    private String barcode;
    private int amount;
    private String name;

    public InventoryIn(int id, String barcode, int amount, String name) {
        this.id = id;
        this.barcode = barcode;
        this.amount = amount;
        this.name = name;
    }

    public int getId() { return id; }

    public String getBarcode() { return barcode; }

    public int getAmount() { return amount; }

    public String getName() { return name; }
}
