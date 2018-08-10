// cake
// Jerred Shepherd

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
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

            int max = solve(layers, 0, 0);
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

    public static int solve(int[] layers, int currentLayer, int diameterOfPreviousLayer) {
        int diameterOfCurrentLayer = layers[currentLayer];

        if (currentLayer == layers.length - 1) {
            return canTakeLayer(diameterOfCurrentLayer, diameterOfPreviousLayer) ? 1 : 0;
        } else {
            int maxHeight;
            int maxSizeIfLayerIsSkipped = solve(layers, currentLayer + 1, diameterOfPreviousLayer);
            int maxHeightIfLayerIsTaken;

            if (canTakeLayer(diameterOfCurrentLayer, diameterOfPreviousLayer)) {
                maxHeightIfLayerIsTaken = 1 + solve(layers, currentLayer + 1, diameterOfCurrentLayer);
                maxHeight = max(maxHeightIfLayerIsTaken, maxSizeIfLayerIsSkipped);
            } else {
                maxHeight = maxSizeIfLayerIsSkipped;
            }

            return maxHeight;
        }
    }


}
