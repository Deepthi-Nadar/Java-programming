import java.util.*;

class Author {
    String name;
    int numberOfBooksWritten;

    Author(String name, int count) {
        this.name = name;
        this.numberOfBooksWritten = count;
    }

    void displayBio() {
        System.out.println(name + " has written " + numberOfBooksWritten + " books.");
    }
}

class Book {
    String title;
    Author author;
    int publicationYear;
    boolean isAvailable = true;

    Book(String title, Author author, int year) {
        this.title = title;
        this.author = author;
        this.publicationYear = year;
    }

    void displayDetails() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author.name);
        System.out.println("Year: " + publicationYear);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
    }
}

class Library {
    Book[] books = new Book[10];
    int count = 0;

    void addBook(Book b) {
        books[count++] = b;
        System.out.println("Book added: " + b.title);
    }

    void borrowBook(String title) {
        for (Book b : books) {
            if (b != null && b.title.equalsIgnoreCase(title) && b.isAvailable) {
                b.isAvailable = false;
                System.out.println("Borrowed: " + b.title);
                return;
            }
        }
        System.out.println("Book not available.");
    }

    void returnBook(String title) {
        for (Book b : books) {
            if (b != null && b.title.equalsIgnoreCase(title)) {
                b.isAvailable = true;
                System.out.println("Returned: " + b.title);
                return;
            }
        }
    }

    void searchBook(String keyword) {
        for (Book b : books) {
            if (b != null && (b.title.toLowerCase().contains(keyword.toLowerCase()) ||
                              b.author.name.toLowerCase().contains(keyword.toLowerCase()))) {
                b.displayDetails();
            }
        }
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Author author1 = new Author("Deepthi Nadar", 3);

        Book book1 = new Book("Java Basics", author1, 2023);
        Book book2 = new Book("OOP Concepts", author1, 2024);
        Book book3 = new Book("Data Structures", author1, 2022);

        Library library = new Library();

        // Performing 5 operations
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        library.borrowBook("Java Basics");
        library.returnBook("Java Basics");
        library.searchBook("Deepthi");

        author1.displayBio();
    }
}
