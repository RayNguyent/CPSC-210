package ui;

import model.Pagoda;
import model.Statue;
import persistence.Reader;
import persistence.Writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static model.Pagoda.*;

public class PagodaApp {
    private Pagoda pagoda;
    private Statue statue;
    private Scanner input;
    private Writer writer;
    private Reader reader;
    private static final String JSON_STORE = "./data/workroom.json";

    // EFFECTS: runs the online pagoda application
    public PagodaApp() throws FileNotFoundException {
        writer = new Writer(JSON_STORE);
        reader = new Reader(JSON_STORE);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "a":
                doAdd();
                break;
            case "v":
                doView();
                break;
            case "m":
                viewMoney();
                break;
            case "s":
                savePagoda();
                break;
            case "l":
                loadPagoda();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: initialize
    private void init() {
        pagoda = new Pagoda("Guan Yin");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Welcome to our pagoda: ");
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add");
        System.out.println("\tv -> view");
        System.out.println("\tm -> view donate");
        System.out.println("\ts -> save pagoda to file");
        System.out.println("\tl -> load pagoda from file");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: add a statue with the typed name to the list of statues
    private void doAdd() {
        System.out.println("Enter statue to add: ");
        String name = input.next();
        statue = new Statue(name);
        pagoda.addStatue(statue);
        doStatue(statue);
    }

    //EFFECTS: represent things to do with the added statue
    private void doStatue(Statue stat) {
        String selection = "";  // force entry into loop

        while (!(selection.equals("d") || selection.equals("p"))) {
            System.out.println("d to donate");
            System.out.println("p to pray");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("d")) {
            doDonate(stat);
        } else {
            doPray();
        }
    }

    // MODIFIES: this
    // EFFECTS: make donate to the statue
    private void doDonate(Statue stat) {
        System.out.print("Enter amount to donate: $");
        int amount = input.nextInt();

        if (amount >= 0.0) {
            stat.donate(amount);
        } else {
            System.out.println("Cannot deposit negative amount...\n");
        }
        System.out.println("You have donated: $ " + stat.getAmountFunded() + " " + "to" + " " + stat.getName());
    }

    //EFFECTS: pray to a statue
    private void doPray() {
        System.out.println("You have prayed to" + " " + statue.getName());
    }

    //EFFECTS: view the list of statues user want to see
    private void doView() {
        if (pagoda.getStatues().size() == 0) {
            System.out.println("You haven't added any statues to view");
        }
        for (Statue statue : pagoda.getStatues()) {
            System.out.println(statue.getName());
        }
    }

    //EFFECTS: view the donation for each statue
    private void viewMoney() {
        if (pagoda.getStatues().size() == 0) {
            System.out.println("You haven't added any statues to view");
        }
        for (Statue statue : pagoda.getStatues()) {
            System.out.println("You have donated: $" + statue.getAmountFunded() + " " + "to" + " "
                    + statue.getName() + "statue");
        }
    }

    // EFFECTS: saves the workroom to file
    private void savePagoda() {
        try {
            writer.open();
            writer.write(pagoda);
            writer.close();
            System.out.println("Saved " + pagoda.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadPagoda() {
        try {
            pagoda = reader.read();
            System.out.println("Loaded " + pagoda.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}



