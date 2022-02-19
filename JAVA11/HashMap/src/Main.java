import java.util.HashMap;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>();
        map.put("s2","ss");
        map.put("s2","xx");
        map.remove("s2");
//        map.put("vg3","tt");
//        map.put(null,"ss");

        for (String key : map.keySet()){
            System.out.println("key : " + key);
            System.out.println("value : " + map.get(key));
        }
    }
}