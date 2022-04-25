package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseControl {

    // the string constants that are required to access the database
    private static final String url = "jdbc:postgresql://localhost:5434/nickcliffel";
    private static final String password = "";
    private static final String username = "";
    private Connection connection;

    // used to initalize the class and create the connection
    public DatabaseControl()  {
        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // sets up the database if the tables and sequences are not present
    public void databaseSetup() {
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.addBatch("CREATE TABLE IF NOT EXISTS inventory_in (id bigint PRIMARY KEY, barcode varchar(180), amount int, name varchar(180));");
                statement.addBatch("CREATE TABLE IF NOT EXISTS inventory(id bigint PRIMARY KEY, amount int, description TEXT, name varchar(180), vendor_id varchar(180), manufactor_id varchar(180), reorder_amount int, resin varchar(1), speciality varchar(1));");
                statement.addBatch("CREATE SEQUENCE IF NOT EXISTS inventory_in_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775806 START 1;");
                statement.addBatch("CREATE SEQUENCE IF NOT EXISTS inventory_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775806 START 1;");
                statement.executeBatch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // can be used to wipe the database of all information related to the name test
    // was used when testing no long in use
    public void clearDatabase() {
        String sql = "DELETE FROM inventory WHERE name = 'test';";
        String sql2 = "DELETE FROM inventory_in WHERE name = 'test'";
        try {
            Statement statement = connection.createStatement();
            statement.addBatch(sql);
            statement.addBatch(sql2);
            statement.executeBatch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // uses a string name to retrieve the id that it is associated with
    public int getIdFromName(String name) {
        String sql = "SELECT id FROM inventory WHERE name = ?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                return results.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // used to add brand new inventory at one point in time
    // is no longer being used currently here just in case we want to use it at a later point in time
    public void addNewInventory(int amount, String name, String description, String barcode, String vendorId, String manufactorId, int reorderAmount) {
        String sql = "INSERT INTO inventory (id, amount, description, name, vendor_id, manufactor_id) VALUES ((SELECT nextval('inventory_sequence')), ?, ?, ?, ?, ?);";
        String sql2 = "INSERT INTO inventory_in (id, barcode, amount, name) VALUES ((SELECT nextval('inventory_in_sequence')), ?, ?, ?);";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setString(2, description);
            pstmt.setString(3, name);
            pstmt.setString(4, vendorId);
            pstmt.setString(5, manufactorId);
            pstmt.executeUpdate();
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
            pstmt2.setString(1, barcode);
            pstmt2.setInt(2, amount);
            pstmt2.setString(3, name);
            pstmt2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // retrieves all of the fields associated with a name from the Inventory table
    public Inventory getInventoryUsingName(String name) {
        String sql = "SELECT * FROM inventory WHERE name = ?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            System.out.println(pstmt);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int amount = resultSet.getInt("amount");
                String description = resultSet.getString("description");
                String nameOut = resultSet.getString("name");
                String vendorId = resultSet.getString("vendor_id");
                String manufactorId = resultSet.getString("manufactor_id");
                int reorderAmount = resultSet.getInt("reorder_amount");
                String resinOut = resultSet.getString("resin");
                boolean resinIn = false;
                if (resinOut.equals("Y")) {
                    resinIn = true;
                }
                String specialOut = resultSet.getString("speciality");
                boolean specialIn = false;
                if (specialOut.equals("Y")) {
                    specialIn = true;
                }
                return new Inventory(id, amount, description, nameOut, vendorId, manufactorId, reorderAmount, resinIn, specialIn);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // adds a row to the inventory_in table
    public void addNewInventoryIn(String barcode, int amount, String name) {
        String sql = "INSERT INTO inventory_in (id, barcode, amount, name) VALUES ((SELECT nextval('inventory_in_sequence')), ?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, barcode);
            statement.setInt(2, amount);
            statement.setString(3, name);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // retrieves the amount associated with a name in the inventory table
    public int getInventoryAmountFromInventoryUsingName(String name) {
        String sql = "SELECT amount FROM inventory WHERE name = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                return results.getInt("amount");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    // addes an inventory row to the inventory table
    public void addInventory(int amount, String description, String name, String vendorId, String manufactorId, int reoderAmount, String resin, String specail) {
        String sql = "INSERT INTO inventory (id, amount, description, name, vendor_id, manufactor_id, reorder_amount, resin, speciality) VALUES ((SELECT nextval('inventory_sequence')), ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, amount);
            statement.setString(2, description);
            statement.setString(3, name);
            statement.setString(4, vendorId);
            statement.setString(5, manufactorId);
            statement.setInt(6, reoderAmount);
            statement.setString(7, resin);
            statement.setString(8, specail);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // returns the amount in the inventory table associated with a name
    public int getAmountFromName(String name) {
        String sql = "SELECT amount FROM inventory WHERE name = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("amount");
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // used to check a name might be used in the future for fool proofing the system
    public boolean checkName(String name) {
        String sql = "SELECT name FROM inventory WHERE name = ?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet resultSet = pstmt.executeQuery();
            int value = 0;
            while (resultSet.next()) {
                value++;
            }
            if (value == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // used to add incoming inventory not currnetly used
    public void addIncomingInventory(String barcode, int amount, String name) {
        String sql = "INSERT INTO inventory_in (id, barcode, amount, name) VALUES ((SELECT nextval('inventory_in_sequence')), ?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, barcode);
            statement.setInt(2, amount);
            statement.setString(3, name);
            statement.executeUpdate();
            int newAmount = getAmountFromName(name) + amount;
            String updateAmount = "UPDATE inventory SET amount = " + newAmount + " WHERE name = '" + name + "';";
            Statement statement1 = connection.createStatement();
            statement1.executeUpdate(updateAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // used to update the amount column in the inventory table associated with the name parameter
    public void updateInventoryAmountUsingName(String name, int amount) {
        String sql = "UPDATE inventory SET amount = ? WHERE name = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, amount);
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // used to remove things from inventory in assocaited with a barcode no longer in use
    public void removeInventoryIn(String barcode) {
        String sql = "SELECT id, amount, name FROM inventory_in WHERE barcode = ?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, barcode);
            ResultSet resultSet = pstmt.executeQuery();
            boolean found = false;
            int amount = -1;
            String name = null;
            int id = -1;
            while (resultSet.next() && !found) {
                id = resultSet.getInt("id");
                amount = resultSet.getInt("amount");
                name = resultSet.getString("name");
                found = true;
            }
            if (amount != -1 && name != null && id != -1) {
                int newAmount = getAmountFromName(name) - amount;
                String sql2 = "UPDATE inventory SET amount = " + newAmount + " WHERE name = '" + name + "';";
                connection.createStatement().executeUpdate(sql2);
                String sql3 = "DELETE FROM inventory_in WHERE id = " + id + ";";
                connection.createStatement().executeUpdate(sql3);
                conductReorderAmountCheck(name, newAmount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // used to check the reoder amount in the inventory table associated with the name parameter
    public void conductReorderAmountCheck(String name, int newAmount) {
        String sql = "SELECT reorder_amount FROM inventory WHERE name = ?;";
        try {
            int reorderAmount = -1;
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                reorderAmount = resultSet.getInt("reorder_amount");
            }
            if (reorderAmount <= newAmount) {
                System.out.println("it needs to be reorderd");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // used to close the connection
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // used to get all information from the inventory panel
    public ArrayList<Inventory> getAll() {
        String sql = "SELECT * FROM inventory;";
        ArrayList<Inventory> result = new ArrayList<Inventory>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int amount = resultSet.getInt("amount");
                String description = resultSet.getString("description");
                String name = resultSet.getString("name");
                String vendorId = resultSet.getString("vendor_id");
                String manufactorId = resultSet.getString("manufactor_id");
                int reorderAmount = resultSet.getInt("reorder_amount");
                boolean resin = false;
                boolean specaility = false;
                char charResin = resultSet.getString("resin").charAt(0);
                char charSpeciality = resultSet.getString("speciality").charAt(0);
                if (charResin == 'Y') {
                    resin = true;
                }
                if (charSpeciality == 'Y') {
                    specaility = true;
                }
                result.add(new Inventory(id, amount, description, name, vendorId, manufactorId, reorderAmount, resin, specaility));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    // retrieves all of the material names only used for when conducting estimates
    public ArrayList<String> getMaterialNames() {
        String sql = "SELECT name FROM inventory;";
        ArrayList<String> names = new ArrayList<String>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while(resultSet.next()) {
                names.add(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    // uses the barcode to get the information from inventory_in table
    public InventoryIn getInventoryInFromBarcode(String barcode) {
        String sql = "SELECT * FROM inventory_in WHERE barcode = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, barcode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String barcodeOut = resultSet.getString("barcode");
                int amount = resultSet.getInt("amount");
                String nameOut = resultSet.getString("name");
                return new InventoryIn(id, barcodeOut, amount, nameOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // removes row from inventory_in table based upon the id
    public void removeFromInventoryIn(int id) {
        String sql = "DELETE FROM inventory_in WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Inventory> getUnderReoder() {
        ArrayList<Inventory> inventoryArrayList = new ArrayList<Inventory>();
        String sql = "SELECT * FROM inventory WHERE inventory.amount < inventory.reorder_amount;";
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next()) {
                int id = results.getInt("id");
                int amount = results.getInt("amount");
                String description = results.getString("description");
                String name = results.getString("name");
                String vendorID = results.getString("vendor_id");
                String manufactorID = results.getString("manufactor_id");
                int reoderAmount = results.getInt("redorder_amount");
                boolean resin = false;
                if (results.getString("resin").equals("Y")) {
                    resin = true;
                }
                boolean special = false;
                if(results.getString("speciality").equals("Y")) {
                    special = true;
                }
                inventoryArrayList.add(new Inventory(id, amount,description, name, vendorID, manufactorID, reoderAmount, resin, special));
            }
            return inventoryArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
