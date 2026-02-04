import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RRTPathFinder {
    static final double STEP = 15;
    static final int MAX_ITER = 3000;

    private ArrayList<Node> tree;
    private ArrayList<Node> path;
    private ArrayList<Rectangle> obstacles;
    private Random rand;
    private Node goal;

    public RRTPathFinder(Node start, Node goal, ArrayList<Rectangle> obstacles) {
        this.tree = new ArrayList<>();
        this.path = new ArrayList<>();
        this.obstacles = obstacles;
        this.rand = new Random();
        this.goal = goal;
        tree.add(start);
    }

    public void buildRRT() {
        for (int i = 0; i < MAX_ITER; i++) {
            Node randNode = randomNode();
            Node nearest = nearestNode(randNode);
            Node newNode = steer(nearest, randNode);

            if (!collision(nearest, newNode)) {
                tree.add(newNode);
                if (dist(newNode, goal) < STEP) {
                    goal.parent = newNode;
                    extractPath();
                    break;
                }
            }
        }
    }

    private Node randomNode() {
        return new Node(rand.nextInt(800), rand.nextInt(600), null);
    }

    private Node nearestNode(Node r) {
        Node best = null;
        double min = Double.MAX_VALUE;
        for (Node n : tree) {
            double d = dist(n, r);
            if (d < min) {
                min = d;
                best = n;
            }
        }
        return best;
    }

    private Node steer(Node a, Node b) {
        double theta = Math.atan2(b.y - a.y, b.x - a.x);
        return new Node(a.x + STEP * Math.cos(theta),
                a.y + STEP * Math.sin(theta), a);
    }

    private boolean collision(Node a, Node b) {
        Line2D.Double line = new Line2D.Double(a.x, a.y, b.x, b.y);
        for (Rectangle r : obstacles)
            if (r.intersectsLine(line))
                return true;
        return false;
    }

    private double dist(Node a, Node b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    private void extractPath() {
        Node cur = goal;
        while (cur != null) {
            path.add(cur);
            cur = cur.parent;
        }
        Collections.reverse(path);
    }

    public ArrayList<Node> getPath() {
        return path;
    }

    public ArrayList<Node> getTree() {
        return tree;
    }

    public boolean isPathFound() {
        return !path.isEmpty();
    }
}
