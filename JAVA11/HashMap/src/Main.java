import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>();
        map.replace("a", "1");
        System.out.println(map.get("a"));

        map.put("a", "1");
        System.out.println(map.get("a"));

        map.replace("a", "2");
        System.out.println(map.get("a"));
    }
}