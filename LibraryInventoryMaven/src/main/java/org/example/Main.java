package org.example;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        printStatement();
        final DatabaseControl databaseControl = new DatabaseControl();
        databaseControl.databaseSetup();
        ControlScreen controlScreen = new ControlScreen(databaseControl);
        controlScreen.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                new PDFCreation().makePDF(databaseControl.getUnderReoder(), "/Users/nickcliffel/Documents/PDFDoc.pdf");
                databaseControl.closeConnection();
            }
        });
    }

    public static void printStatement() {
        System.out.println("------- |\\      |  -------- \\            /   /\\       ------- -------  --------  |\\      |");
        System.out.println("   |    | \\     | |        | \\          /   /  \\         |       |    |        | | \\     |");
        System.out.println("   |    |  \\    | |        |  \\        /   /    \\        |       |    |        | |  \\    |");
        System.out.println("   |    |   \\   | |        |   \\      /   /------\\       |       |    |        | |   \\   |");
        System.out.println("   |    |    \\  | |        |    \\    /   /        \\      |       |    |        | |    \\  |");
        System.out.println("   |    |     \\ | |        |     \\  /   /          \\     |       |    |        | |     \\ |");
        System.out.println("------- |      \\|  --------       \\/   /            \\    |    -------  --------  |      \\|");
        System.out.println("");
        System.out.println("");
        System.out.println(" ------  ----- |\\      | -------  -----  ---");
        System.out.println("|       |      | \\     |    |    |      |   \\");
        System.out.println("|       |      |  \\    |    |    |      |    |");
        System.out.println("|       |----- |   \\   |    |    |----- |   /");
        System.out.println("|       |      |    \\  |    |    |      |--");
        System.out.println("|       |      |     \\ |    |    |      |  \\");
        System.out.println(" ------  ----- |      \\|    |     ----- |   \\");
    }
}
