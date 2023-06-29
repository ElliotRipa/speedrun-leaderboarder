import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Queue;

public class Requester {

    public int queriesPerMinute = 300;

    public int querieCounter = 0;

    private Queue<LocalTime> pastQueries = new LinkedList<LocalTime>() {
        @Override
        public boolean add(LocalTime localTime) {

            querieCounter++;

            if(this.size() >= queriesPerMinute) {
                while (LocalTime.now().compareTo(this.peek()) < 0) {
                    System.out.println("Waiting until " + this.peek().toString());
                }
                this.remove();
                this.add(this.size(), localTime);
                return true;
            }

            this.add(this.size(), localTime);
            return true;
        }
    };

    public JSONObject getJSON(String url) throws IOException {

        pastQueries.add(LocalTime.now().plus(1, ChronoUnit.MINUTES));
        return RequesterJSON.getJSON(url);

    }

    // Gets the information for a game given an ID

    public JSONObject getGame(String id) throws IOException {

        pastQueries.add(LocalTime.now().plus(1, ChronoUnit.MINUTES));
        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/" + id);

    }

    public JSONObject getCategory(String id) throws IOException {

        pastQueries.add(LocalTime.now().plus(1, ChronoUnit.MINUTES));
        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/categories/" + id);

    }

    public JSONObject getVariable(String id) throws IOException {

        pastQueries.add(LocalTime.now().plus(1, ChronoUnit.MINUTES));
        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/variables/" + id);

    }

    //Gets the top run given a game, category, variable, and option.
    public JSONObject getRecord(String game, String category, String variableID, String optionID) throws IOException {

        pastQueries.add(LocalTime.now().plus(1, ChronoUnit.MINUTES));
        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/leaderboards/" + game + "/category/" + category + "?var-" + variableID + "=" + optionID);

    }


    public JSONObject getLevel(String id) throws IOException {

        pastQueries.add(LocalTime.now().plus(1, ChronoUnit.MINUTES));
        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/levels/" + id);

    }

    // TODO: Come up with something better!
    // Poorly formatted code to do the below, but with two variables and options.
    public JSONObject getRecord(String game, String category, String variable1ID, String option1ID, String variable2ID, String option2ID) throws IOException {

        pastQueries.add(LocalTime.now().plus(1, ChronoUnit.MINUTES));
        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/leaderboards/" + game + "/category/" + category + "?var-" + variable1ID + "=" + option1ID + "&var-" + variable2ID + "=" + option2ID);

    }

    // Gets the level record with one variable.
    public JSONObject getLevelRecord(String game, String level, String variableID, String optionID) throws IOException {

        JSONObject JSONgame = this.getGame(game);
        // Bad code. Sorry
        String prettyName = JSONgame.getJSONObject("data").getString("abbreviation");
        String levelCategory = FileReader.readFile("src/main/resources/" + prettyName + "/levels/levels-category.txt").get(0);
        pastQueries.add(LocalTime.now().plus(1, ChronoUnit.MINUTES));
        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/leaderboards/" + game + "/level/" + level + "/" + levelCategory + "?var-" + variableID + "=" + optionID);

    }

    // The same but without variables.
    public JSONObject getLevelRecord(String game, String level) throws IOException {

        JSONObject JSONgame = this.getGame(game);
        // Bad code. Sorry
        String prettyName = JSONgame.getJSONObject("data").getString("abbreviation");
        String levelCategory = FileReader.readFile("src/main/resources/" + prettyName + "/levels/levels-category.txt").get(0);
        pastQueries.add(LocalTime.now().plus(1, ChronoUnit.MINUTES));
        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/leaderboards/" + game + "/level/" + level + "/" + levelCategory);

    }

}
