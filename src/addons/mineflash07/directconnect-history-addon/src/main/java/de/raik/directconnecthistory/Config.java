package de.raik.directconnecthistory;

import com.google.gson.*;

import java.io.*;
import java.util.HashSet;
import java.util.Map;

public class Config {

    private String configFileName;
    private File configFile;

    private JsonObject config = null;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final JsonParser JSON_PARSER = new JsonParser();

    public Config(String name, String directory) {
        this.configFileName = name + ".json";
        this.configFile = new File(directory, this.configFileName);
        File directoryFile = this.configFile.getParentFile();
        if (!directoryFile.exists())
            directoryFile.mkdirs();

        this.loadConfig();
    }

    public JsonObject getConfigAsJsonObject() {
        return this.config;
    }

    public void save() {
        this.writeFile(this.config);
    }

    private void loadConfig() {
        JsonObject resourceConfig = this.getFromResource();
        try {
            this.config = (JsonObject) JSON_PARSER.parse(new BufferedReader(new InputStreamReader(new FileInputStream(this.configFile))));
        } catch (FileNotFoundException | JsonParseException | ClassCastException exception) {
            this.config = new JsonObject();
        }
        this.config = compareJsonObjects(this.config, resourceConfig);
        this.save();
    }

    private JsonObject compareJsonObjects(JsonObject in, JsonObject from) {
        HashSet<String> keySet = new HashSet<>();
        in.entrySet().forEach(entry -> keySet.add(entry.getKey()));
        for (Map.Entry<String, JsonElement> entry: from.entrySet()) {
            if (keySet.contains(entry.getKey())) {
                if (!(entry.getValue() instanceof JsonObject))
                    continue;
                JsonObject inObject = in.getAsJsonObject(entry.getKey());
                in.remove(entry.getKey());
                in.add(entry.getKey(), this.compareJsonObjects(inObject, (JsonObject) entry.getValue()));
                continue;
            }
            in.add(entry.getKey(), entry.getValue());
        }
        return in;
    }

    private void writeFile(JsonObject jsonObject) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.configFile));
            writer.write(GSON.toJson(jsonObject));
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private JsonObject getFromResource() {
        InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream(this.configFileName);
        JsonObject result = new JsonObject();
        try {
            if (resourceStream != null) {
                result = (JsonObject) JSON_PARSER.parse(new BufferedReader(new InputStreamReader(resourceStream)));
                resourceStream.close();
            }
        } catch (JsonParseException | ClassCastException | IOException ignored) {}
        if (!this.configFile.exists())
            this.writeFile(result);
        return result;
    }


}
