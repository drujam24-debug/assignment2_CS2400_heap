public class heap {
    public class maxHeapSequential(int[] arr){ //time complexity O(nlogn) because we call heapifyUp per n elements AND

        int swapCount = 0;

        for (int i = 1; i < arr.length; i++){
            heapifyUp(arr, i);
        }

    }

    private static void heapifyUp(int[] arr, int index) {
        int current = index; //number nodes
        int parent = (current - 1)/2; //calc for non-leaf nodes

        while (current > 0 && arr[current] > arr[parent]) {
            
            swap(arr, current, parent);
            current = parent;
            parent = (current - 1)/2;
 
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];//child or current holds parent's old value cause it needs to be smaller than parent
        arr[j] = temp; //old parent is now hold's child's value cause it was greather than it
        swapCount++;
    }

    public class maxHeapOptimal(){ //time complexity O(n) because we call heapifyDown per n/2 elements AND

        int swapCount = 0;

    }

    public class maxHeapRemove(int[] arr){

    }


}
