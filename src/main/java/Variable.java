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

            switch (this.id) {

                case "onvkzmlm":
                    for (String categoryKey : categoryKeys) {
                        categories.get(categoryKey).addVariable(this);
                    }
                    break;

                case "ql6165x8":
                    categories.get("vdo5xe6k").addVariable(this);
                    break;

                case "5ly7kkkl":
                    categories.get("8241w7w2").addVariable(this);
                    break;

                case "onvj96mn":
                    categories.get("xk9vrl6d").addVariable(this);


            }



        } else {

            if(categoryKeys.contains(category)) {
                categories.get(category).addVariable(this);
            } else {
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
