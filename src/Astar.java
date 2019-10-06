import java.util.*;
import javafx.scene.shape.*;

public class Astar{
    public ArrayList<Node> openList;
    public ArrayList<Node> closedList;

    public static void calculateShortestPath(Node start, Node goal, Rectangle[][] board){
        start.distance = 0;

        HashSet<Node> closedNodes = new HashSet<Node>();
        HashSet<Node> openNodes =new HashSet<Node>();

        openNodes.add(start);
        Node currentNode;

        while(!openNodes.isEmpty()){
            currentNode = getNextNode(openNodes);
            openNodes.remove(currentNode);

            // Get the iterator for the neighbours
            Iterator<Map.Entry<Node,Integer>> it = currentNode.neighbours.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Node,Integer> neighbour = (Map.Entry<Node,Integer>)it.next();
                Node n = neighbour.getKey();
                // Get the next node with the lower distance
                int predictedDist = currentNode.predictedDistanceTo(goal);
                Integer dist = neighbour.getValue() + predictedDist;

                if (!closedNodes.contains(n)) {
                    n.minDist(currentNode, dist);
                    openNodes.add(n);
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
