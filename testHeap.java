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
            heap sequentialHeapTEST = heap.maxHeapSequential(sequentialTEST);

            out.println("Heap built using sequential insertions: " + first10(sequentialHeapTEST.toArray()));
            out.println("Number of swaps in the heap creation: " + heap.getSwapCount());

            removeTen(sequentialHeapTEST);
            out.println("Heap after 10 removals: " + first10(sequentialHeapTEST.toArray()));

            out.println("");
            
            //we create new heap objects once past is complete else swapCount will face interference from previous heap object, so we create new heap objects for each test
            // Optimal (bottom-up) method
            heap optimalHeapTEST = heap.maxHeapOptimal(optimalTEST);

            out.println("Heap built using optimal method: " + first10(optimalHeapTEST.toArray()));
            out.println("Number of swaps in the heap creation: " + heap.getSwapCount());

            removeTen(optimalHeapTEST);
            out.println("Heap after 10 removals: " + first10(optimalHeapTEST.toArray()));

            out.println(LINE);
        }
    }

    // Removes the max 10 times; removed values end up past the shrunken heap
    private static void removeTen(heap heapTEST) {
        
        for (int i = 0; i < 10; i++) {
            heapTEST.removeMax();
        }

    }

    // First 10 array values, comma-separated, followed by ",..."
    private static String first10(Integer[] arr) {
        return Arrays.stream(arr, 0, 10)
                     .map(String::valueOf)
                     .collect(Collectors.joining(",")) + ",..."; //update code list notes!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
}