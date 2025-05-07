import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Finalvideo extends JPanel {

    private int frame = 0;
    private ArrayList<Star> stars = new ArrayList<>();
    private Ball ball = new Ball();

    public Finalvideo() {
        // Initialize stars
        for (int i = 0; i < 30; i++) {
            stars.add(new Star());
        }

        Timer timer = new Timer(100, e -> {
            frame++;
            repaint();
            saveFrame(); // Optional: Save frame as image
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScene((Graphics2D) g);
    }

    private void drawScene(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw Stars
        for (Star star : stars) {
            star.update(frame);
            star.draw(g2);
        }

        // Draw stick figures
        drawStickFigure(g2, 150, 300, frame);
        drawStickFigure(g2, 350, 300, frame);

        // Draw tossing ball
        ball.update(frame);
        ball.draw(g2);
    }

    private void drawStickFigure(Graphics2D g2, int x, int y, int frame) {
        g2.setColor(Color.WHITE);
        // Head
        g2.drawOval(x - 10, y - 60, 20, 20);
        // Body
        g2.drawLine(x, y - 40, x, y);
        // Arms with animation
        int armOffset = (int)(10 * Math.sin(frame * 0.1));
        g2.drawLine(x, y - 30, x - 20, y - 30 - armOffset);
        g2.drawLine(x, y - 30, x + 20, y - 30 + armOffset);
        // Legs
        g2.drawLine(x, y, x - 10, y + 30);
        g2.drawLine(x, y, x + 10, y + 30);
    }

    private void saveFrame() {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        paint(g2);
        g2.dispose();
        try {
            ImageIO.write(img, "png", new File(String.format("frame_%04d.png", frame)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Star class
    static class Star {
        double x = Math.random() * 500;
        double y = Math.random() * 300;
        double size = Math.random() * 2 + 1;
        double speed = Math.random() * 0.5 + 0.2;
        double angle = 0;

        public void update(int frame) {
            y += speed;
            angle += 0.01;
            size = 1 + 0.5 * Math.sin(frame * 0.1);
            if (y > 400) y = 0;
        }

        public void draw(Graphics2D g2) {
            AffineTransform old = g2.getTransform();
            g2.translate(x, y);
            g2.rotate(angle);
            g2.setColor(Color.WHITE);
            g2.fillOval((int)(-size / 2), (int)(-size / 2), (int)size, (int)size);
            g2.setTransform(old);
        }
    }

    // Ball class
    static class Ball {
        double x, y;
        boolean forward = true;

        public void update(int frame) {
            double t = (frame % 100) / 100.0;
            if (!forward) t = 1 - t;
            x = (1 - t) * 150 + t * 350;
            y = 300 - 50 * Math.sin(t * Math.PI); // arc
            if (frame % 100 == 99) forward = !forward;
        }

        public void draw(Graphics2D g2) {
            g2.setColor(Color.ORANGE);
            g2.fillOval((int)x - 5, (int)y - 5, 10, 10);
        }
    }

    // Main runner
    public static void main(String[] args) {
        JFrame frame = new JFrame("Live Drawing Video");
        Finalvideo panel = new Finalvideo();
        frame.add(panel);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
