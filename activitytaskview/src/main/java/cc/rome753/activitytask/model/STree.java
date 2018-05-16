package cc.rome753.activitytask.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by rome753 on 2017/3/31.
 */
public class STree {

    private String tab1, tab2, tab3;

    private STNode root;

    public STree() {
        root = new STNode("");
    }

    public void setTabs(String tab1, String tab2, String tab3){
        this.tab1 = tab1;
        this.tab2 = tab2;
        this.tab3 = tab3;
    }

    public void add(LinkedList<String> list) {
        STNode node = root;
        while (!list.isEmpty()) {
            String s = list.removeLast();
            if (!node.children.containsKey(s)) {
                STNode newNode = new STNode(s);
                node.children.put(s, newNode);
            }
            node = node.children.get(s);
        }
    }

    public void remove(LinkedList<String> list) {
        if (list.isEmpty()) return;
        STNode node = root;
        while (list.size() > 1) {
            String s = list.removeLast();
            if (node.children.containsKey(s)) {
                node = node.children.get(s);
            } else return;
        }
        node.children.remove(list.getLast());
    }

    public List<String> convertToList(){
        List<String> res = new ArrayList<>();
        convert(res, root, "", true);
        return res;
    }

    private void convert(List<String> res, STNode node, String pre, boolean end){
        if(node != root){
            String s = pre + (end ? tab2 : tab3) +
                    node.name;
            res.add(s);
        }
        int i = 0;
        for(Map.Entry<String, STNode> entry : node.children.entrySet()){
            i++;
            boolean subEnd = i == node.children.size();
            String subPre = pre + (node == root ? "" : (end ? "        " : tab1 + "   "));
            convert(res, entry.getValue(), subPre, subEnd);
        }
    }

    static class STNode {

        String name;
        HashMap<String, STNode> children;

        STNode(String name) {
            this.name = name;
            this.children = new HashMap<>();
        }
    }

}