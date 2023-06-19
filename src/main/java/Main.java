import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        // System.out.println("hi :3");

        /* Map<String, String> map = Requester.getJSON("https://www.speedrun.com/api/v1/users?lookup=TheEskot");
         Map<String, String> map2 = Requester.getPerson("TheEskot");
        Map<String, String> map3 = Requester.getGame("v1pxjz68");

        Map<String, String> runs = Requester.getJSON("https://www.speedrun.com/api/v1/runs?game=hollowknight&max=2000");
        Map<String, String> categories = Requester.getJSON("https://www.speedrun.com/api/v1/games/hollowknight/categories");
        Map<String, String> vars = Requester.getJSON("https://www.speedrun.com/api/v1/games/76rqmld8/variables");

        System.out.println("hai :3");*/

        JSONArray hkLeaderboards = LeaderboardGetter.getLeaderboards("hollowknight");

        JSONArray catXLeaderboards = LeaderboardGetter.getLeaderboards("hkmemes");

        JSONArray allLeaderboards = new JSONArray();

        for (Object leaderboard: hkLeaderboards) {
            allLeaderboards.put(leaderboard);
        }

        for (Object leaderboard: catXLeaderboards) {
            allLeaderboards.put(leaderboard);
        }

        System.out.println("!hi");

        HashMap<String, Integer> leaders = LeaderboardGetter.getLeaders(allLeaderboards);

        System.out.println(leaders);

    }

}
