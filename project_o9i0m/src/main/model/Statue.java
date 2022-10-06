package model;

import org.json.JSONObject;
import persistence.Writable;

//Represent a statue
public class Statue implements Writable {
    private final String name;
    private int amountFunded;

    //EFFECTS: create a statue with a name and target funding and the description of that statue
    public Statue(String nm) {
        this.name = nm;
        this.amountFunded = 0;
    }

    //GETTERS
    public String getName() {
        return name;
    }

    public int getAmountFunded() {
        return amountFunded;
    }

    //MODIFIES: this
    //EFFECTS: add an amount of money to the statue
    public int donate(int amount) {
        amountFunded = amount + amountFunded;
        EventLog.getInstance().logEvent(new Event("$" + amount + " has been donated"));
        return amountFunded;

    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("amountFunded", amountFunded);
        return json;
    }


}
