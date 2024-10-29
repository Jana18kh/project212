import java.util.*;

public class RankedQueryProcessor {
    private Indexer indexer;

    // Constructor to initialize with an indexer
    public RankedQueryProcessor(Indexer indexer) {
        this.indexer = indexer;
    }

    // Process a ranked query and return documents sorted by relevance
    public List<Map.Entry<Integer, Integer>> processQuery(String query) {
        String[] terms = query.toLowerCase().split("\\s+");
        Map<Integer, Integer> docScores = new HashMap<>();

        for (String term : terms) {
            List<Integer> docIds = indexer.getDocuments(term);
            for (Integer docId : docIds) {
                docScores.put(docId, docScores.getOrDefault(docId, 0) + 1);
            }
        }

        // Sort documents by score in descending order
        List<Map.Entry<Integer, Integer>> sortedDocs = new ArrayList<>(docScores.entrySet());
        sortedDocs.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return sortedDocs;
    }
}