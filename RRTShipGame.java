import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RRTShipGame extends JPanel implements ActionListener {

    static final int WIDTH = 800, HEIGHT = 600;

    private Node start;
    private Node goal;
    private ArrayList<Node> path;
    private ArrayList<Node> tree;
    private ArrayList<Rectangle> obstacles;
    private RRTPathFinder pathFinder;

    private javax.swing.Timer timer;
    private int shipIndex = 0;
    private double shipAngle = 0;

    public RRTShipGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(30, 144, 255));

        // Initialize nodes and obstacles
        start = new Node(50, 300, null);
        goal = new Node(750, 300, null);
        obstacles = new ArrayList<>();

        obstacles.add(new Rectangle(250, 150, 100, 300));
        obstacles.add(new Rectangle(450, 100, 120, 250));

        // Build RRT path
        pathFinder = new RRTPathFinder(start, goal, obstacles);
        pathFinder.buildRRT();

        path = pathFinder.getPath();
        tree = pathFinder.getTree();

        // Start animation timer
        timer = new javax.swing.Timer(50, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!path.isEmpty() && shipIndex < path.size() - 1) {
            Node current = path.get(shipIndex);
            Node next = path.get(shipIndex + 1);
            shipAngle = Math.atan2(next.y - current.y, next.x - current.x);
            shipIndex++;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Use the GameRenderer to draw everything
        GameRenderer.render(g2, tree, path, obstacles, start, goal, shipIndex, shipAngle);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RRT Ship Navigation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new RRTShipGame());
        frame.pack();
        frame.setVisible(true);
    }
}
