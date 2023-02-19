import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Requester {


    public static Map<String, String> getJSON(String url) throws IOException {

        URL obj = new URL(url);

        return getJSON(obj);

    }

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
