import com.sun.source.tree.Tree;
import org.w3c.dom.Node;

import javax.swing.tree.TreeNode;
import java.util.*;
import java.util.stream.IntStream;

public class main {

    public static void main(String[] args)  {

        //int[] input = { 4,2,3,1};
        //int[] output = findBuildings(input);

//        int[] nums1 = {1,2,3,0,0,0};
//        int m = 3;
//        int[] nums2 = {2,5,6};
//        int n = 3;
//        merge(nums1, m, nums2, n);

//        String input = "abbaca";
//        String output = removeDuplicates(input);

//        int[] input = {0,0,0,0};
//        List<List<Integer>> output = threeSum(input);

//        int[] input = {9,9};
//        int[] output = plusOne3(input);
//        Arrays.stream(output).forEach(x -> System.out.println(x));

//        List<List<Integer>> input = Arrays.asList(
//                Arrays.asList(1,2,2,1),
//                Arrays.asList(3,1,2),
//                Arrays.asList(1,3,2),
//                Arrays.asList(2,4),
//                Arrays.asList(3,1,2),
//                Arrays.asList(1,3,1,1));
//        int output = leastBricks2(input);
//        int[] input = {-2,0,1,3};
//        int output = threeSumSmaller(input, 2);

//        String input = "6th Jun 1933";
//        String output = reformatDate(input);
//      int[] input = new int[] {3,2,4,5,0};
        //var output = convertArray(input);

//        int[] input = {3,4,5,6,1,2};
//        int output = search(input, 4);

       // int[] input = {10,1,5,6,7,1};
        //int output = maxProfit(input);

        String input = "zxyzxyz";
        int output = lengthOfLongestSubstring(input);

        System.out.println(output);
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

    public static String removeDuplicates(String s) {
       StringBuilder sb = new StringBuilder();
       for(char c : s.toCharArray()) {
           int size = sb.length();
           if (size > 0 && sb.charAt(size - 1) == c) {
               sb.deleteCharAt(size-1);
           } else {
               sb.append(c);
           }
       }

       return sb.toString();
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> resultList = new HashSet<>();
        Arrays.sort(nums);

        for(int i = 1; i < nums.length-1; i++) {
            int lo = i-1;
            int hi = i+1;

            while (lo >= 0 && hi < nums.length) {
                int result = nums[lo] + nums[i] + nums[hi];

                if (result == 0) {
                    List<Integer> newFound = Arrays.asList(nums[lo], nums[i], nums[hi]);
                    resultList.add(newFound);

                    lo--;
                    hi++;
                }

                if (result > 0) {
                    lo--;
                }

                if (result < 0) {
                    hi++;
                }

            }
        }
        return new ArrayList<>(resultList);
    }

    public static int[] plusOne(int[] digits) {
        int size = digits.length;
        boolean carryOver = false;
        List<Integer> result = new ArrayList<>();
        var reversed = IntStream.range(0, size).map(i -> digits[size-i-1]).toArray();

        if (reversed[0] != 9) {
            reversed[0]++;
            return IntStream.range(0, size).map(i -> reversed[size-i-1]).toArray();
        } else {
            for(int i = 0; i < reversed.length; i++) {
                if (reversed[i] == 9) {
                    carryOver = true;
                    reversed[i] = 0;
                } else {
                    reversed[i]++;
                    return IntStream.range(0, size).map(j -> reversed[size-j-1]).toArray();
                }
            }
            if (carryOver) {
                result.add(1);
            }
        }



        return IntStream.range(0, size).map(i -> reversed[size-i-1]).toArray();

    }

    public static int[] plusOne2(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }

        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }

    public static int[] plusOne3(int[] digits) {
        for(int i = digits.length-1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            } else {
                digits[i] = 0;
            }
        }

