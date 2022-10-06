package persistence;

import model.Pagoda;
import model.Statue;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
// This is based on JsonSerializationDemo project.
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads JSON representation of pagoda to file
public class Reader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public Reader(String source) {
        this.source = source;
    }

    // EFFECTS: reads pagoda from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Pagoda read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePagoda(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses pagoda from JSON object and returns it
    private Pagoda parsePagoda(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Pagoda pa = new Pagoda(name);
        addStatues(pa, jsonObject);
        return pa;
    }

    // MODIFIES: Pagoda
    // EFFECTS: parses statues from JSON object and adds them to pagoda
    private void addStatues(Pagoda pagoda, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("statues");
        for (Object json : jsonArray) {
            JSONObject nextStatue = (JSONObject) json;
            addStatue(pagoda, nextStatue);
        }
    }

    // MODIFIES: Pagoda
    // EFFECTS: parses statue from JSON object and adds it to pagoda
    private void addStatue(Pagoda pagoda, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int amountFunded = jsonObject.getInt("amountFunded");
        Statue statue = new Statue(name);
        statue.donate(amountFunded);
        pagoda.addStatue(statue);
    }

}
