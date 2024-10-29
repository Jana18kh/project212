import java.util.*;

public class BooleanQueryProcessor {
    private Indexer indexer;

    // Constructor to initialize with an indexer
    public BooleanQueryProcessor(Indexer indexer) {
        this.indexer = indexer;
    }

    // Process a Boolean query and return matching document IDs
    public Set<Integer> processQuery(String query) {
        // Split query into terms and operators
        String[] tokens = query.split("\\s+");
        Stack<Set<Integer>> resultStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        for (String token : tokens) {
            if (token.equalsIgnoreCase("AND") || token.equalsIgnoreCase("OR")) {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(token)) {
                    Set<Integer> right = resultStack.pop();
                    Set<Integer> left = resultStack.pop();
                    String op = operatorStack.pop();
                    resultStack.push(applyOperator(op, left, right));
                }
                operatorStack.push(token);
            } else {
                // Retrieve documents for the term and push to the result stack
                resultStack.push(new HashSet<>(indexer.getDocuments(token.toLowerCase())));
            }
        }

        // Process remaining operators in the stack
        while (!operatorStack.isEmpty()) {
            Set<Integer> right = resultStack.pop();
            Set<Integer> left = resultStack.pop();
            String op = operatorStack.pop();
            resultStack.push(applyOperator(op, left, right));
        }

        return resultStack.isEmpty() ? new HashSet<>() : resultStack.pop();
    }

    // Define operator precedence
    private int precedence(String op) {
        return op.equalsIgnoreCase("AND") ? 2 : 1;
    }

    // Apply the given operator to two sets of document IDs
    private Set<Integer> applyOperator(String operator, Set<Integer> left, Set<Integer> right) {
        Set<Integer> result = new HashSet<>(left);
        if (operator.equalsIgnoreCase("AND")) {
            result.retainAll(right);  // Intersection for AND
        } else if (operator.equalsIgnoreCase("OR")) {
            result.addAll(right);  // Union for OR
        }
        return result;
    }
}