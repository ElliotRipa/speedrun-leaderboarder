import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RequesterJSON {


    // Gets the information for a person given a name

    public static JSONObject getJSON(String url) throws IOException {

        URL obj = new URL(url);

        return getJSON(obj);

    }

    // Gets any information given a URL.
    public static JSONObject getJSON(URL url) throws IOException {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        if(con != null) {
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();

            System.out.println("'GET' request is sent to URL : " + url + "\nResponse Code: " + responseCode);

            if(responseCode == 200) {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONObject obj = new JSONObject(inline);

                //Get the required data using its key
                return obj;
            }

            }

        return null;

    }

}
