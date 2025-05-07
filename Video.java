import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import Assignment06.Color;
import Assignment06.ColorAbstract;
import Assignment06.Vector;
import Assignment06.VectorAbstract;
import Assignment08.Shader;
import Assignment08.ShaderAbstract.FILLSTYLE;
import Common.ReadWriteImage;
import Matrix.Matrix;
import Matrix.MatrixAbstract;
import Assignment10.*;
import Assignment08.Triangle;
import Assignment08.TriangleAbstract;
import Assignment07.AffineTransformation;

public class Video {

    public static void main(String[] args) {
        BufferedImage texture = null;
try {
    texture = ImageIO.read(new File("background.jpg")); // or .png
} catch (IOException e) {
    System.err.println("Could not load background texture.");
    e.printStackTrace();
}
        int width = 800;
        int height = 800;
        int[][][] framebuffer = new int[3][height][width];
        new File("frames").mkdirs();

        int quarterStartY = (int) (height * 0.75); // Start of bottom quarter
for (int y = quarterStartY; y < height; y++) {
    for (int x = 0; x < width; x++) {
        int tx = x * texture.getWidth() / width;
        int ty = (y - quarterStartY) * texture.getHeight() / (height - quarterStartY); // Scale texture to fill just the quarter
        int rgb = texture.getRGB(tx, ty);

        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        framebuffer[0][y][x] = r;
        framebuffer[1][y][x] = g;
        framebuffer[2][y][x] = b;
    }
}
    
        ScanConvertLine drawer = new ScanConvertLine();
    
        List<Vector> circle1 = generateCircle(150, 500, 20, new Color(1, 1, 0)); // Yellow
        List<Vector> circle2 = generateCircle(650, 500, 20, new Color(0, 1, 1)); // Cyan
    
        int frame = 0;
        int circleframe = 0;
    
        for (Vector p : circle1) {
            drawPoint(p, framebuffer);
            if (circleframe % 2 == 0) {
                saveFrame(framebuffer, frame++);
            }
            circleframe++;
        }
    
        for (Vector p : circle2) {
            drawPoint(p, framebuffer);
            if (circleframe % 2 == 0) {
                saveFrame(framebuffer, frame++);
            }
            circleframe++;
        }
    
        ArrayList<VectorAbstract> linePoints = new ArrayList<>();
        buildStickFigureLines(linePoints, drawer, new Color(1, 1, 0), 150, 600); // Yellow left
        buildStickFigureLines(linePoints, drawer, new Color(0, 1, 1), 650, 600); // Cyan right
    
        for (int i = 0; i < linePoints.size(); i++) {
            VectorAbstract p = linePoints.get(i);
            drawPoint(p, framebuffer);
            if (i % 2 == 0) {
                saveFrame(framebuffer, frame++);
            }
        }
    
        List<Vector> starCenters = List.of(
            new Vector(100, 100, 0, new Color(1, 1, 1)),
            new Vector(200, 150, 0, new Color(1, 1, 1)),
            new Vector(300, 80, 0, new Color(1, 1, 1)),
            new Vector(400, 120, 0, new Color(1, 1, 1)),
            new Vector(500, 90, 0, new Color(1, 1, 1)),
            new Vector(600, 180, 0, new Color(1, 1, 1)),
            new Vector(700, 100, 0, new Color(1, 1, 1)),
            new Vector(150, 250, 0, new Color(1, 1, 1)),
            new Vector(250, 300, 0, new Color(1, 1, 1)),
            new Vector(350, 250, 0, new Color(1, 1, 1)),
            new Vector(450, 350, 0, new Color(1, 1, 1)),
            new Vector(550, 300, 0, new Color(1, 1, 1))
        );
    
        int[] frameCounter = new int[] { frame };
    
        List<StarState> starStates = new ArrayList<>();
        for (Vector center : starCenters) {
            drawStarIncrementally(center, 10, framebuffer, drawer, frameCounter);
        }
        
        // Hold the completed static image for 10 frames
        for (int i = 0; i < 10; i++) {
            saveFrame(framebuffer, frameCounter[0]);
            frameCounter[0]++;
        }
        
        // Initialize animation state only after full drawing is complete
        starStates = new ArrayList<>();
        for (Vector center : starCenters) {
            starStates.add(new StarState(center));
        }

        int[][][] background = new int[3][framebuffer[0].length][framebuffer[0][0].length];
for (int y = 0; y < framebuffer[0].length; y++) {
    for (int x = 0; x < framebuffer[0][0].length; x++) {
        background[0][y][x] = framebuffer[0][y][x];
        background[1][y][x] = framebuffer[1][y][x];
        background[2][y][x] = framebuffer[2][y][x];
    }
}
// Draw ball near left stick figure (150, 520)
List<Vector> ballPixels = generateFilledCircle(160, 550, 6, new Color(1, 0, 0)); // Red ball
for (Vector p : ballPixels) {
    drawPoint(p, framebuffer);
}  
        
        // Now animate rotating stars — using fresh frame buffer each frame
        AffineTransformation transform = new AffineTransformation();
        int starAnimationFrames = 60;
        double rotationIncrement = Math.toRadians(5);

        ShootingStar shootingStar = new ShootingStar(new Vector(0, 0, 0, new Color(1, 1, 1)), Math.toRadians(30), 5);
        Ball ball = new Ball(
    new Vector(160, 550, 0, new Color(1, 0, 0)), // Left figure
    new Vector(630, 550, 0, new Color(1, 0, 0)), // Right figure
    0.02, // Speed
    80    // Arc height
);
        /*for (int i = 0; i < starAnimationFrames; i++) {
            int[][][] newFrame = new int[3][framebuffer[0].length][framebuffer[0][0].length];
        
            // Copy the fully drawn framebuffer into newFrame as a background
            for (int y = 0; y < framebuffer[0].length; y++) {
                for (int x = 0; x < framebuffer[0][0].length; x++) {
                    newFrame[0][y][x] = framebuffer[0][y][x];
                    newFrame[1][y][x] = framebuffer[1][y][x];
                    newFrame[2][y][x] = framebuffer[2][y][x];
                }
            }
        
            for (StarState starState : starStates) {
                Vector center = starState.center;
                starState.incrementAngle(rotationIncrement);
        
                List<Vector> star = generateStarPoints(center, 10);
                double[][] coords = new double[star.size()][4];
                for (int j = 0; j < star.size(); j++) {
                    coords[j][0] = star.get(j).getX();
                    coords[j][1] = star.get(j).getY();
                    coords[j][2] = 0;
                    coords[j][3] = 1;
                }
        
                MatrixAbstract starMatrix = new Matrix(coords);
                starMatrix = transform.rotateZ(starState.angle, center, starMatrix);
        
                for (int j = 0; j < 5; j++) {
                    double[][] starData = starMatrix.getMatrix();
                    VectorAbstract start = new Vector(starData[j][0], starData[j][1], starData[j][2], center.getColor());
                    VectorAbstract end = new Vector(starData[(j + 2) % 5][0], starData[(j + 2) % 5][1], starData[(j + 2) % 5][2], center.getColor());
        
                    linePoints = new ArrayList<>();
                    drawer.bresenham((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY(), center.getColor(), center.getColor(), linePoints);
                    for (VectorAbstract p : linePoints) {
                        drawPoint(p, newFrame);
                    }
                }
            }
        
            saveFrame(newFrame, frameCounter[0]++);
        }*/
        for (int i = 0; i < starAnimationFrames; i++) {
            int[][][] newFrame = new int[3][framebuffer[0].length][framebuffer[0][0].length];
        
            // Copy only the background (no stars)
            /*for (int y = 0; y < background[0].length; y++) {
                for (int x = 0; x < background[0][0].length; x++) {
                    newFrame[0][y][x] = background[0][y][x];
                    newFrame[1][y][x] = background[1][y][x];
                    newFrame[2][y][x] = background[2][y][x];
                }
            }*/
            int halfHeight = height / 2;
for (int y = halfHeight; y < height; y++) {
    for (int x = 0; x < background[0][0].length; x++) {
        newFrame[0][y][x] = background[0][y][x];
        newFrame[1][y][x] = background[1][y][x];
        newFrame[2][y][x] = background[2][y][x];
    }
}
        
            // Draw only rotating stars on top of background
            for (StarState starState : starStates) {
                Vector center = starState.center;
                starState.incrementAngle(rotationIncrement);
        
                List<Vector> star = generateStarPoints(center, 10);
                double[][] coords = new double[star.size()][4];
                for (int j = 0; j < star.size(); j++) {
                    coords[j][0] = star.get(j).getX();
                    coords[j][1] = star.get(j).getY();
                    coords[j][2] = 0;
                    coords[j][3] = 1;
                }
        
                MatrixAbstract starMatrix = new Matrix(coords);
                starMatrix = transform.rotateZ(starState.angle, center, starMatrix);
        
                for (int j = 0; j < 5; j++) {
                    double[][] starData = starMatrix.getMatrix();
                    VectorAbstract start = new Vector(starData[j][0], starData[j][1], starData[j][2], center.getColor());
                    VectorAbstract end = new Vector(starData[(j + 2) % 5][0], starData[(j + 2) % 5][1], starData[(j + 2) % 5][2], center.getColor());
        
                    linePoints = new ArrayList<>();
                    drawer.bresenham((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY(), center.getColor(), center.getColor(), linePoints);
                    for (VectorAbstract p : linePoints) {
                        drawPoint(p, newFrame);
                    }
                }
                // Update and draw the shooting star
                    shootingStar.update();
                List<Vector> shootingStarPoints = generateStarPoints(shootingStar.position, (int) shootingStar.size);

                for (int j = 0; j < 5; j++) {
                    Vector start = shootingStarPoints.get(j);
                    Vector end = shootingStarPoints.get((j + 2) % 5);
                    linePoints = new ArrayList<>();
                    drawer.bresenham((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY(), start.getColor(), start.getColor(), linePoints);
                for (VectorAbstract p : linePoints) {
                    drawPoint(p, newFrame);
                    }
                }
            }


            // Update and draw the ball in flight
            ball.update();
Vector ballPos = ball.getPosition();
ballPixels = generateFilledCircle((int) ballPos.getX(), (int) ballPos.getY(), 6, ballPos.getColor());
for (Vector p : ballPixels) {
    drawPoint(p, newFrame);
}
            saveFrame(newFrame, frameCounter[0]++);
        }
    }

private static void buildStickFigureLines(ArrayList<VectorAbstract> linePoints, ScanConvertLine drawer, Color color, int cx, int cy) {
    drawer.bresenham(cx, cy - 80, cx, cy - 20, color, color, linePoints); // Body
    drawer.bresenham(cx, cy - 70, cx - 20, cy - 50, color, color, linePoints); // Left arm
    drawer.bresenham(cx, cy - 70, cx + 20, cy - 50, color, color, linePoints); // Right arm
    drawer.bresenham(cx, cy - 20, cx - 15, cy + 20, color, color, linePoints); // Left leg
    drawer.bresenham(cx, cy - 20, cx + 15, cy + 20, color, color, linePoints); // Right leg
}

static List<Vector> generateCircle(int cx, int cy, int r, Color color) {
    List<Vector> points = new ArrayList<>();
    for (int i = 0; i <= 360; i++) {
        double t = Math.toRadians(i);
        int x = (int) Math.round(cx + r * Math.cos(t));
        int y = (int) Math.round(cy + r * Math.sin(t));
        points.add(new Vector(x, y, 0, color));
    }
    return points;
}

static List<Vector> generateFilledCircle(int cx, int cy, int r, Color color) {
    List<Vector> points = new ArrayList<>();
    for (int y = -r; y <= r; y++) {
        for (int x = -r; x <= r; x++) {
            if (x * x + y * y <= r * r) {
                points.add(new Vector(cx + x, cy + y, 0, color));
            }
        }
    }
    return points;
}

static void collectStickFigureLines(ScanConvertLine drawer, List<VectorAbstract> out, int cx, int cy, Color color) {
    int[][] lines = {
        {cx, cy - 80, cx, cy - 20},     // Body
        {cx, cy - 70, cx - 20, cy - 50}, // Left arm
        {cx, cy - 70, cx + 20, cy - 50}, // Right arm
        {cx, cy - 20, cx - 15, cy + 20}, // Left leg
        {cx, cy - 20, cx + 15, cy + 20}  // Right leg
    };

    for (int[] l : lines) {
        ArrayList<VectorAbstract> linePoints = new ArrayList<>();
        drawer.bresenham(l[0], l[1], l[2], l[3], color, color, linePoints);
        out.addAll(linePoints);
    }
}

static void drawPoint(VectorAbstract p, int[][][] fb) {
    int x = (int) p.getX();
    int y = (int) p.getY();
    ColorAbstract color = p.getColor();

    if (color == null || x < 0 || x >= fb[0][0].length || y < 0 || y >= fb[0].length) return;

    int[] rgb = color.scale(255);
    fb[0][y][x] = rgb[0];
    fb[1][y][x] = rgb[1];
    fb[2][y][x] = rgb[2];
}

private static void saveFrame(int[][][] framebuffer, int frameIndex) {
    try {
        String filename = String.format("frames/frame_%03d.png", frameIndex);
        ReadWriteImage.writeImage(framebuffer, filename);
    } catch (IOException e) {
        System.out.println("Failed to save frame " + frameIndex);
    }
}

// Method to draw a star incrementally
private static StarState drawStarIncrementally(Vector center, int radius, int[][][] framebuffer, ScanConvertLine drawer, int[] frameCounter) {
    List<Vector> starPoints = generateStarPoints(center, radius);

    for (int i = 0; i < starPoints.size(); i++) {
        Vector start = starPoints.get(i);
        Vector end = starPoints.get((i + 2) % starPoints.size());

        ArrayList<VectorAbstract> linePoints = new ArrayList<>();
        drawer.bresenham((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY(), start.getColor(), start.getColor(), linePoints);

        for (VectorAbstract p : linePoints) {
            drawPoint(p, framebuffer);
            saveFrame(framebuffer, frameCounter[0]++);
        }
    }

    return new StarState(center);
}

// Generate the points of a five-pointed star
private static List<Vector> generateStarPoints(Vector center, int radius) {
    List<Vector> points = new ArrayList<>();
    double angleStep = 72; // 360° / 5 points
    for (int i = 0; i < 5; i++) {
        double angle = Math.toRadians(i * angleStep);
        int x = (int) Math.round(center.getX() + radius * Math.cos(angle));
        int y = (int) Math.round(center.getY() + radius * Math.sin(angle));
        points.add(new Vector(x, y, 0, center.getColor()));
    }
    return points;
}
static class StarState {
    Vector center;
    double angle;

    public StarState(Vector center) {
        this.center = center;
        this.angle = 0;
    }

    public void incrementAngle(double delta) {
        this.angle += delta;
    }
}
static class ShootingStar {
    Vector position;
    double angle;
    double size;

    public ShootingStar(Vector startPosition, double angle, double initialSize) {
        this.position = startPosition;
        this.angle = angle; // radians
        this.size = initialSize;
    }

    public void update() {
        // Move in the direction of the angle
        double dx = Math.cos(angle) * 5;
        double dy = Math.sin(angle) * 5;
        position = new Vector(position.getX() + dx, position.getY() + dy, 0, position.getColor());
        size += 0.5; // Gradually increase size
    }
}
static class Ball {
    Vector start;
    Vector end;
    double t;            // interpolation value (0.0 to 1.0)
    double speed;        // how fast to interpolate
    boolean forward;     // direction of movement
    double arcHeight;    // max arc height

    public Ball(Vector start, Vector end, double speed, double arcHeight) {
        this.start = start;
        this.end = end;
        this.speed = speed;
        this.arcHeight = arcHeight;
        this.t = 0.0;
        this.forward = true;
    }

    public Vector getPosition() {
        // Linearly interpolate X and Y
        double x = (1 - t) * start.getX() + t * end.getX();
        double y = (1 - t) * start.getY() + t * end.getY();

        // Add arc offset (simple parabola)
        double arcOffset = -4 * arcHeight * t * (1 - t); // peak at t=0.5

        return new Vector(x, y + arcOffset, 0, new Color(1, 0, 0));
    }

    public void update() {
        if (forward) {
            t += speed;
            if (t >= 1.0) {
                t = 1.0;
                forward = false;
            }
        } else {
            t -= speed;
            if (t <= 0.0) {
                t = 0.0;
                forward = true;
            }
        }
    }
}
}