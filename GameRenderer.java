import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;

public class GameRenderer {
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    public static void render(Graphics2D g2, ArrayList<Node> tree, ArrayList<Node> path,
            ArrayList<Rectangle> obstacles, Node start, Node goal,
            int shipIndex, double shipAngle) {
        // Set rendering hints
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Sea background
        g2.setColor(new Color(30, 144, 255));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // Waves
        g2.setColor(new Color(20, 120, 220));
        g2.setStroke(new BasicStroke(1));
        for (int i = 0; i < WIDTH; i += 40) {
            g2.drawLine(i, 150, i + 20, 160);
            g2.drawLine(i + 20, 160, i + 40, 150);
            g2.drawLine(i, 350, i + 20, 360);
            g2.drawLine(i + 20, 360, i + 40, 350);
        }

        // Obstacles (islands)
        g2.setColor(new Color(150, 100, 50));
        for (Rectangle r : obstacles) {
            g2.fill(r);
            g2.setColor(new Color(120, 80, 40));
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(r.x, r.y, r.width, r.height);
            g2.setColor(new Color(150, 100, 50));
        }

        // RRT tree
        g2.setColor(new Color(200, 200, 200, 80));
        g2.setStroke(new BasicStroke(0.5f));
        for (Node n : tree)
            if (n.parent != null)
                g2.drawLine((int) n.x, (int) n.y,
                        (int) n.parent.x, (int) n.parent.y);

        // Path
        g2.setColor(new Color(255, 255, 100));
        g2.setStroke(new BasicStroke(2.5f));
        for (int i = 0; i < path.size() - 1; i++)
            g2.drawLine((int) path.get(i).x, (int) path.get(i).y,
                    (int) path.get(i + 1).x, (int) path.get(i + 1).y);

        // Ship
        if (!path.isEmpty()) {
            Node ship = path.get(shipIndex);
            ShipRenderer.drawRetroShip(g2, ship.x, ship.y, shipAngle);
        }

        // Start marker
        g2.setColor(new Color(150, 255, 150));
        g2.fillOval((int) start.x - 7, (int) start.y - 7, 14, 14);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval((int) start.x - 7, (int) start.y - 7, 14, 14);

        // Goal marker
        g2.setColor(Color.GREEN);
        g2.fillOval((int) goal.x - 8, (int) goal.y - 8, 16, 16);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval((int) goal.x - 8, (int) goal.y - 8, 16, 16);

        // Status text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        if (path.isEmpty()) {
            g2.drawString("Calculating path...", 10, 20);
        } else {
            g2.drawString("Path found with " + path.size() + " waypoints", 10, 40);
        }
    }
}
