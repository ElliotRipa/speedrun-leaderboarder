import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HollowKnightGetter {

    public static void HollowKnightGetterMain() throws IOException {

        /*ArrayList<Map<String, String>> patches = new ArrayList<>();
        Map<String, String> vars = Requester.getJSON("https://www.speedrun.com/api/v1/games/76rqmld8/variables");
        String hi =
        vars.get("data");

        System.out.println("hi");*/

        System.out.println(getGame("hollowknight"));


        JSONObject categories = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/76rqmld8/categories");

        System.out.println(categories);

        // Get the variables for Hollow Knight
        JSONObject vars = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/76rqmld8/variables");

        String patchID = (vars.getJSONArray("data").getJSONObject(0)).getString("id");

        System.out.println(patchID);

        System.out.println("Bweakpoint uwu :3");

        // Get HK patches
        JSONObject patches = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/variables/" + patchID);

        JSONObject PatchIDs = ((patches.getJSONObject("data")).getJSONObject("values")).getJSONObject("choices");

        System.out.println(patches);

        Variable v = new Variable(vars.getJSONArray("data").getJSONObject(0));

        System.out.println(v);

    }

    public static JSONObject getGame(String id) throws IOException {

        return RequesterJSON.getJSON("https://www.speedrun.com/api/v1/games/" + id);

    }


}
