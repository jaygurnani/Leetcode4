import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class main {

    public static void main(String[] args)  {

        //int[] input = { 4,2,3,1};
        //int[] output = findBuildings(input);

        int[] nums1 = {1,2,3,0,0,0};
        int m = 3;
        int[] nums2 = {2,5,6};
        int n = 3;
        merge(nums1, m, nums2, n);

        System.out.println(nums1);
    }

    public static int[] findBuildings(int[] heights) {
        List<Integer> result = new ArrayList<>();
        int maxHeight = heights[heights.length-1];
        result.add(heights.length-1);

        for(int i = heights.length-2; i >= 0; --i) {
            int curr = heights[i];
            if (curr > maxHeight) {
                result.add(i);
                maxHeight = curr;
            }
        }

        Collections.sort(result);
        return result.stream().mapToInt(i -> i).toArray();
    }

    public Node lowestCommonAncestor(Node p, Node q) {
        List<Node> path = findPath(p);
        while (q.parent != null) {
            for(Node n: path) {
                if (n == q) {
                    return q;
                }
            }
            q = q.parent;
        }

        return q;
    }

    public static List<Node> findPath(Node p) {
        List<Node> path = new ArrayList<>();
        while(p.parent != null) {
            path.add(p);
            p = p.parent;
        }
        return path;
    }


    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] inputNums1 = Arrays.copyOf(nums1, n+m);

        int mPointer = 0;
        int nPointer = 0;
        for(int i = 0; i < nums1.length; i++) {
            if ( nPointer >=n || (mPointer < m && inputNums1[mPointer] < nums2[nPointer])) {
                nums1[i] = inputNums1[mPointer];
                mPointer++;
            } else {
                nums1[i] = nums2[nPointer];
                nPointer++;
            }
        }
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }

}


