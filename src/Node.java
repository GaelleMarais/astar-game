import java.util.*;
import javafx.scene.shape.*;

public class Node{

    // Name of the node
    public String name="";
    // Distance from the first Node
    public int distance = Integer.MAX_VALUE;
    // Adjacent nodes to this node
    public HashMap<Node, Integer> neighbours = new HashMap<Node,Integer>();
    // Current shortest path from the first node to this node
    public LinkedList<Node> path = new LinkedList<Node>();
    // Coordinates of the node ( for A* )
    public int x,y;

    public Node(String name){
        this.name=name;
    }

    public Node(int x, int y){
        this.x=x;
        this.y=y;
    }

    public void addNeighbour(Node n, int dist){
        if(n!=null){
            neighbours.put(n,dist);
            // DEBUG
            // System.out.println(this.toString()+" voisin de "+n.toString());
        }

    }

    // Remplace the distance of a node if the new calculated distance is lower
    // and update the path
    public void minDist(Node start, int dist) {
        if (start.distance + dist < this.distance) {
            this.distance = start.distance + dist;
            this.path = new LinkedList<>(start.path);
            this.path.add(start);
            // DEBUG
            // System.out.println(" Adding to the path: "+start.toString());
            // System.out.println(this.path.toString());
        }
    }

    // Distance between the coordinates of this node and the coordinates of the given node
    public int predictedDistanceTo(Node node){
        return(Math.abs(this.x-node.x) + Math.abs(this.y-node.y));
    }

    public void printPath(Rectangle[][] board){
        for(Node node : path){
            // System.out.print(node.toString());
            board[node.y][node.x-1].setOpacity(0.3);
        }
        // System.out.println(this.toString());
    }

    @Override
    public String toString(){
        return ("("+this.x+","+this.y+")");
    }
}
