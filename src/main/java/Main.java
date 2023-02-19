import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("hi :3");

        String url = "https://www.speedrun.com/api/v1/users?lookup=TheEskot";

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        if(con instanceof HttpURLConnection) {
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();

            System.out.println("'GET' request is sent to URL : " + url + "\nResponse Code: " + responseCode);

            if(responseCode == 200) {


                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());

            }

        }

    }

}
