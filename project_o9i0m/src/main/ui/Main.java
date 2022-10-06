package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
//        EFFECTS: create new PagodaApp
        try {
            PagodaApp pagoda = new PagodaApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");

//        try {
//            new PagodaAppGUI();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }
//    }

        }
    }
}