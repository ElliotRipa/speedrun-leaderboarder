import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class LeaderboardGetter {

    public static JSONArray getLeaderboards(String gameID, Requester requester) throws IOException {

        // Get an ArrayList of all the categories
        JSONArray categoriesJSON = requester.getJSON("https://www.speedrun.com/api/v1/games/" + gameID + "/categories").getJSONArray("data");

        HashMap<String, Category> categories = new HashMap<>();

        ArrayList<String> unnecessaryCategories = FileReader.readFile("src/main/resources/" + gameID + "/categories/unnecessary-categories.txt");


        for (Object category : categoriesJSON) {
            JSONObject jsonCategory = (JSONObject) category;
            if (!unnecessaryCategories.contains(jsonCategory.getString("id"))) {
                categories.put(jsonCategory.getString("id"), new Category((JSONObject) category, requester));
            }
        }

        System.out.println("Categories retrieved for " + gameID + ".");


        // Get the variables for Hollow Knight
        JSONArray variablesJSON = requester.getJSON("https://www.speedrun.com/api/v1/games/" + gameID + "/variables").getJSONArray("data");

        HashMap<String, Variable> variables = new HashMap<>();

        ArrayList<String> unnecessaryVariables = FileReader.readFile("src/main/resources/" + gameID + "/categories/unnecessary-variables.txt");
        for (Object variable : variablesJSON) {
            JSONObject jsonVariable = (JSONObject) variable;
            if (!unnecessaryVariables.contains(jsonVariable.getString("id"))) {
                variables.put(jsonVariable.getString("id"), new Variable((JSONObject) variable));
            }
        }

        System.out.println("Variables retrieved for " + gameID + ".");


        Set<String> varIDs = variables.keySet();

        for (String id : varIDs) {
            variables.get(id).addToCategories(categories);

        }
        System.out.println("Variables added to categories for " + gameID + ".");

        JSONArray allLeaderBoards = new JSONArray();

        Set<String> categoryKeys = categories.keySet();

        for (String key : categoryKeys) {
            JSONArray leaderboards = categories.get(key).getLeaderboards(requester);
            for (Object leaderboard : leaderboards) {
                JSONObject JSONLeaderboard = (JSONObject) leaderboard;
                allLeaderBoards.put(JSONLeaderboard);
            }
        }
        return allLeaderBoards;
    }

    public static HashMap<String, Integer> getLeaders(JSONArray allLeaderBoards, Requester requester) throws IOException {

        int undoneCounter = 0;

        HashMap<String, Integer> leaders = new HashMap<>();

        for (Object leaderboard: allLeaderBoards) {
            JSONObject JSONLeaderboard = (JSONObject) leaderboard;

            if(JSONLeaderboard.getJSONObject("data").getJSONArray("runs").length() == 0) {
                undoneCounter++;
                JSONObject game = requester.getGame(JSONLeaderboard.getJSONObject("data").getString("game"));
                String gameName = game.getJSONObject("data").getJSONObject("names").getString("international");

                JSONObject category = requester.getCategory(JSONLeaderboard.getJSONObject("data").getString("category"));
                String categoryName = category.getJSONObject("data").getString("name");

                String variableID = new ArrayList<String>(JSONLeaderboard.getJSONObject("data").getJSONObject("values").keySet()).get(0);
                String variableName = requester.getVariable(variableID).getJSONObject("data").getString("name");
                String optionID = JSONLeaderboard.getJSONObject("data").getJSONObject("values").getString(variableID);
                String optionName = requester.getVariable(variableID).getJSONObject("data").getJSONObject("values").getJSONObject("values").getJSONObject(optionID).getString("label");

                System.out.println("No one has run " + gameName + " " + categoryName + " where " + variableName + " = " + optionName);

                //JSONObject variable = Requester.getVariable(JSONLeaderboard.getJSONObject("data").getJSONObject("values"))

                //String category = Requester.
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

    public static void printLeaders(HashMap<String, Integer> leaders, Requester requester) throws IOException, InterruptedException {

        HashMap<String, Integer> readableLeaders = new HashMap<>();

        Set<String> userIDs = leaders.keySet();

        Main.sleep(10);

        int userCounter = 0;

        for (String id : userIDs) {
            userCounter++;
            if(userCounter % 50 == 0) {
                Main.sleep(5);
            }
            String username = requester.getJSON("https://www.speedrun.com/api/v1/users/" + id).getJSONObject("data").getJSONObject("names").getString("international");
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
