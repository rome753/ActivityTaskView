package cc.rome753.activitytask.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rome753 on 2017/3/31.
 */
public class FTree {

    private String tab1 = "" + '\u2502';            // |
    private String tab2 = "" + '\u2514' + '\u2500'; // |_
    private String tab3 = "" + '\u251c' + '\u2500'; // |-

    private Node root;

    public FTree() {
        root = new Node("");
    }

    public void add(List<String> list, String lifecycle) {
        lifeMap.put(list.get(0), lifecycle);
        Node node = root;
        while (!list.isEmpty()) {
            String s = list.remove(list.size() - 1);
            if (!node.children.containsKey(s)) {
                Node newNode = new Node(s);
                node.children.put(s, newNode);

            }
            node = node.children.get(s);
        }
    }

    public void remove(List<String> list) {
        if (list.isEmpty()) return;
        lifeMap.remove(list.get(0));
        Node node = root;
        while (list.size() > 1) {
            String s = list.remove(list.size() - 1);
            if (node.children.containsKey(s)) {
                node = node.children.get(s);
            } else return;
        }
        String last = list.get(list.size() - 1);
        node.children.remove(last);
    }

    public List<String> convertToList(){
        List<String> res = new ArrayList<>();
        convert(res, root, "", true);
        return res;
    }

    private void convert(List<String> res, Node node, String pre, boolean end){
        if(node != root){
            String s = pre + (end ? tab2 : tab3) + node.name;
            res.add(s);
        }
        int i = 0;
        for(Map.Entry<String, Node> entry : node.children.entrySet()){
            i++;
            boolean subEnd = i == node.children.size();
            String subPre = pre + (node == root ? "" : (end ? "        " : tab1 + "   "));
            convert(res, entry.getValue(), subPre, subEnd);
        }
    }

    private static class Node {

        String name;
        HashMap<String, Node> children;

        Node(String name) {
            this.name = name;
            this.children = new HashMap<>();
        }
    }

    public String getLifecycle(String name) {
        return lifeMap.get(name);
    }

    private HashMap<String, String> lifeMap = new HashMap<>();
    public void updateLifecycle(String key, String value) {
        lifeMap.put(key, value);
    }
}