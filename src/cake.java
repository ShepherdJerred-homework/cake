// cake
// Jerred Shepherd
// The nested hashmap is a crime, but hey it's fast

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class cake {
    public static void main(String args[]) throws FileNotFoundException {
        File input = new File("cake.in");
        File output = new File("cake.out");

        Scanner scanner = new Scanner(input);
        PrintWriter printWriter = new PrintWriter(output);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.equals("0")) {
                break;
            }

            int[] layers = Stream.of(line)
                    .map(s -> s.split(" "))
                    .flatMap(Arrays::stream)
                    .mapToInt(Integer::parseInt)
                    .skip(1)
                    .toArray();

            int max = solve(layers, 0, 0, new HashMap<>());
            System.out.println(max);
            printWriter.println(max);
        }

        printWriter.close();
    }

    public static int max(int l, int r) {
        return l > r ? l : r;
    }

    public static boolean canTakeLayer(int diameterOfLayer, int diameterOfPreviousLayer) {
        return diameterOfPreviousLayer == 0 || diameterOfLayer <= diameterOfPreviousLayer;
    }

    public static int solve(int[] layers, int currentLayer, int diameterOfPreviousLayer, Map<Integer, Map<Integer, Integer>> memo) {
        int diameterOfCurrentLayer = layers[currentLayer];

        if (memo.containsKey(currentLayer)) {
            if (memo.get(currentLayer).containsKey(diameterOfPreviousLayer)) {
                return memo.get(currentLayer).get(diameterOfPreviousLayer);
            }
        }

        if (currentLayer == layers.length - 1) {
            return canTakeLayer(diameterOfCurrentLayer, diameterOfPreviousLayer) ? 1 : 0;
        } else {
            int maxHeight;
            int maxSizeIfLayerIsSkipped = solve(layers, currentLayer + 1, diameterOfPreviousLayer, memo);
            int maxHeightIfLayerIsTaken;

            if (canTakeLayer(diameterOfCurrentLayer, diameterOfPreviousLayer)) {
                maxHeightIfLayerIsTaken = 1 + solve(layers, currentLayer + 1, diameterOfCurrentLayer, memo);
                maxHeight = max(maxHeightIfLayerIsTaken, maxSizeIfLayerIsSkipped);
            } else {
                maxHeight = maxSizeIfLayerIsSkipped;
            }


            if (memo.containsKey(currentLayer)) {
                memo.get(currentLayer).put(diameterOfPreviousLayer, maxHeight);
            } else {
                Map<Integer, Integer> newMap = new HashMap<>();
                newMap.put(diameterOfPreviousLayer, maxHeight);
                memo.put(currentLayer, newMap);
            }
            return maxHeight;
        }
    }


}
