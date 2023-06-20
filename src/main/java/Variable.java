import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Variable {

    String id;
    String name;
    String category;

    ArrayList<Option> options = new ArrayList<>();

    public Variable(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addOption(String id, String name) {
        Option option = new Option(id, name);
        this.options.add(option);
    }

    public int size() {
        return options.size();
    }

    // Generates a variable object given an appropriate JSONObject
    public Variable(JSONObject jsonObject) {

        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        if(jsonObject.getJSONObject("scope").getString("type").equals("global")) {
            this.category = "global";
        } else if(jsonObject.getJSONObject("scope").getString("type").equals("single-level")) {
            this.category = jsonObject.getJSONObject("scope").getString("level");
        } else {

            this.category = jsonObject.getString("category");
        }

        JSONObject choices = jsonObject.getJSONObject("values").getJSONObject("choices");

        Set<String> ids = choices.keySet();

        for (String id: ids){
            Option o = new Option(id, choices.getString(id));
            this.options.add(o);
        }

    }

    public void addToCategories(HashMap<String, Category> categories) {

        ArrayList<String> categoryKeys = new ArrayList<>(categories.keySet());

        if(category.equals("global")) {

            String game = categories.get(categoryKeys.get(0)).getPrettyGame();

            ArrayList<String> globalVarsArray = FileReader.readFile("src/main/resources/" + game + "/categories/global-variables.txt");

            HashMap<String, String> globalVarsHashMap = new HashMap<>();

            for (String line : globalVarsArray) {
                String[] parts = line.split(" ");
                globalVarsHashMap.put(parts[0], parts[1]);
            }

            categories.get(globalVarsHashMap.get(this.id)).addVariable(this);

        } else {

            if(categoryKeys.contains(category)) {                           // Adds all the well-behaved variables to their categories.
                categories.get(category).addVariable(this);
            } else {                                                        // Throws an exception if the category does not exist.
                throw new RuntimeException("Specified category with id " + category + " does not exist.");
            }

        }

    }

    public void addToLevels(HashMap<String, Level> levels) {

        ArrayList<String> levelKeys = new ArrayList<>(levels.keySet());

        if(category.equals("global")) {

            String game = levels.get(levelKeys.get(0)).getPrettyGame();

            ArrayList<String> globalVarsArray = FileReader.readFile("src/main/resources/" + game + "/levels/global-variables.txt");

            HashMap<String, String> globalVarsHashMap = new HashMap<>();

            for (String line : globalVarsArray) {
                String[] parts = line.split(" ");
                globalVarsHashMap.put(parts[0], parts[1]);
            }

            levels.get(globalVarsHashMap.get(this.id)).addVariable(this);

        } else {

            if(levelKeys.contains(category)) {                           // Adds all the well-behaved variables to their categories.
                levels.get(category).addVariable(this);
            } else {                                                        // Throws an exception if the category does not exist.
                throw new RuntimeException("Specified level with id " + category + " does not exist.");
            }

        }

    }

    public String getName() {
        return name;
    }

    public static class Option {
        String id;
        String name;

        public Option(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getID() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
