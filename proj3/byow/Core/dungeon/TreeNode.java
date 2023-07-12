package byow.Core.dungeon;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {
    public TreeNode<T> left;
    public TreeNode<T> right;
    public T region;

    public TreeNode(T region) {
        this.region = region;
    }

    /** Get the bottom-most leaves */
    public List<T> getRegions(TreeNode<T> node) {
        List<T> result = new ArrayList<>();
        if(this.left != null && this.right != null) {
            result.addAll(getRegions(this.left));
            result.addAll(getRegions(this.right));
        } else {
            result.add(this.region);
        }
        return result;
    }
}