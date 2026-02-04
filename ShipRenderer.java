import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class ShipRenderer {

    public static void drawRetroShip(Graphics2D g2, double x, double y, double angle) {
        AffineTransform oldTransform = g2.getTransform();

        g2.translate(x, y);
        g2.rotate(angle);

        // Hull
        g2.setColor(new Color(200, 50, 50));
        int[] hullX = { 0, -8, -6, 6, 8 };
        int[] hullY = { -12, -2, 8, 8, -2 };
        g2.fillPolygon(hullX, hullY, 5);
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(Color.BLACK);
        g2.drawPolygon(hullX, hullY, 5);

        // Cabin
        g2.setColor(new Color(255, 200, 100));
        int[] cabinX = { -4, 0, 4, 2, -2 };
        int[] cabinY = { -8, -14, -8, -2, -2 };
        g2.fillPolygon(cabinX, cabinY, 5);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(cabinX, cabinY, 5);

        // Window
        g2.setColor(new Color(100, 150, 200));
        g2.fillRect(-2, -8, 4, 3);
        g2.setColor(Color.BLACK);
        g2.drawRect(-2, -8, 4, 3);

        // Flag
        g2.setColor(new Color(255, 100, 100));
        int[] flagX = { 0, 6, 6, 0 };
        int[] flagY = { -14, -12, -16, -18 };
        g2.fillPolygon(flagX, flagY, 4);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(flagX, flagY, 4);

        // Anchor
        g2.setColor(new Color(150, 150, 150));
        g2.fillOval(-3, 5, 6, 4);
        g2.setColor(Color.BLACK);
        g2.drawOval(-3, 5, 6, 4);

        // Wake
        g2.setColor(new Color(255, 255, 255, 100));
        g2.drawLine(0, 8, 0, 12);
        g2.drawLine(-2, 10, -2, 14);
        g2.drawLine(2, 10, 2, 14);

        g2.setTransform(oldTransform);
    }
}
