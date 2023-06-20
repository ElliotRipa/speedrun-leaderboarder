import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Level {

    String id;
    String name;
    String game;
    String prettyGame;

    ArrayList<Variable> variables = new ArrayList<>();


    // Generates an incomplete category object given an appropriate JSONObject
    public Level(JSONObject jsonObject) throws IOException {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        // Magic numbers are bad, but it's only temporary... Hopefully...
        this.game = jsonObject.getJSONArray("links").getJSONObject(1).getString("uri").substring(38);
        this.prettyGame = Requester.getGame(game).getJSONObject("data").getString("abbreviation");
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
    }

    // This is horrible code practise. I am so, so sorry.
    public JSONArray getLeaderboards() throws IOException {

        JSONArray result = new JSONArray();

        if(variables.size() == 0) {
            result.put(Requester.getLevelRecord(game, id));
        }

        if(variables.size() == 1) {
            Variable var = variables.get(0);

            for(int i = 0 ; i < var.size() ; i++) {
                result.put(Requester.getLevelRecord(game, id, var.id, var.options.get(i).getID()));
            }
        }

        // Here's where it gets really bad.
        else if(variables.size() == 2) {

            Variable var0 = variables.get(0);
            Variable var1 = variables.get(1);

            for(int i = 0 ; i < var0.size() ; i++) {
                for(int j = 0 ; j < var1.size() ; j++) {
                    result.put(Requester.getRecord(game, id, var0.id, var0.options.get(i).getID(), var1.id, var1.options.get(j).getID()));
                }
            }

        }
        return result;
    }


    public String getName() {
        return name;
    }

    public String getPrettyGame() {
        return prettyGame;
    }

}
