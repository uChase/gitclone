import java.io.File;
import java.util.ArrayList;

public class tree {
    public static void main(String[] args) {

    }

    File tree;
    ArrayList<String> list;

    public tree() {
        tree = new File("./objects/tree");
        list = new ArrayList<String>();
    }

    public void add(String treeEntry) {
        list.add(treeEntry);
    }

    public void remove(String treeEntry) {
        boolean removed = false;
        for (int i = 0; i < list.size() && removed == false; i++) {
            if (list.get(i).contains(treeEntry)) {
                list.remove(i);
                removed = true;
            }
        }
    }
}
