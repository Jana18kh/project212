import java.util.*;

public class Indexer {
    private Map<Integer, List<String>> index;  // Document ID to terms
    private Map<String, List<Integer>> invertedIndex; // Term to document IDs

    public Indexer() {
        index = new HashMap<>();
        invertedIndex = new HashMap<>();
    }

    // Build the index and inverted index
    public void buildIndex(Map<Integer, List<String>> documents) {
        for (Map.Entry<Integer, List<String>> entry : documents.entrySet()) {
            int docId = entry.getKey();
            List<String> terms = entry.getValue();
            index.put(docId, terms);

            for (String term : terms) {
                invertedIndex.computeIfAbsent(term, k -> new ArrayList<>()).add(docId);
            }
        }
    }

    // Get the list of document IDs containing a term
    public List<Integer> getDocuments(String term) {
        return invertedIndex.getOrDefault(term, Collections.emptyList());
    }

    // Print the inverted index for debugging
    public void printInvertedIndex() {
        for (Map.Entry<String, List<Integer>> entry : invertedIndex.entrySet()) {
            System.out.println("Term: " + entry.getKey() + " -> Documents: " + entry.getValue());
        }
    }
}