import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class heap {
    private static int swapCount;
    private int lastIndex;
    private static int[] heapArray;
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAP = 10000;

    public heap() {
        this(DEFAULT_CAPACITY);
    }

    public heap(int capacity) {
        if (capacity < DEFAULT_CAPACITY) {
            capacity = DEFAULT_CAPACITY;
        }
        else{
            checkCapacity(capacity);
        }
        
        @SusspressWarnings("unchecked")
        int tempHeap[] = new int [capacity]:
        heapArray = tempHeap;
        lastIndex = -1;
        swapCount = 0;
        initialized = true; 
    }

    public static void checkCapacity(int capacity){
        if (capacity > MAX_CAP) {
            throw new IllegalArgumentException("Capacity exceeds maximum limit of " + MAX_CAP);
        }
    }

    public void checkInitialization(){
        if (!intializaed){
            throw new SecuirityException("Heap is yet to be initialized, must be an error in creating heapArray");
        }
    }

    public int getMax(){
        checkInitialization();
        int root = heapArray[0];
        return root;
    }

    public boolean isEmpty(){
        return lastIndex == -1; //nothing added, -1 so clear out of bounds
    }

    public static int getSwapCount() { //should be easier to ouput swapCount and array
        return swapCount;
    }

    public int getSize(){
        return lastIndex + 1;
    }

    public void clear(){
        checkInitialization();
        
        while (!isEmpty()){
            heapArray[lastIndex] = null;
            lastIndex--;
        }

        lastIndex = -1;
    }

    public int removeMax(){
        checkInitialization();
        if (isEmpty()){
            throw new IllegalStateException("Heap is empty, cannot remove max");
        }

        int root = heapArray[0];
        heapArray[0] = heapArray[lastIndex];
        lastIndex--;
        heapifyDown(0);
        return root;
    }

    public static int maxHeapSequential(int[] arr) {
        swapCount = 0;

        for (int i = 1; i < arr.length; i++) {
            heapifyUp(arr, i);
        }

        return swapCount;
    }

    private static void heapifyUp(int[] arr, int index) {
        int current = index;
        int parent = (current - 1) / 2;

        while (current > 0 && arr[current] > arr[parent]) { //feel like some integers might get revisited, might be an O(n^2) time complexity
            swap(arr, current, parent);
            current = parent;
            parent = (current - 1) / 2;
        }
    }

    public static int maxHeapOptimal(int[] arr) {
        swapCount = 0;

        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, i, arr.length);
        }

        return swapCount;
    }

    private static void heapifyDown(int[] arr, int index, int size) {
        int current = index;

        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int largest = current;

            if (left < size && arr[left] > arr[largest]) {
                largest = left;
            }
            if (right < size && arr[right] > arr[largest]) {
                largest = right;
            }
            if (largest == current) {
                return;
            }

            swap(arr, current, largest);
            current = largest;
        }
    }

    public static void maxHeapRemove(int[] arr, int size) {
        if (size <= 0 || size > arr.length) {
            throw new IllegalArgumentException("Heap size must be between 1 and the array length.");
        }

        swap(arr, 0, size - 1);
        heapifyDown(arr, 0, size - 1);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        swapCount++;
    }

    public static void main(String[] args) throws IOException {
        int[] data = Files.readAllLines(Paths.get("data.txt")).stream() //read file and convert into array, proccessing the lines one at a time with .stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty()) //skip blank lines
                .mapToInt(Integer::parseInt) //convert each line to an integer
                .toArray(); //collect the integers into an array
        int[] sequential = Arrays.copyOf(data, data.length);
        int[] optimal = Arrays.copyOf(data, data.length);

        int sequentialSwaps = maxHeapSequential(sequential);
        System.out.println("Array after maxHeapSequential: " + Arrays.toString(sequential));
        System.out.println("Number of swaps (sequential): " + getSwapCount());

        int optimalSwaps = maxHeapOptimal(optimal);
        System.out.println("Array after maxHeapOptimal: " + Arrays.toString(optimal));
        System.out.println("Number of swaps (optimal): " + getSwapCount());
    }
}
