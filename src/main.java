import org.w3c.dom.Node;

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

        List<List<Integer>> input = Arrays.asList(
                Arrays.asList(1,2,2,1),
                Arrays.asList(3,1,2),
                Arrays.asList(1,3,2),
                Arrays.asList(2,4),
                Arrays.asList(3,1,2),
                Arrays.asList(1,3,1,1));
        int output = leastBricks2(input);
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

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }

}


