import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public final class heap {
    private static int swapCount;
    private int lastIndex;
    private Integer[] heapArray;
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
        
        Integer tempHeap[] = new Integer [capacity];
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

    private void ensureCapacity(int requiredCapacity) {
        checkCapacity(requiredCapacity);
        if (requiredCapacity > heapArray.length) {
            int newCapacity = Math.min(heapArray.length * 2, MAX_CAP);
            heapArray = Arrays.copyOf(heapArray, Math.max(newCapacity, requiredCapacity));
        }
    }

    public void checkInitialization(){
        if (!initialized){
            throw new SecurityException("Heap is yet to be initialized, must be an error in creating heapArray");
        }
    }

    public Integer getMax(){
        checkInitialization();
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty, cannot get max");
        }
        return heapArray[0];
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

    public Integer removeMax(){
        checkInitialization();
        Integer root = null;

        if (isEmpty()){
            throw new IllegalStateException("Heap is empty, cannot remove max");
        }

        if (!isEmpty()){
            root = heapArray[0];
            heapArray[0] = heapArray[lastIndex];
            lastIndex--;
            reheap(0);
        }

        return root;
    }

    public void reheap(int rootIndex){
        boolean done = false;
        Integer orphan = heapArray[rootIndex]; //or parent kinda, the very root with no parents
        int leftChildIndex = (2 * rootIndex) + 1; //since our root is at index 0 FIXXXXXXXXXXXXXXX

        while (!done && (leftChildIndex <= lastIndex)){
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;

            if (rightChildIndex <= lastIndex
                    && heapArray[rightChildIndex].compareTo(heapArray[largerChildIndex]) > 0) {
                largerChildIndex = rightChildIndex;
            }

            if (orphan.compareTo(heapArray[largerChildIndex]) < 0){
                heapArray[rootIndex] = heapArray[largerChildIndex];
                rootIndex = largerChildIndex; //we moving to larger child as new root to repair rest of subtree
                leftChildIndex = (2 * rootIndex) + 1;
            }
            else {
                done = true;
            }
        }

        heapArray[rootIndex] = orphan;  //assure we leave the node at right spot in tree after repair
    }

    public void add (Integer newEntry){
        checkInitialization();
        ensureCapacity(lastIndex + 2); //lastnidex is an index not exactly size, +1 for next avaibel index, +2 for actual size

        int newIndex = lastIndex + 1;
        int parentIndex = (newIndex - 1) / 2;

        while(newIndex > 0 && newEntry.compareTo(heapArray[parentIndex]) > 0){
            heapArray[newIndex] = heapArray[parentIndex];
            newIndex = parentIndex;
            swapCount++;
            parentIndex = (newIndex - 1) / 2;  
        }

        heapArray[newIndex] = newEntry;
        lastIndex++;
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
