import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class Variable {

    String id;
    String name;

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

        JSONObject choices = jsonObject.getJSONObject("values").getJSONObject("choices");

        Set<String> ids = choices.keySet();

        for (String id: ids){
            Option o = new Option(id, choices.getString(id));
            this.options.add(o);
        }

        System.out.println(choices);


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
