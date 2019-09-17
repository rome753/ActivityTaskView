package cc.rome753.activitytask.model;

import java.util.ArrayList;
import java.util.HashMap;

public class ATree extends HashMap<String, ArrayList<String>> {

    public void add(String key, String value, String lifecycle) {
        ArrayList<String> values = get(key);
        if(values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
        put(key, values);

        lifeMap.put(value, lifecycle);
    }

    public void remove(String key, String value) {
        ArrayList<String> values = get(key);
        if(values == null) {
            return;
        }
        values.remove(value);

        lifeMap.remove(value);
    }

    public String getLifecycle(String name) {
        return lifeMap.get(name);
    }

    private HashMap<String, String> lifeMap = new HashMap<>();
    public void updateLifecycle(String key, String value) {
        lifeMap.put(key, value);
    }
}
