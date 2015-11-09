import java.util.Map;
import java.util.HashMap;

public class Test {

    private static Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>() {
        {
            put("foo", new HashMap<String, String>() {
                {
                    put("foo2", "bar2");
                }
            });
            put("foo3", new HashMap<String, String>() {
                {
                    put("foo4", "bar4");
                }
            });
        }
    };

    public static void main(String[] args) {
        System.out.println(map);
    }
}