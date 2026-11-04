import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class testHeap {
    private static final String LINE =
        "=====================================================================";

    public static void main(String[] args) throws IOException {
        // Read the 100 positive, nonduplicate integers from data.txt
        List<String> lines = Files.readAllLines(Paths.get("data.txt"));
        Integer[] data = lines.stream().map(String::trim).filter(s -> !s.isEmpty())
                              .map(Integer::valueOf).toArray(Integer[]::new);

        Integer[] sequentialTEST = Arrays.copyOf(data, data.length);
        Integer[] optimalTEST  = Arrays.copyOf(data, data.length);

        try (PrintWriter out = new PrintWriter("output.txt")) { //to write into the output file, bit rusty with this, might be some errors so check
            out.println(LINE);

            // Sequential insertions
            heap.maxHeapSequential(sequentialTEST);

            System.out.println("Array after maxHeapSequential: " + Arrays.toString(sequentialTEST));
            System.out.println("Number of swaps (sequential): " + heap.getSwapCount());

            removeTen();
            out.println("Heap after 10 removals: " + first10(sequentialTEST));

            // Optimal (bottom-up) method
            heap.maxHeapOptimal(optimalTEST);

            System.out.println("Array after maxHeapOptimal: " + Arrays.toString(optimalTEST));
            System.out.println("Number of swaps (optimal): " + heap.getSwapCount());



            //get rid of
            

            // Optimal (bottom-up) method
           
            removeTen();
            out.println("Heap after 10 removals: " + first10(optimal));

            out.println(LINE);
        }
    }

    // Removes the max 10 times; removed values end up past the shrunken heap
    private static void removeTen(heap ) {
        
        for (int i = 0; i < 10; i++) {
            heap.removeMax();
        }

    }

    // First 10 array values, comma-separated, followed by ",..."
    private static String first10(Integer[] arr) {
        return Arrays.stream(arr, 0, 10)
                     .map(String::valueOf)
                     .collect(Collectors.joining(",")) + ",..."; //update code list notes!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
}