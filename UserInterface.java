import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class UserInterface {
    private BooleanQueryProcessor booleanQueryProcessor;
    private RankedQueryProcessor rankedQueryProcessor;
    private Scanner scanner;

    public UserInterface(BooleanQueryProcessor booleanQueryProcessor, RankedQueryProcessor rankedQueryProcessor) {
        this.booleanQueryProcessor = booleanQueryProcessor;
        this.rankedQueryProcessor = rankedQueryProcessor;
        this.scanner = new Scanner(System.in);
    }

    // Display the main menu and handle user input
    public void start() {
        System.out.println("Welcome to the Simple Search Engine!");
        boolean exit = false;

        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Boolean Retrieval");
            System.out.println("2. Ranked Retrieval");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (choice) {
                case 1:
                    handleBooleanQuery();
                    break;
                case 2:
                    handleRankedQuery();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting the search engine. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handle Boolean Retrieval
    private void handleBooleanQuery() {
        System.out.print("Enter a Boolean query: ");
        String query = scanner.nextLine();
        Set<Integer> results = booleanQueryProcessor.processQuery(query);

        System.out.println("Boolean Retrieval Results for \"" + query + "\":");
        if (results.isEmpty()) {
            System.out.println("No matching documents found.");
        } else {
            System.out.println("Document IDs: " + results);
        }
    }

    // Handle Ranked Retrieval
    private void handleRankedQuery() {
        System.out.print("Enter a Ranked query: ");
        String query = scanner.nextLine();
        List<Map.Entry<Integer, Integer>> rankedResults = rankedQueryProcessor.processQuery(query);

        System.out.println("Ranked Retrieval Results for \"" + query + "\":");
        if (rankedResults.isEmpty()) {
            System.out.println("No matching documents found.");
        } else {
            for (Map.Entry<Integer, Integer> entry : rankedResults) {
                System.out.println("DocID: " + entry.getKey() + " Score: " + entry.getValue());
            }
        }
    }
}
