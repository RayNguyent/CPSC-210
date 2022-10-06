package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
// Represent a pagoda

public class Pagoda implements Writable {
    private final String name;
    private List<Statue> statues;

    //EFFECTS: create a pagoda with a name and an empty list of statues
    public Pagoda(String name) {
        this.name = name;
        statues = new ArrayList<>();
    }

    //Getters
    public String getName() {
        return name;
    }

    public List<Statue> getStatues() {
        return statues;
    }

    public int getNumStatues() {
        return statues.size();
    }

    //MODIFIES: this
    //EFFECTS: add a statue into the list of statues
    public void addStatue(Statue e) {
        statues.add(e);
        EventLog.getInstance().logEvent(new Event("A new statue is added to the list"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("statues",statuesToJson());
        return json;
    }

    // EFFECTS: returns statues in this pagoda as a JSON array
    private JSONArray statuesToJson() {
        JSONArray jsonList = new JSONArray();

        for (Statue t : statues) {
            jsonList.put(t.toJson());
        }
        return jsonList;
    }

    //EFFECTS: check if the money has been funded to the statue
    public String checkMoney(int amt) {
        for (Statue statue : statues) {
            if (statue.getAmountFunded() == amt) {
                return statue.getName() + " statue has been funded " + amt + "$";
            }
        }
        return "No statue has been funded " + amt + "$";
    }

    public String getListings() {
        String text = "";
        for (int i = 0; i < getNumStatues(); i++) {
            text = text + ("Statue #" + i + ": " + getStatues().get(i).getName()
                    + "\n" + "You have donated: $ " + getStatues().get(i).getAmountFunded() + " " + "to" + " "
                    + getStatues().get(i).getName() + " statue" + "\n" + "\n");
        }
        EventLog.getInstance().logEvent(new Event("A list of statues has been printed out"));
        return text;

    }
}
