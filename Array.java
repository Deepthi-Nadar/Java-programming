import java.util.*;

public class Array {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] arr = new int[5];

        
        System.out.println("Enter 5 numbers:");
        for (int i = 0; i < 5; i++) {
            arr[i] = sc.nextInt();
        }

        // Sorting (Bubble Sort)
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        System.out.println("\nSorted array:");
        for (int i = 0; i < 5; i++) {
            System.out.print(arr[i] + " ");
        }

        // Second lowest and second highest
        System.out.println("\nSecond Lowest: " + arr[1]);
        System.out.println("Second Highest: " + arr[3]);

    }
}
