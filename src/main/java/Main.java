import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("hi :3");

        Map<String, String> map = Requester.getJSON("https://www.speedrun.com/api/v1/users?lookup=TheEskot");

        System.out.println(map);

    }

}