        int[] newDigits = new int[digits.length+1];
        newDigits[0] = 1;
        return newDigits;
    }

    public static int leastBricks(List<List<Integer>> wall) {
        int width = 0;
        for(int brick: wall.get(0)) {
            width = width + brick;
        }

        int height = wall.size();
        int[] positions = new int[height];

        int fewestCrossing = height;
        for(int col = 0; col < width-1; col++) {
            int count = 0;

            for(int i = 0; i < height; i++) {
                List<Integer> row = wall.get(i);
                // wtf
                row.set(positions[i], row.get(positions[i])-1);

                if (row.get(positions[i]) == 0) {
                    positions[i]++;
                } else {
                    count++;
                }
            }
            fewestCrossing = Math.min(count,fewestCrossing);
        }


        return fewestCrossing;
    }

    public static int leastBricks2(List<List<Integer>> wall) {
        Map<Integer, Integer> gaps = new HashMap<>();

        for (List<Integer> row: wall) {
            int position = 0;
            for(int i = 0; i < row.size()-1; i++) {
                position = row.get(i) + position;
                gaps.put(position, gaps.getOrDefault(position, 0) +1);
            }
        }

        int fewestCrossing = wall.size();
        if (!gaps.isEmpty()) {
            fewestCrossing = fewestCrossing - Collections.max(gaps.values());
        }

        return fewestCrossing;
    }

    public static int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int count = 0;

        for(int i = 0; i < nums.length-2; i++) {
            int lo = i+1;
            int hi = nums.length-1;

            while (lo < hi) {
                int current = nums[i] + nums[lo] + nums[hi];

                if (current < target) {
                    count = count + (hi-lo);
                    lo++;
                } else {
                    hi--;
                }
            }
        }

        return count;
    }

    int sum = 0;
    public int countPairs(TreeNode root, int distance) {
        traverseNode(root, distance);
        return sum;
    }

    public List<Integer> traverseNode(TreeNode root, int distance) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        List<Integer> left = traverseNode(root.left, distance);
        List<Integer> right = traverseNode(root.right, distance);

        if (left.isEmpty() && right.isEmpty()) {
            list.add(1);
            return list;
        }

        if (!left.isEmpty() && !right.isEmpty()) {
            for (Integer l: left) {
                for (Integer r: right) {
                    if (l + r <= distance) {
                        sum++;
                    }
                }
            }
        }

        for (Integer l : left) {
            list.add(l + 1);
        }

        for (Integer r : right) {
            list.add(r + 1);
        }

        return list;
    }

    public static String reformatDate(String date) {
        String[] split = date.split("\s");
        String day = split[0];
        String month = split[1];
        String year = split[2];

        Map<String, String> monthConvertor = new HashMap<>();
        monthConvertor.put("Jan", "01");
        monthConvertor.put("Feb", "02");
        monthConvertor.put("Mar", "03");
        monthConvertor.put("Apr", "04");
        monthConvertor.put("May", "05");
        monthConvertor.put("Jun", "06");
        monthConvertor.put("Jul", "07");
        monthConvertor.put("Aug", "08");
        monthConvertor.put("Sep", "09");
        monthConvertor.put("Oct", "10");
        monthConvertor.put("Nov", "11");
        monthConvertor.put("Dec", "12");

        String dayString = day.substring(0, day.length() - 2);
        if (dayString.length() == 1) {
            dayString = "0" + dayString;
        }

        String monthString = monthConvertor.get(month);
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append("-");
        sb.append(monthString);
        sb.append("-");
        sb.append(dayString);

        return sb.toString();
    }

    public static int helper(int[] nums, int[] levels) {
        var dp = new HashMap<Integer,Integer>();
        for(var num : nums) {
            var cur_res = Integer.MAX_VALUE;
            for(var level : levels) {
                var prev_res = dp.getOrDefault(level,0);
                cur_res = Math.min(cur_res, prev_res+Math.abs(num-level));
                dp.put(level,cur_res);
            }
        }
        return dp.get(levels[levels.length-1]);
    }

    public static int convertArray(int[] nums) {
        int[] levels = Arrays.stream(nums).distinct().sorted().toArray();

        int[] nums2 = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            nums2[nums.length-1-i]=nums[i];
        }

            return Math.min(helper(nums, levels),helper(nums2, levels));
    }

    int maxArea = 0;
    public int maxAreaOfIsland(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    int newMax = bfs(i,j,grid);
                    maxArea = Math.max(newMax, maxArea);
                }
            }
        }

        return maxArea;
    }

    public int bfs(int i, int j, int[][] grid) {
        int maxRow = grid.length;
        int maxCol = grid[0].length;
        int innerMax = 0;
        Queue<int[]> nodes = new LinkedList<>();
        nodes.offer(new int[]{i,j});
        innerMax++;
        grid[i][j] = 0;

        while(!nodes.isEmpty()) {
            int n = nodes.size();
            for(int size = 0; size < n; size++) {
                int[] item = nodes.poll();
                int x = item[0];
                int y = item[1];

                int[][] directions = new int[][] {{1,0}, {-1,0}, {0,-1}, {0,1} };
                for(int[] direction: directions) {
                    int newX = x + direction[0];
                    int newY = y + direction[1];

                    // We are out of bounds
                    if (newX < 0 || newX >= maxRow || newY < 0 || newY >= maxCol) {
                        continue;
                    }

                    if (grid[newX][newY] == 1) {
                        grid[newX][newY] = 0;
                        nodes.offer(new int[]{newX, newY});
                        innerMax++;
                    }
                }
            }
        }

        return innerMax;
    }

    public int minDistance(String word1, String word2) {
        int word1Length = word1.length();
        int word2Length = word2.length();
        char[] word1Char = word1.toCharArray();
        char[] word2Char = word2.toCharArray();

        int[][] dp = new int[word1Length+1][word2Length+1];

        // base case
        for(int i = 0; i <= word1Length; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        // We are operating on a i+1 and j+1 scenario
        for(int i = 0; i < word1Length; i++) {
            for (int j = 0; j < word2Length; j++) {

                // Base case
                if (word1Char[i] == word2Char[j]) {
                    dp[i+1][j+1] = dp[i][j];
                } else {
                    // insert - move the j pointer as we no longer handle it
                    int insertCost = dp[i][j+1] + 1;

                    // delete - take our i pointer and shift it to the next position
                    int deleteCost = dp[i+1][j] + 1;

                    // replace - force both i and j pointer and shift it
                    int replaceCost = dp[i][j] + 1;

                    int min1 = Math.min(insertCost, deleteCost);
                    int min2 = Math.min(min1, replaceCost);

                    dp[i+1][j+1] = min2;
                }
            }
        }

        return dp[word1Length][word2Length];
    }

    public int findMin(int[] nums) {
        int l = 0;
        int r = nums.length-1;

        while (l <= r) {
            if (nums[l] <= nums[r]) {
                return nums[l];
            }
            int mid = (l+r) / 2;
            if (nums[mid] >= nums[l]) {
                // look on right side
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return 0;
    }

    public static int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int mid = (l+r) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[l] <= nums[mid]) {
                if (target > nums[mid] || target < nums[l]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else {
                if (target < nums[mid] || target > nums [r]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
        }

        return -1;
    }

    public List<List<Integer>> threeSum2(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        Arrays.sort(nums);

        for(int i = 0; i < nums.length-2; i++) {
            int lo = i+1;
            int hi = nums.length-1;

            while (lo < hi) {
                int currentItem = nums[lo] + nums[hi] + nums[i];
                if (currentItem == 0) {
                    result.add(List.of(nums[lo], nums[hi], nums[i]));
                }

                if (currentItem < 0) {
                    lo++;
                } else {
                    hi--;
                }
            }
        }

        return new ArrayList<>(result);
    }

    public TreeNode invertTree(TreeNode root) {
        if(root == null) {
            return null;
        }

        TreeNode node = new TreeNode(root.val);
        node.left = invertTree(root.right);
        node.right = invertTree(root.left);

        return node;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;

        if (current == q || current == p || current == null) {
            return current;
        }

        TreeNode left = lowestCommonAncestor(current.left, p, q);
        TreeNode right = lowestCommonAncestor(current.right, p, q);

        if (left != null && right != null) {
            return current;
        } else if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        }

        return null;
    }


    class PrefixTree {
        TrieObj rootNode;

        public class TrieObj {
            char val;
            HashMap<Character, TrieObj> dict = new HashMap<>();
            boolean isEndNode = false;
        }


        public PrefixTree() {
            rootNode = new TrieObj();
        }

        public void insert(String word) {
            char[] wordCharArray = word.toCharArray();
            TrieObj currentNode = rootNode;

            for(int i = 0; i < wordCharArray.length; i++) {
                // If it does exist
                if (currentNode.dict.containsKey(wordCharArray[i])){

                    // Navigate to this node
                    currentNode = currentNode.dict.get(wordCharArray[i]);
                } else {
                    // if it doesn't exist we need to create it
                    TrieObj newNode = new TrieObj();
                    newNode.val = wordCharArray[i];

                    currentNode.dict.put(wordCharArray[i], newNode);
                    currentNode = newNode;
                }
            }

            // End the Current word
            currentNode.isEndNode = true;

        }

        public boolean search(String word) {
            char[] wordCharArray = word.toCharArray();
            TrieObj currentNode = rootNode;

            for (int i = 0; i < wordCharArray.length; i++) {
                if (!currentNode.dict.containsKey(wordCharArray[i])) {
                    return false;
                } else {
                    currentNode = currentNode.dict.get(wordCharArray[i]);
                }
            }

            if(currentNode.isEndNode) {
                return true;
            }

            return false;
        }

        public boolean startsWith(String prefix) {
            char[] wordCharArray = prefix.toCharArray();
            TrieObj currentNode = rootNode;

            for (int i = 0; i < wordCharArray.length; i++) {
                if (!currentNode.dict.containsKey(wordCharArray[i])) {
                    return false;
                } else {
                    currentNode = currentNode.dict.get(wordCharArray[i]);
                }
            }

            return true;
        }
    }

    public int numIslands(char[][] grid) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    bfs(grid, i, j, rows, cols);
                }
            }
        }
        return count;
    }

    public void bfs(char[][] grid, int i, int j, int rows, int cols) {
        Queue<int[]> nodes = new LinkedList<>();
        nodes.offer(new int[]{i,j});

        while(!nodes.isEmpty()) {
            int n = nodes.size();
            for(int count = 0; count < n; count++) {
                int[] current = nodes.poll();
                int x = current[0];
                int y = current[1];
                grid[x][y] = 0;

                int[][] directions = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};
                for(int[] direction: directions) {
                    int newX = x + direction[0];
                    int newY = y + direction[1];

                    if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) {
                        continue;
                    }

                    if (grid[newX][newY] == '1') {
                        nodes.offer(new int[]{newX, newY});
                    }
                }
            }
        }
    }

    public static int maxProfit(int[] prices) {
        int left = 0;
        int right = 1;
        int maxProfit = 0;

        while (right < prices.length) {
            if (prices[left] < prices[right]) {
                int currentPrice = prices[right] - prices[left];
                maxProfit = Math.max(maxProfit, currentPrice);
            } else {
                left = right;
            }

            right++;
        }

        return maxProfit;
    }

    public static int lengthOfLongestSubstring(String s) {
        int left = 0;
        int longest = 0;

        char[] sCharArray = s.toCharArray();
        HashSet<Character> dict = new HashSet<>();

        while (left < sCharArray.length) {
            if (!dict.contains(sCharArray[left])) {
                dict.add(sCharArray[left]);
            } else {
                dict = new HashSet<Character>();
                longest = Math.max(dict.size(), longest);
            }
            left++;
        }

        return longest;
    }

    public boolean isValidBST(TreeNode root) {
        return isValidBSTWithNumber(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public boolean isValidBSTWithNumber(TreeNode node, double left, double right) {
        if (node == null) {
            return true;
        }

        if (!(left < node.val && node.val < right)) {
            return false;
        }

        return isValidBSTWithNumber(node.left, left, node.val) && isValidBSTWithNumber(node.right, node.val, right);
    }

    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int longest = 0;
        for(int n: numSet) {
            if (!numSet.contains(n-1)) {
                int length = 1;
                while(numSet.contains(n + length)) {
                    length++;
                }

                longest = Math.max(length, longest);
            }
        }
        return longest;
    }


    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        HashMap<Integer, List<Integer>> dict = new HashMap<>();
        levelOrderWithMap(root, dict, 0);

        for(int i = 0 ; i < dict.size(); i++) {
            result.add(dict.get(i));
        }

        return result;
    }

    public void levelOrderWithMap(TreeNode node, HashMap<Integer, List<Integer>> dict, int level) {
        if (node == null) {
            return;
        }

        List<Integer> list = dict.computeIfAbsent(level, x -> new ArrayList<Integer>());
        list.add(node.val);

        levelOrderWithMap(node.left, dict, level+1);
        levelOrderWithMap(node.right, dict, level+1);
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }



}


