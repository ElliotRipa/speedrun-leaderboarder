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
                                                                            // TODO: Fix this switch statement; It grew much larger than expected.
            switch (this.id) {                                              // These variables are set to global. Most of them shouldn't be.

                case "onvkzmlm":                                            // Patch is the only one that should be global.
                    for (String categoryKey : categoryKeys) {               // We add it to every category.
                        categories.get(categoryKey).addVariable(this);      // P.S. It's now been removed. But the code is kept in case of reversal.
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
                    break;

                                                                            // Category Extensions.
                case "2lg3mmen":                                            // 3 Vessel Fragments Glitch should not be global.
                    categories.get("jdzl8pv2").addVariable(this);           // We add it to 3 Vessel Fragments.
                    break;

                case "68koqg3n":                                            // Marissa Audience Glitch should not be global.
                    categories.get("wkp5v40d").addVariable(this);           // We add it to Marissa Audience.
                    break;

                case "wlewrr4l":                                            // Save Myla Glitch should not be global.
                    categories.get("824n6mgk").addVariable(this);           // We add it to Save Myla.
                    break;

                case "j845oq2n":                                            // Al2ba Glitch should not be global.
                    categories.get("vdo5391k").addVariable(this);           // We add it to Al2ba%.
                    break;

                case "wlewrjxl":                                            // PoP% Glitch should not be global.
                    categories.get("w20q1mzd").addVariable(this);           // We add it to PoP%.
                    break;

                case "j84ge248":                                            // Grubsong% Glitch should not be global.
                    categories.get("z27w090k").addVariable(this);           // We add it to Grubsong%.
                    break;

                case "0nwpxg58":                                            // Ghostbusters Glitch should not be global.
                    categories.get("zdnozyx2").addVariable(this);           // We add it to Ghostbusters.
                    break;

                case "zdnozyx2":                                            // Cartographer Glitch should not be global.
                    categories.get("n2yvewe2").addVariable(this);           // We add it to Cartographer.
                    break;

                case "wl3177v8":                                            // Clawless Shade Cloak Glitch should not be global.
                    categories.get("02q4o8pd").addVariable(this);           // We add it to Clawless Shade Cloak.
                    break;

                case "rn1p02dn":                                            // Pantheon 5 Boss Order Glitch should not be global.
                    categories.get("zdnz57ed").addVariable(this);           // We add it to Pantheon 5 Boss Order.
                    break;

                case "r8rg9k2n":                                            // 0 Geo Glitch should not be global.
                    categories.get("wkpvrqjk").addVariable(this);           // We add it to 0 Geo.
                    break;


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
