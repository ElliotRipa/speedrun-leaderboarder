import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {

        JSONArray hkLeaderboards = LeaderboardGetter.getLeaderboards("hollowknight");

        JSONArray catXLeaderboards = LeaderboardGetter.getLeaderboards("hkmemes");

        JSONArray allLeaderboards = new JSONArray();

        for (Object leaderboard: hkLeaderboards) {
            allLeaderboards.put(leaderboard);
        }

        for (Object leaderboard: catXLeaderboards) {
            allLeaderboards.put(leaderboard);
        }

        HashMap<String, Integer> leaders = LeaderboardGetter.getLeaders(allLeaderboards);

        LeaderboardGetter.printLeaders(leaders);

    }

}
