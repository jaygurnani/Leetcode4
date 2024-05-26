import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class main {

    public static void main(String[] args)  {

        int[] input = { 4,2,3,1};
        int[] output = findBuildings(input);

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

}


