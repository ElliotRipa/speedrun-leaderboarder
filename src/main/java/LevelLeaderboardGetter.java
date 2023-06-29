import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class LevelLeaderboardGetter {

    public static JSONArray getLeaderboards(String gameID) throws IOException {

        // Get an ArrayList of all the levels
        JSONArray levelsJSON = Requester.getJSON("https://www.speedrun.com/api/v1/games/" + gameID + "/levels").getJSONArray("data");

        HashMap<String, Level> levels = new HashMap<>();

        for (Object level : levelsJSON) {
            JSONObject jsonCategory = (JSONObject) level;
            levels.put(jsonCategory.getString("id"), new Level((JSONObject) level));
        }

        System.out.println("Levels retrieved for " + gameID + ".");


        // Get the variables for Hollow Knight
        JSONArray variablesJSON = Requester.getJSON("https://www.speedrun.com/api/v1/games/" + gameID + "/variables").getJSONArray("data");

        HashMap<String, Variable> variables = new HashMap<>();

        ArrayList<String> unnecessaryVariables = FileReader.readFile("src/main/resources/" + gameID + "/levels/unnecessary-variables.txt");
        for (Object variable : variablesJSON) {
            JSONObject jsonVariable = (JSONObject) variable;
            if (!unnecessaryVariables.contains(jsonVariable.getString("id"))) {
                variables.put(jsonVariable.getString("id"), new Variable((JSONObject) variable));
            }
        }

        System.out.println("Variables retrieved for " + gameID + ".");


        Set<String> varIDs = variables.keySet();

        for (String id : varIDs) {
            variables.get(id).addToLevels(levels);

        }
        System.out.println("Variables added to levels for " + gameID + ".");

        JSONArray allLeaderBoards = new JSONArray();

        Set<String> categoryKeys = levels.keySet();

        for (String key : categoryKeys) {
            JSONArray leaderboards = levels.get(key).getLeaderboards();
            for (Object leaderboard : leaderboards) {
                JSONObject JSONLeaderboard = (JSONObject) leaderboard;
                allLeaderBoards.put(JSONLeaderboard);
            }
        }
        return allLeaderBoards;
    }

}
