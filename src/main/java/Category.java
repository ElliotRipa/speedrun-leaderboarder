import org.json.JSONObject;

import java.util.ArrayList;

public class Category {

    String id;
    String name;

    ArrayList<Variable> variables = new ArrayList<>();


    // Generates an incomplete category object given an appropriate JSONObject
    public Category(JSONObject jsonObject) {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
    }

    public String getName() {
        return name;
    }

}
