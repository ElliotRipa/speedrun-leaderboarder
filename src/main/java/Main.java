import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("hi :3");

         Map<String, String> map = Requester.getJSON("https://www.speedrun.com/api/v1/users?lookup=TheEskot");
         Map<String, String> map2 = Requester.getPerson("TheEskot");
        Map<String, String> map3 = Requester.getGame("v1pxjz68");

        Map<String, String> runs = Requester.getJSON("https://www.speedrun.com/api/v1/runs?game=hollowknight&max=2000");
        Map<String, String> categories = Requester.getJSON("https://www.speedrun.com/api/v1/games/hollowknight/categories");
        Map<String, String> records = Requester.getJSON("https://www.speedrun.com/api/v1/games/hollowknight/records?miscellaneous=no&scope=full-game");

        System.out.println("hai :3");




    }

}
