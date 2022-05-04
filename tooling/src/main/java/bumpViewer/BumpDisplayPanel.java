package bumpViewer;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Reef;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Stream;
import simulator.objects.SimulatorInfos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class BumpDisplayPanel extends JPanel {
    private double offsetX = 0;
    private double offsetY = 0;
    private double scale = 0.5;
    int circleSize = 10;

    private IntPoint mouseOrigin;

    private SimulatorInfos simulatorInfos;
    private Bump bump;

    private final Color waterColor = new Color(173, 216, 230);

    public BumpDisplayPanel(SimulatorInfos simulatorInfos, Bump bump) {
        super();
        this.simulatorInfos = simulatorInfos;
        this.bump = bump;

        addMouseWheelListener(e -> {
            var wheelRotation = e.getPreciseWheelRotation() < 0 ? 1.1 : (1 / 1.1);
            scale = scale * wheelRotation;
        });


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    mouseOrigin = new IntPoint(e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    mouseOrigin = null;
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (mouseOrigin != null) {
                    offsetX -= (e.getX() - mouseOrigin.x) / scale;
                    offsetY -= (e.getY() - mouseOrigin.y) / scale;
                    mouseOrigin = new IntPoint(e.getX(), e.getY());
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics _g) {
        var g = (Graphics2D) _g;

        g.setColor(waterColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setPaintMode();


        // infos from simulator

        var visibleEntities = simulatorInfos.getSeaEntities();
        for (var entity : visibleEntities) {
            if (entity instanceof Reef) {
                g.setColor(new Color(0, 128, 0));
            } else if (entity instanceof Stream) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.RED);
            }
            drawShapedThing(g, entity.getPosition(), entity.getShape(), true, Color.BLACK);
        }

        var checkPoints = ((RegattaGoal) simulatorInfos.getGoal()).getCheckpoints();
        for (int i = 0; i < checkPoints.length; i++) {
            var checkPoint = checkPoints[i];
            g.setColor(new Color(255, 0, 0));
            drawShapedThing(g, checkPoint.getPosition(), checkPoint.getShape(), false, Color.BLACK);
            g.setColor(Color.BLACK);
            var p = getPoint(checkPoint.getPosition());
            g.drawString(i + "", p.x - 4, p.y + 4);
        }

        var ship = simulatorInfos.getShip();
        drawShapedThing(g, ship.getPosition(), ship.getShape(), true, Color.yellow);

        // display bump infos : for each point draw a line to the next point
        var bumpPoints = bump.getPositions();
        for (int i = 0; i < bumpPoints.size() - 1; i++) {
            var p1 = getPoint(bumpPoints.get(i));
            var p2 = getPoint(bumpPoints.get(i + 1));
            g.setColor(Color.RED);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    private void drawShapedThing(Graphics gp, Position p, fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Shape shape, boolean showArrow, Color color) {
        gp = (Graphics2D) gp.create();
        var point = getPoint(p);
        gp.translate(point.x, point.y);
        ((Graphics2D) gp).rotate(p.getOrientation(), 0, 0);
        var ap = new Polygon();
        var shp = shape.toPolygon();
        for (var pt : shp.getVertices()) {
            ap.addPoint((int) (pt.getX() * scale), (int) (pt.getY() * scale));
        }
        gp.fillPolygon(ap);
        if (showArrow) {
            ((Graphics2D) gp).setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            gp.setColor(color);
            gp.drawPolygon(new Polygon(new int[]{-10, -10, 20}, new int[]{-10, 10, 0}, 3));
        }
    }

    private static class IntPoint {
        int x;
        int y;

        public IntPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private IntPoint getPoint(Point point) {
        return new IntPoint(
                getWidth() / 2 + (int) ((point.getX() - offsetX) * scale),
                getHeight() / 2 + (int) ((point.getY() - offsetY) * scale)
        );
    }
}

