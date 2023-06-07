import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HollowKnightGetter {

    public static void HollowKnightGetterMain() throws IOException {

        /*ArrayList<Map<String, String>> patches = new ArrayList<>();
        Map<String, String> vars = Requester.getJSON("https://www.speedrun.com/api/v1/games/76rqmld8/variables");
        String hi =
        vars.get("data");

        System.out.println("hi");*/

        System.out.println(getGame("hollowknight"));


        // Get an ArrayList of all the categories
        JSONArray categoriesJSON = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/76rqmld8/categories").getJSONArray("data");

        HashMap<String, Category> categories = new HashMap<>();

        ArrayList<String> unnecessaryCategories = FileReader.readFile("src/main/resources/unnecessary-categories.txt");
        for (Object category: categoriesJSON) {
            JSONObject jsonCategory = (JSONObject) category;
            if(!unnecessaryCategories.contains(jsonCategory.getString("id"))) {
                categories.put(jsonCategory.getString("id"), new Category((JSONObject) category));
            }
        }

        System.out.println("Categories retrieved.");


        // Get the variables for Hollow Knight
        JSONArray variablesJSON = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/76rqmld8/variables").getJSONArray("data");

        HashMap<String, Variable> variables = new HashMap<>();

        ArrayList<String> unnecessaryVariables = FileReader.readFile("src/main/resources/unnecessary-variables.txt");
        for (Object variable : variablesJSON) {
            JSONObject jsonVariable = (JSONObject) variable;
            if(!unnecessaryVariables.contains(jsonVariable.getString("id"))) {
                variables.put(jsonVariable.getString("id"), new Variable((JSONObject) variable));
            }
        }

        System.out.println("Variables retrieved.");

/*        String patchID = (variablesJSON.getJSONObject(0)).getString("id");

        System.out.println(patchID);*/


        Set<String> catIDs = categories.keySet();

        for (String id: catIDs) {
            System.out.println(id + " " + categories.get(id).getName());
        }

        Set<String> varIDs = variables.keySet();

        for (String id: varIDs) {
            System.out.println(id + " " + variables.get(id).getName());
        }

        for (String id: varIDs) {
            variables.get(id).addToCategories(categories);

        }
        System.out.println("hi");

    }

    public static JSONObject getGame(String id) throws IOException {

        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/" + id);

    }


}
