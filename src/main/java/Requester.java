import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Requester {


    // Gets the information for a person given a name

    public static Map<String, String> getPerson(String name) throws IOException {

        URL obj = new URL("https://www.speedrun.com/api/v1/users?lookup=" + name);

        return getJSON(obj);
    }


    // Gets the information for a game given an ID

    public static JSONObject getGame(String id) throws IOException {

        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/" + id);

    }

    //Gets the top run given a game, category, variable, and option.
    public static JSONObject getRecord(String game, String category, String variableID, String optionID) throws IOException {

        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/leaderboards/" + game + "/category/" + category + "?var-" + variableID + "=" + optionID);

    }

    // TODO: Come up with something better!
    // Poorly formatted code to do the same, but with two variables and options.
    public static JSONObject getRecord(String game, String category, String variable1ID, String option1ID, String variable2ID, String option2ID) throws IOException {

        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/leaderboards/" + game + "/category/" + category + "?var-" + variable1ID + "=" + option1ID + "&var-" + variable2ID + "=" + option2ID);

    }


    // Gets any information given a URL.
    public static Map<String, String> getJSON(URL url) throws IOException {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        if(con != null) {
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();

            System.out.println("'GET' request is sent to URL : " + url + "\nResponse Code: " + responseCode);

            if(responseCode == 200) {


                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                ObjectMapper mapper = new ObjectMapper();

                try {
                    Map<String, String> map = mapper.readValue(response.toString(), Map.class);

                    return map;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        return null;

    }

}
