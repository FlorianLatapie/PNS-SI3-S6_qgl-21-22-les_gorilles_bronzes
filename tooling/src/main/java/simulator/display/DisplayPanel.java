package simulator.display;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Reef;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Stream;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding.Node;
import simulator.objects.SimulatorInfos;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.Set;

public class DisplayPanel extends JPanel {
    private double offsetX = 0;
    private double offsetY = 0;
    private double scale = 0.5;
    int circleSize = 10;

    private IntPoint mouseOrigin;

    private final Color waterColor = new Color(173, 216, 230);

    private SimulatorInfos simulatorInfos;
    private Cockpit cockpit;

    private Set<VisibleEntity> visibleEntitiesCache;
    private List<Point> path;

    public DisplayPanel(SimulatorInfos simulatorInfos, Cockpit cockpit) {
        super();
        this.cockpit = cockpit;
        this.simulatorInfos = simulatorInfos;

        this.path = cockpit.getGlobalEngine().getNavigationEngine().getPath();
        this.visibleEntitiesCache = cockpit.getGlobalEngine().getNavigationEngine().getVisibleEntitiesCache();

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


        // infos from cockpit
        this.path = cockpit.getGlobalEngine().getNavigationEngine().getPath();
        if (path != null) {
            g.setColor(Color.RED);
            for (var i = 0; i < path.size() - 1; i++) {
                var from = getPoint(path.get(i));
                var to = getPoint(path.get(i + 1));
                g.drawLine(from.x, from.y, to.x, to.y);
            }
        }

        this.visibleEntitiesCache = cockpit.getGlobalEngine().getNavigationEngine().getVisibleEntitiesCache();
        if (visibleEntitiesCache != null) {
            for (var entity : visibleEntitiesCache) {
                if (entity instanceof Reef) {
                    g.setColor(Color.GREEN);
                } else if (entity instanceof Stream) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.RED);
                }
                drawShapedThing(g, entity.getPosition(), entity.getShape(), true, Color.BLACK);
            }
        }




        // infos from simulator

        var visibleEntities = simulatorInfos.getSeaEntities();
        for (var entity : visibleEntities) {
            if (entity instanceof Reef) {
                g.setColor(new Color(0, 128, 0));
            } else if (entity instanceof Stream) {
                g.setColor(new Color(0, 0, 255));
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

