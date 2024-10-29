import java.io.*;
import java.util.*;
import java.util.regex.*;

public class DocumentProcessor {
    private Set<String> stopWords;

    // Constructor to load stop words from a file
    public DocumentProcessor(String stopWordsFile) throws IOException {
        stopWords = new HashSet<>();
        loadStopWords(stopWordsFile);
    }

    // Load stop words from a file
    private void loadStopWords(String stopWordsFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(stopWordsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim().toLowerCase());
            }
        }
    }

    // Process a document, remove stop words and punctuation, and return a list of terms
    public List<String> processText(String text) {
        text = text.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", ""); // Remove punctuation
        String[] words = text.split("\\s+");
        List<String> terms = new ArrayList<>();

        for (String word : words) {
            if (!stopWords.contains(word) && !word.isEmpty()) {
                terms.add(word);
            }
        }
        return terms;
    }

    // Read documents from a CSV file and return a map of document IDs to processed terms
    public Map<Integer, List<String>> readDocuments(String csvFile) throws IOException {
        Map<Integer, List<String>> documents = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int docId = 0;
            while ((line = reader.readLine()) != null) {
                List<String> processedTerms = processText(line);
                documents.put(docId++, processedTerms);
            }
        }
        return documents;
    }
}
