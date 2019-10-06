
import java.util.*;

public class Dijkstra{

    public static void calculateShortestPath(Node start){
        start.distance = 0;

        HashSet<Node> visitedNodes = new HashSet<Node>();
        HashSet<Node> unvisitedNodes =new HashSet<Node>();

        unvisitedNodes.add(start);
        Node currentNode;

        while(!unvisitedNodes.isEmpty()){
            currentNode = getNextNode(unvisitedNodes);
            unvisitedNodes.remove(currentNode);

            // Get the iterator for the neighbours
            Iterator<Map.Entry<Node,Integer>> it = currentNode.neighbours.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Node,Integer> neighbour = (Map.Entry<Node,Integer>)it.next();
                Node n = neighbour.getKey();
                Integer dist = neighbour.getValue();

                if (!visitedNodes.contains(n)) {
                    n.minDist(currentNode, dist);
                    unvisitedNodes.add(n);
                }
                it.remove();
            }
        }
    }

    // Return the node with the lowest distance to the start
    public static Node getNextNode(HashSet<Node> nodes){
        Node result = null;
        int lowest = Integer.MAX_VALUE; // infinite distance
        for (Node node: nodes) {
            if (node.distance < lowest) {
                lowest = node.distance;
                result = node;
            }
        }
        return result;
    }
}
