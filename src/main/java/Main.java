import org.json.JSONArray;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        LocalTime start = LocalTime.now();

        Requester requester = new Requester();

        JSONArray hkLevelLeaderboards = LevelLeaderboardGetter.getLeaderboards("hollowknight", requester);

        // sleep(10);

        JSONArray catXLevelLeaderboards = LevelLeaderboardGetter.getLeaderboards("hkmemes", requester);

        // sleep(10);

        JSONArray hkLeaderboards = LeaderboardGetter.getLeaderboards("hollowknight", requester);

        // sleep(10);

        JSONArray catXLeaderboards = LeaderboardGetter.getLeaderboards("hkmemes", requester);

        // sleep(10);

        JSONArray allLeaderboards = new JSONArray();

        for (Object leaderboard: hkLevelLeaderboards) {
            allLeaderboards.put(leaderboard);
        }

        for (Object leaderboard: catXLevelLeaderboards) {
            allLeaderboards.put(leaderboard);
        }

        for (Object leaderboard: hkLeaderboards) {
            allLeaderboards.put(leaderboard);
        }

        for (Object leaderboard: catXLeaderboards) {
            allLeaderboards.put(leaderboard);
        }

        HashMap<String, Integer> leaders = LeaderboardGetter.getLeaders(allLeaderboards, requester);

        LeaderboardGetter.printLeaders(leaders, requester);

        System.out.println(start.toString());
        System.out.println(LocalTime.now().toString());

        System.out.println(requester.querieCounter);

    }

    public static void sleep(int seconds) throws InterruptedException {
        System.out.println("Sleeping for "+ seconds + " seconds to not upset API");
        for(int i = seconds ; i > 0 ; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }
        System.out.println(0);
    }

}
