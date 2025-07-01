import java.util.*;
// Custom exception for invalid thread execution
class InvalidThreadException extends Exception {
    public InvalidThreadException(String message) {
        super(message);
    }
}

// Thread to print numbers from 1 to 10 with 1-second delay
class PrintThread extends Thread {
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Printing number: " + i);
                Thread.sleep(1000); // 1 second delay
            }
        } catch (InterruptedException e) {
            System.out.println("PrintThread interrupted: " + e.getMessage());
        }
    }
}

// Thread to calculate factorials from 1 to 5
class FactorialThread extends Thread {
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                long fact = calculateFactorial(i);
                System.out.println("Factorial of " + i + " is " + fact);
            }
        } catch (Exception e) {
            System.out.println("Error in FactorialThread: " + e.getMessage());
        }
    }

    synchronized long calculateFactorial(int n) throws InvalidThreadException {
        if (n < 0) {
            throw new InvalidThreadException("Invalid number for factorial: " + n);
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}


public class MultiThreadedApp {
    public static void main(String[] args) {
        PrintThread pt = new PrintThread();
        FactorialThread ft = new FactorialThread();

        pt.start();
        ft.start();

        try {
            pt.join();  
            ft.join();  
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        System.out.println("All threads finished execution safely.");
    }
}
