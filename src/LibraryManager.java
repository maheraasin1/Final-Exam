import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryManager {
    private ArrayList<Book> books;
    private ExecutorService executorService;

    // Constructor
    public LibraryManager() {
        books = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(2); // Two threads for concurrent tasks
    }

    // Add a book
    public synchronized void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book);
    }

    // Search books
    public synchronized List<Book> searchBook(String query) {
        query = query.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query) || book.getAuthor().toLowerCase().contains(query)) {
                result.add(book);
            }
        }
        return result;
    }

    // Sort books by title using concurrency
    public void sortBooksByTitle() {
        executorService.submit(() -> {
            synchronized (books) {
                books.sort(Comparator.comparing(Book::getTitle));
                System.out.println("Books sorted by title.");
            }
        });
    }

    // Sort books by publication year using concurrency
    public void sortBooksByYear() {
        executorService.submit(() -> {
            synchronized (books) {
                books.sort(Comparator.comparingInt(Book::getPublicationYear));
                System.out.println("Books sorted by year.");
            }
        });
    }

    // Save catalog to file using concurrency
    public void saveCatalogToFile(String filePath) {
        executorService.submit(() -> {
            synchronized (books) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    for (Book book : books) {
                        writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getGenre() + "," + book.getPublicationYear());
                        writer.newLine();
                    }
                    System.out.println("Catalog saved to " + filePath);
                } catch (IOException e) {
                    System.err.println("Error saving catalog: " + e.getMessage());
                }
            }
        });
    }

    // Load catalog from file using concurrency
    public void loadCatalogFromFile(String filePath) {
        executorService.submit(() -> {
            synchronized (books) {
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    books.clear();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 4) {
                            String title = parts[0].trim();
                            String author = parts[1].trim();
                            String genre = parts[2].trim();
                            int year = Integer.parseInt(parts[3].trim());
                            books.add(new Book(title, author, genre, year));
                        }
                    }
                    System.out.println("Catalog loaded from " + filePath);
                } catch (IOException e) {
                    System.err.println("Error loading catalog: " + e.getMessage());
                }
            }
        });
    }

    // Display books
    public synchronized void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

    // Shutdown the executor service
    public void shutdown() {
        executorService.shutdown();
        System.out.println("Executor service shut down.");
    }
}
