import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("hi :3");

        Map<String, String> map = Requester.getJSON("https://www.speedrun.com/api/v1/users?lookup=TheEskot");

        System.out.println(map);

    }

}
