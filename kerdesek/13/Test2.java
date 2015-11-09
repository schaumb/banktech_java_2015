import java.util.Map;
import java.util.HashMap;

public class Test2 {

    private static Map<String, Map<String, String>> map2 = new HashMap<String, Map<String, String>>();

    static {
        Map<String, String> entry = new HashMap<String, String>();
        entry.put("foo2", "bar2");
        map2.put("foo", entry);

        entry = new HashMap<String, String>();
        entry.put("foo4", "bar4");
        map2.put("foo3", entry);
    }

    public static void main(String[] args) {
        System.out.println(map2);
    }
}