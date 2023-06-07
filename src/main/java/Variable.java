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

    // Generates a variable object given an appropriate JSONObject
    public Variable(JSONObject jsonObject) {

        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        if(jsonObject.getJSONObject("scope").getString("type").equals("global")) {
            this.category = "global";
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

        Set<String> categoryKeys = categories.keySet();

        if(category.equals("global")) {

            switch (this.id) {                                              // These variables are set to global. Most of them shouldn't be.

                case "onvkzmlm":                                            // Patch is the only one that should be global.
                    for (String categoryKey : categoryKeys) {               // We add it to every category.
                        categories.get(categoryKey).addVariable(this);
                    }
                    break;

                case "ql6165x8":                                            // 107% AB Glitch should not be global.
                    categories.get("vdo5xe6k").addVariable(this);           // We add it to 107% AB.
                    break;

                case "5ly7kkkl":                                            // Godhome Ending Glitch should not be global.
                    categories.get("8241w7w2").addVariable(this);           // We add it to Godhome Ending.
                    break;

                case "onvj96mn":                                            // All Achievements Glitch should not be global.
                    categories.get("xk9vrl6d").addVariable(this);           // We add it to All Achievements.

            }

        } else {

            if(categoryKeys.contains(category)) {                           // Adds all the well-behaved variables to their categories.
                categories.get(category).addVariable(this);
            } else {                                                        // Throws an exception if the category does not exist.
                throw new RuntimeException("Specified category with id " + category + " does not exist.");
            }

        }

    }

    public String getName() {
        return name;
    }

    private static class Option {
        String id;
        String name;

        public Option(String id, String name) {
            this.id = id;
            this.name = name;
        }

    }

}
