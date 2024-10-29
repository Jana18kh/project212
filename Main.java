import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
///Users/jana/Downloads/data/stop.txt
public class Main {
    public static void main(String[] args) {
        try {
            // Specify the paths to your stop words file and dataset
            String stopWordsFilePath = "/Users/jana/Downloads/data/stop.txt";
            String datasetFilePath = "/Users/jana/Downloads/data/dataset.csv";

            // Initialize Document Processor and Indexer
            DocumentProcessor processor = new DocumentProcessor(stopWordsFilePath);
            Map<Integer, List<String>> documents = processor.readDocuments(datasetFilePath);
            Indexer indexer = new Indexer();
            indexer.buildIndex(documents);

            // Initialize BooleanQueryProcessor and RankedQueryProcessor
            BooleanQueryProcessor booleanQueryProcessor = new BooleanQueryProcessor(indexer);
            RankedQueryProcessor rankedQueryProcessor = new RankedQueryProcessor(indexer);

            // Initialize and start the user interface
            UserInterface ui = new UserInterface(booleanQueryProcessor, rankedQueryProcessor);
            ui.start();

        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }
}
