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
        int[] data = lines.stream().map(String::trim).filter(s -> !s.isEmpty())
                          .mapToInt(Integer::parseInt).toArray();

        int[] sequential = Arrays.copyOf(data, data.length);
        int[] optimal = Arrays.copyOf(data, data.length);

        try (PrintWriter out = new PrintWriter("output.txt")) {
            out.println(LINE);

            // Sequential insertions
            int seqSwaps = heap.maxHeapSequential(sequential);
            out.println("Heap built using sequential insertions: " + first10(sequential));
            out.println("Number of swaps in the heap creation: " + seqSwaps);
            removeTen(sequential);
            out.println("Heap after 10 removals: " + first10(sequential));

            // Optimal (bottom-up) method
            int optSwaps = heap.maxHeapOptimal(optimal);
            out.println("Heap built using optimal method: " + first10(optimal));
            out.println("Number of swaps in the heap creation: " + optSwaps);
            removeTen(optimal);
            out.println("Heap after 10 removals: " + first10(optimal));

            out.println(LINE);
        }
    }

    // Removes the max 10 times; removed values end up past the shrunken heap
    private static void removeTen(int[] arr) {
        int size = arr.length;
        for (int i = 0; i < 10; i++) {
            heap.maxHeapRemove(arr, size);
            size--;
        }
    }

    // First 10 array values, comma-separated, followed by ",..."
    private static String first10(int[] arr) {
        return Arrays.stream(arr, 0, 10)
                     .mapToObj(String::valueOf)
                     .collect(Collectors.joining(",")) + ",...";
    }
}