import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Sort Books by Title");
            System.out.println("4. Sort Books by Year");
            System.out.println("5. Save Catalog to File");
            System.out.println("6. Load Catalog from File");
            System.out.println("7. Display All Books");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int year = scanner.nextInt();
                    manager.addBook(new Book(title, author, genre, year));
                    break;

                case 2:
                    System.out.print("Enter search query: ");
                    String query = scanner.nextLine();
                    List<Book> results = manager.searchBook(query);
                    if (results.isEmpty()) {
                        System.out.println("No books found.");
                    } else {
                        results.forEach(System.out::println);
                    }
                    break;

                case 3:
                    manager.sortBooksByTitle();
                    break;

                case 4:
                    manager.sortBooksByYear();
                    break;

                case 5:
                    System.out.print("Enter file path to save: ");
                    String savePath = scanner.nextLine();
                    manager.saveCatalogToFile(savePath);
                    break;

                case 6:
                    System.out.print("Enter file path to load: ");
                    String loadPath = scanner.nextLine();
                    manager.loadCatalogFromFile(loadPath);
                    break;

                case 7:
                    manager.displayBooks();
                    break;

                case 8:
                    manager.shutdown();
                    scanner.close();
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
