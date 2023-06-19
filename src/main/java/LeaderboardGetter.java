import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class LeaderboardGetter {

    public static JSONArray getLeaderboards(String gameID) throws IOException {

        // Get the ID for Hollow Knight
        String goodID = Requester.getGame(gameID).getJSONObject("data").getString("id");


        // Get an ArrayList of all the categories
        JSONArray categoriesJSON = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/" + goodID + "/categories").getJSONArray("data");

        HashMap<String, Category> categories = new HashMap<>();

        ArrayList<String> unnecessaryCategories = FileReader.readFile("src/main/resources/unnecessary-categories.txt");


        for (Object category : categoriesJSON) {
            JSONObject jsonCategory = (JSONObject) category;
            if (!unnecessaryCategories.contains(jsonCategory.getString("id"))) {
                categories.put(jsonCategory.getString("id"), new Category((JSONObject) category));
            }
        }

        System.out.println("Categories retrieved for " + gameID + ".");


        // Get the variables for Hollow Knight
        JSONArray variablesJSON = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/" + goodID + "/variables").getJSONArray("data");

        HashMap<String, Variable> variables = new HashMap<>();

        ArrayList<String> unnecessaryVariables = FileReader.readFile("src/main/resources/unnecessary-variables.txt");
        for (Object variable : variablesJSON) {
            JSONObject jsonVariable = (JSONObject) variable;
            if (!unnecessaryVariables.contains(jsonVariable.getString("id"))) {
                variables.put(jsonVariable.getString("id"), new Variable((JSONObject) variable));
            }
        }

        System.out.println("Variables retrieved for " + gameID + ".");

/*        String patchID = (variablesJSON.getJSONObject(0)).getString("id");

        System.out.println(patchID);*/


        Set<String> catIDs = categories.keySet();

        for (String id : catIDs) {
            System.out.println(id + " " + categories.get(id).getName());
        }

        Set<String> varIDs = variables.keySet();

        for (String id : varIDs) {
            System.out.println(id + " " + variables.get(id).getName());
        }

        for (String id : varIDs) {
            variables.get(id).addToCategories(categories);

        }
        System.out.println("hi");

        JSONArray allLeaderBoards = new JSONArray();

        Set<String> categoryKeys = categories.keySet();

        for (String key : categoryKeys) {
            JSONArray leaderboards = categories.get(key).getLeaderboards();
            for (Object leaderboard : leaderboards) {
                JSONObject JSONLeaderboard = (JSONObject) leaderboard;
                allLeaderBoards.put(JSONLeaderboard);
            }
        }
        return allLeaderBoards;
    }

    public static HashMap<String, Integer> getLeaders(JSONArray allLeaderBoards) {

        int undoneCounter = 0;

        HashMap<String, Integer> leaders = new HashMap<>();

        for (Object leaderboard: allLeaderBoards) {
            JSONObject JSONLeaderboard = (JSONObject) leaderboard;

            if(JSONLeaderboard.getJSONObject("data").getJSONArray("runs").length() == 0) {
                undoneCounter++;
            } else {
                String leader = JSONLeaderboard.getJSONObject("data").getJSONArray("runs").getJSONObject(0).getJSONObject("run").getJSONArray("players").getJSONObject(0).getString("id");
                if(leaders.containsKey(leader)) {
                    leaders.put(leader, leaders.get(leader)+1);
                } else {
                    leaders.put(leader, 1);
                }
            }
            // System.out.println(JSONLeaderboard.getJSONObject("data").getJSONArray("runs"));

        }

        System.out.println("There are " + undoneCounter + " unrun categories... Get to it!");

        return leaders;


    }
}
