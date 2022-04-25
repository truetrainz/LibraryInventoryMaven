package org.example;

import java.io.*;
import java.util.ArrayList;


public class PDFCreation {
    public PDFCreation() {}

    public void makePDF(ArrayList<Inventory> inventoryArrayList, String location) {
      try {
          File file = new File(location);
          Writer writer = new BufferedWriter(new FileWriter(file));
          for (int i = 0; i < inventoryArrayList.size(); i++) {
            writer.write(inventoryArrayList.get(i).getName() + " -> " + inventoryArrayList.get(i).getVendorId() + " -> " + inventoryArrayList.get(i).getManufactorId());
          }
      } catch (Exception ex) {
          ex.printStackTrace();
      }
    }
}
