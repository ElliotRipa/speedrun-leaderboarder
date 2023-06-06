import org.json.JSONObject;

import java.io.IOException;

public class HollowKnightGetter {

    public static void HollowKnightGetterMain() throws IOException {

        /*ArrayList<Map<String, String>> patches = new ArrayList<>();
        Map<String, String> vars = Requester.getJSON("https://www.speedrun.com/api/v1/games/76rqmld8/variables");
        String hi =
        vars.get("data");

        System.out.println("hi");*/

        JSONObject object = RequesterJSON.getJSON("https://www.speedrun.com/api/v1/users?lookup=TheEskot");

        System.out.println(object);



    }


}
