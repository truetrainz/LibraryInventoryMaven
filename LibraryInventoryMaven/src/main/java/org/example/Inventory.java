package org.example;

public class Inventory {

    private int id;
    private int amount;
    private String description;
    private String name;
    private String vendorId;
    private String manufactorId;
    private int reorderAmount;
    private boolean resin;
    private boolean specaility;

    public Inventory(int id, int amount, String description, String name, String vendorId, String manufactorID, int reoderAmount, boolean resin, boolean specaility) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.name = name;
        this.vendorId = vendorId;
        this.manufactorId = manufactorID;
        this.reorderAmount = reoderAmount;
        this.resin = resin;
        this.specaility = specaility;
    }

    public int getId() { return id; }

    public int getAmount() { return amount; }

    public String getName() { return name; }

    public String getVendorId() { return vendorId; }

    public String getManufactorId() { return manufactorId; }

    public int getReorderAmount() { return reorderAmount; }

    public String getDescription() { return description; }

    public boolean getResin() { return resin; }

    public boolean getSpecaility() { return specaility; }
}
