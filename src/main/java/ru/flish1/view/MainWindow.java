package ru.flish1.view;

import ru.flish1.figure.Circle;
import ru.flish1.model.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainWindow extends JFrame {

    private final int HEIGHT = 800;
    private final int WIDTH = 800;
    private final String title = "VerletPhysic";
    private final Vector gravity = new Vector(0, 1);
    private final List<Circle> circles;
    private int t = 16;

    public MainWindow() {
        super("VerletPhysic");
        circles = new ArrayList<>();
        setSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(new MouseListener() {
            Random random = new Random();

            @Override
            public void mouseClicked(MouseEvent e) {
                circles.add(new Circle(e.getXOnScreen(), e.getYOnScreen(), new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()), 20));

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bf.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.addRenderingHints(Map.of(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED, RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE));
        super.paint(g2d);


        applyConstraints(g2d);
        startMagic(g2d);

        g2d.dispose();
        g.drawImage(bf, 0, 0, null);
        repaint(t);

    }

    private void applyConstraints(Graphics2D g2d) {
        double radius = 250;
        Vector position = new Vector(260, 300);
        g2d.setColor(Color.BLACK);
        g2d.draw(new Circle(position.getVector()[0] - radius, position.getVector()[1] - radius, Color.black, radius));
        for (Circle circle : circles) {
            Vector toObj = circle.getPosition().minus(position);
            double dist = toObj.length();
            System.out.println(dist);
            if (dist > radius - circle.getRadius()) {
                Vector n = toObj.division(dist);
                circle.setPosition(position.append(n.multiply((dist - circle.getRadius())*-1)));
            }
        }
    }

    private void startMagic(Graphics2D g2d) {
        applyAccelerate();
        updatePosition();
        paintObj(g2d);
    }

    private void paintObj(Graphics2D g2d) {
        for (Circle circle : circles) {
            circle.paintCircle(g2d);
        }
    }

    private void updatePosition() {
        for (Circle circle : circles) {
            circle.updatePosition(t / 10.0);
        }
    }

    private void applyAccelerate() {
        for (Circle circle : circles) {
            circle.accelerate(gravity);
        }
    }


}
