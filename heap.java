public class heap {

    private static int swapCount = 0;

    //insert ele one at a time
    //time complexity O(nlogn) because we call heapifyUp per n elements and each cost O(logn)

    public class maxHeapSequential(int[] arr){ 
        swapCount = 0;

        for (int i = 1; i < arr.length; i++){
            heapifyUp(arr, i);
        }

        return swapCount;

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

        swapCount = 0;
 
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, i, arr.length);
        }
 
        return swapCount;
    }

    public class maxHeapRemove(int[] arr){

    }


}
