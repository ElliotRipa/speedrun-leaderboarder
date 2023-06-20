import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class LeaderboardGetter {

    public static JSONArray getLeaderboards(String gameID) throws IOException {

        // Get an ArrayList of all the categories
        JSONArray categoriesJSON = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/" + gameID + "/categories").getJSONArray("data");

        HashMap<String, Category> categories = new HashMap<>();

        ArrayList<String> unnecessaryCategories = FileReader.readFile("src/main/resources/" + gameID + "/unnecessary-categories.txt");


        for (Object category : categoriesJSON) {
            JSONObject jsonCategory = (JSONObject) category;
            if (!unnecessaryCategories.contains(jsonCategory.getString("id"))) {
                categories.put(jsonCategory.getString("id"), new Category((JSONObject) category));
            }
        }

        System.out.println("Categories retrieved for " + gameID + ".");


        // Get the variables for Hollow Knight
        JSONArray variablesJSON = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/" + gameID + "/variables").getJSONArray("data");

        HashMap<String, Variable> variables = new HashMap<>();

        ArrayList<String> unnecessaryVariables = FileReader.readFile("src/main/resources/" + gameID + "/unnecessary-variables.txt");
        for (Object variable : variablesJSON) {
            JSONObject jsonVariable = (JSONObject) variable;
            if (!unnecessaryVariables.contains(jsonVariable.getString("id"))) {
                variables.put(jsonVariable.getString("id"), new Variable((JSONObject) variable));
            }
        }

        System.out.println("Variables retrieved for " + gameID + ".");

/*        String patchID = (variablesJSON.getJSONObject(0)).getString("id");

        System.out.println(patchID);*/

        Set<String> varIDs = variables.keySet();

        for (String id : varIDs) {
            System.out.println(id + " " + variables.get(id).getName());
        }

        for (String id : varIDs) {
            variables.get(id).addToCategories(categories);

        }
        System.out.println("Variables added to categories for " + gameID + ".");

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

        }

        System.out.println("There are " + undoneCounter + " unrun categories... Get to it!");

        return leaders;

    }

    public static void printLeaders(HashMap<String, Integer> leaders) throws IOException {

        HashMap<String, Integer> readableLeaders = new HashMap<>();

        Set<String> userIDs = leaders.keySet();

        for (String id : userIDs) {
            String username = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/users/" + id).getJSONObject("data").getJSONObject("names").getString("international");
            readableLeaders.put(username, leaders.get(id));
        }

        List<Map.Entry<String, Integer>> list = new LinkedList<>(readableLeaders.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for (int i = 0 ; i <= 5 ; i++) {
            System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue() + " World Records!");
        }

    }

}
