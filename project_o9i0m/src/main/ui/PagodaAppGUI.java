package ui;


import model.Event;
import model.EventLog;
import model.Pagoda;
import model.Statue;
import org.json.JSONException;
import persistence.Reader;
import persistence.Writer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class PagodaAppGUI extends JFrame implements ActionListener {
    private Pagoda pagoda;
    private Writer writer;
    private Reader reader;
    private static final String JSON_STORE = "./data/workroom.json";

    private JPanel mainMenu;
    private JButton buttonA;
    private JButton buttonB;
//    private JButton buttonC;
    private JButton buttonD;
    private JButton buttonE;
    private JButton buttonF;

    private JPanel viewingPanel;


    private JPanel addingPanel;
    private JButton addStatue;
    private JLabel listStatue;
    private JTextField box1;
    private JLabel name;
    private JLabel donate;
    private JTextField box2;

//    private JPanel viewDonatePanel;
//    private JLabel listDonation;

    //EFFECTS: make a presentation of the app
    public PagodaAppGUI() throws FileNotFoundException {
        super("Online Pagoda App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 700));
        playMusic();

        initObjects();
        initMenu();
        initButtons();

        addTitleToMainMenu();
        addImageToMainMenu();

        makePanels();
        mainMenu.setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: initialize a new pagoda, writer, and reader
    private void initObjects() {
        pagoda = new Pagoda("Guan Yin");
        writer = new Writer(JSON_STORE);
        reader = new Reader(JSON_STORE);
    }

    //EFFECTS: initializes buttons and their function
    private void initButtons() {
        addButtons();
        addAction();
    }

    //EFFECTS: displays a button
    private void setButton(JPanel panel, JButton button) {
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));
        button.setBackground(Color.LIGHT_GRAY);
        panel.add(button);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    //EFFECTS: makes buttons and displays them on main menu
    private void addButtons() {
        buttonA = new JButton("Add a statue");
        setButton(mainMenu, buttonA);
        buttonB = new JButton("View your list");
        setButton(mainMenu, buttonB);
//        buttonC = new JButton("View your donation");
//        setButton(mainMenu, buttonC);
        buttonD = new JButton("Load your file");
        setButton(mainMenu, buttonD);
        buttonE = new JButton("Save your file");
        setButton(mainMenu, buttonE);
        buttonF = new JButton("Exit");
        setButton(mainMenu, buttonF);
    }

    //EFFECTS: adds functions to the buttons
    private void addAction() {
        buttonA.addActionListener(this);
        buttonA.setActionCommand("Add a statue");
        buttonB.addActionListener(this);
        buttonB.setActionCommand("View your list");
//        buttonC.addActionListener(this);
//        buttonC.setActionCommand("View your donation");
        buttonD.addActionListener(this);
        buttonD.setActionCommand("Load your file");
        buttonE.addActionListener(this);
        buttonE.setActionCommand("Save your file");
        buttonF.addActionListener(this);
        buttonF.setActionCommand("Exit");
    }

    //EFFECTS: initialize main menu panel
    private void initMenu() {
        mainMenu = new JPanel();
        mainMenu.setBackground(Color.WHITE);
        add(mainMenu);
        listStatue = new JLabel();
        listStatue.setText("No list available!");
        listStatue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//        listDonation = new JLabel();
//        listDonation.setText("No list available!");
//        listDonation.setFont(new Font("Times New Roman", Font.PLAIN, 20));

    }

    //EFFECTS: Makes and adds title for the main menu
    //referred to: https://stackoverflow.com/questions/5987600/simple-adding-a-jlabel-to-jpanel
    private void addTitleToMainMenu() {
        JLabel welcome = new JLabel("Welcome to the online pagoda!");
        welcome.setFont(new Font("SanSerif", Font.BOLD, 50));
        mainMenu.setLayout(new FlowLayout());
        mainMenu.add(welcome);
    }

    //EFFECTS: Makes and adds big image for the main menu
    // referred to:
    private void addImageToMainMenu() {
        ImageIcon img = new ImageIcon("./data/pic1.png");
        JLabel image = new JLabel(img);
        image.setMinimumSize(new Dimension(18, 13));
        mainMenu.add(image);
    }

    //EFFECTS: makes panels that appear after clicking
    private void makePanels() {
        makeAddingPanel();
        makeViewingPanel();
//        makeDonationPanel();
    }

    //EFFECTS: makes the panel that displays the option to add a statue
    private void makeAddingPanel() {
        addingPanel = new JPanel(new GridLayout(0, 2));
        JButton backToMenu = new JButton("Return");
        backToMenu.addActionListener(this);
        addButton(backToMenu, addingPanel);

        visualizePanel();
        addInputToListPanel();
    }

    //EFFECTS: visualize adding panel
    private void visualizePanel() {
        addStatue = new JButton("Add");
        addStatue.setActionCommand("Add");
        addStatue.addActionListener(this);
        addStatue.setBackground(Color.DARK_GRAY);
        addStatue.setForeground(Color.WHITE);
        addStatue.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        name = new JLabel("Name:");
        name.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        donate = new JLabel("Donate:");
        donate.setFont(new Font("Times New Roman", Font.PLAIN, 24));

        box1 = new JTextField(10);
        box1.setMaximumSize(new Dimension(1200, 400));
        box1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        box2 = new JTextField(10);
        box2.setMaximumSize(new Dimension(1200, 400));
        box2.setFont(new Font("Times New Roman", Font.PLAIN, 24));
    }

    //EFFECTS: Adds user's input to the viewing panel
    private void addInputToListPanel() {
        addingPanel.add(addStatue);
        addingPanel.add(name);
        addingPanel.add(box1);
        addingPanel.add(donate);
        addingPanel.add(box2);
    }

    //EFFECTS: makes the panel that displays the list of statues that have been added
    private void makeViewingPanel() {
        viewingPanel = new JPanel(new GridLayout(2, 1));
        JScrollPane scroll = new JScrollPane(listStatue, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JButton mainMenuButton = new JButton("Return");
        mainMenuButton.setActionCommand("Return");
        mainMenuButton.addActionListener(this);
        addButton(mainMenuButton, viewingPanel);

        viewingPanel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        viewingPanel.add(scroll);
    }

//    //EFFECTS: makes the panel that displays the donation list that have been donated
//    private void makeDonationPanel() {
//        viewDonatePanel = new JPanel(new GridLayout(2, 1));
//        JScrollPane scroll = new JScrollPane(listDonation, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        JButton mainMenuButton = new JButton("Return");
//        mainMenuButton.setActionCommand("Return");
//        mainMenuButton.addActionListener(this);
//        addButton(mainMenuButton, viewDonatePanel);
//
//        viewDonatePanel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//        viewDonatePanel.add(scroll);
//    }


    //EFFECTS: makes and adds a button to a panel
    private void addButton(JButton button, JPanel panel) {
        button.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.white);
        panel.add(button);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }


    //EFFECTS: does a certain function when a button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add a statue")) {
            openAddingPanel();
        } else if (e.getActionCommand().equals("Add")) {
            addStatueToList();
        } else if (e.getActionCommand().equals("View your list")) {
            openViewingPanel();
//        } else if (e.getActionCommand().equals("View your donation")) {
//            openDonationPanel();
        } else if (e.getActionCommand().equals("Load your file")) {
            loadFile();
        } else if (e.getActionCommand().equals("Save your file")) {
            saveFile();
        } else if (e.getActionCommand().equals("Exit")) {
            printLog(EventLog.getInstance());
            System.exit(0);
        } else if (e.getActionCommand().equals("Return")) {
            returnToMainMenu();
        }
    }

    //EFFECTS: make the main menu pop up on the screen
    private void returnToMainMenu() {
        mainMenu.setVisible(true);
        viewingPanel.setVisible(false);
//        viewDonatePanel.setVisible(false);
        addingPanel.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: add the newly added state to the list of statues
    // adapted HTML <pre> tag from: https://www.geeksforgeeks.org/html-pre-tag/
    private void addStatueToList() {
        try {
            Statue statue = new Statue(box1.getText());
            int amount = Integer.parseInt(box2.getText());
            statue.donate(amount);
            pagoda.addStatue(statue);
            listStatue.setText("<html><pre>Current statues: \n" + pagoda.getListings() + "\n</pre></html>");
            box1.setText("");
            box2.setText("");
        } catch (NumberFormatException e) {
            box2.setText("Invalid input, please try again!");
        }
    }


    //MODIFIES: this
    //EFFECTS: save the status of pagoda to file
    private void saveFile() {
        try {
            writer.open();
            writer.write(pagoda);
            writer.close();
        } catch (FileNotFoundException e) {
            listStatue.setText("Unable to write to file: " + JSON_STORE);
        } catch (NullPointerException e) {
            listStatue.setText("Please load the file before trying to save!");
        }
    }

    //MODIFIES: this
    //EFFECTS: load the file to the current pagoda
    // adapted HTML <pre> tag from: https://www.geeksforgeeks.org/html-pre-tag/
    private void loadFile() {
        try {
            pagoda = reader.read();
            listStatue.setText("<html><pre>Current statues: \n" + pagoda.getListings() + "\n</pre></html>");
        } catch (IOException e) {
            listStatue.setText("Unable to read from file: " + JSON_STORE);
        } catch (JSONException e) {
            listStatue.setText("Please try to initialize the list first!");
        }
    }

    //MODIFIES: this
    //EFFECTS: makes viewing panel pop up on the screen
    private void openViewingPanel() {
        add(viewingPanel);
        addingPanel.setVisible(false);
        mainMenu.setVisible(false);
        viewingPanel.setVisible(true);
//        viewDonatePanel.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: makes adding panel pop up on the screen
    private void openAddingPanel() {
        add(addingPanel);
        addingPanel.setVisible(true);
        mainMenu.setVisible(false);
        viewingPanel.setVisible(false);
//        viewDonatePanel.setVisible(false);
    }

//    //MODIFIES: this
//    //EFFECTS: makes viewing donation panel pop up on the screen
//    private void openDonationPanel() {
//        add(viewDonatePanel);
//        addingPanel.setVisible(false);
//        mainMenu.setVisible(false);
//        viewingPanel.setVisible(false);
//        viewDonatePanel.setVisible(true);
//    }

    //MODIFIES: this
    //EFFECTS: play the background music for the app
    //CREDITS: adapted from
    // https://stackoverflow.com/questions/53468606/
    // java-getaudioinputstream-trying-to-read-audio-file-getting-javax-sound-sampled
    // and https://stackoverflow.com/questions/19603450/how-can-i-play-an-mp3-file
    public void playMusic() {
        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            File file = new File(path + "/data/meditation.wav");
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Please try again!");
        }
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
        repaint();
    }
}
