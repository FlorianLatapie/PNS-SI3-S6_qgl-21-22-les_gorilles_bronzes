package simulator.display;


import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Reef;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Stream;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import simulator.SimulatorInfos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Simulator extends JFrame {

    private SimulatorInfos simulatorInfos;
    private Cockpit cockpit;

    private DisplayPanel panel;


    public Simulator(SimulatorInfos simulatorInfos, Cockpit cockpit) {
        setTitle("oui");
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.simulatorInfos = simulatorInfos;
        initSimulatorInfos();

        this.cockpit = cockpit;


        setLayout(new BorderLayout());
        panel = new DisplayPanel(simulatorInfos);
        add(panel, BorderLayout.CENTER);
        setSize(1800, 900);

        new Thread(() -> {
            try {
                while (true) {
                    panel.repaint();
                    Thread.sleep(1000);
                }
            } catch (final InterruptedException e) {

            }
        }).start();


    }

    private void initSimulatorInfos() {
        var pos = simulatorInfos.getStartingPositions();
        var ship = simulatorInfos.getShip();
        ship.setPosition(pos[0]);
        ship.setShape(new Rectangle(ship.getDeck().getWidth(), ship.getDeck().getLength(),ship.getPosition().getOrientation()));
    }

    private static class DisplayPanel extends JPanel {
        private double offsetX = 0, offsetY = 0, scale = 0.5;
        private IntPoint mouseOrigin;

        private SimulatorInfos simulatorInfos;
        public DisplayPanel(SimulatorInfos simulatorInfos) {
            super();
            this.simulatorInfos = simulatorInfos;

            addMouseWheelListener(e -> {
                var wheelRotation = e.getPreciseWheelRotation() < 0 ? 1.1 : (1 / 1.1);
                scale = scale * wheelRotation;
                repaint();
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
                        repaint();
                    }
                }
            });
        }

        @Override
        public void paintComponent(Graphics _g) {
            var g = (Graphics2D) _g;

            g.setColor(new Color(202, 219, 255));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setPaintMode();

            var visibleEntities = simulatorInfos.getSeaEntities();
            for (var entity : visibleEntities) {
                if (entity instanceof Reef) {
                    g.setColor(Color.GREEN);
                } else if (entity instanceof Stream) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.RED);
                }
                drawShapedThing(g, entity.getPosition(), entity.getShape(), true);
            }
            var ship = simulatorInfos.getShip();
            drawShapedThing(g, ship.getPosition(), ship.getShape(), true);
        }

        private void drawShapedThing(Graphics gp, Position p, Shape shape, boolean showArrow) {
            gp = (Graphics2D) gp.create();
            var point = getPoint(p);
            gp.translate(point.x, point.y);
            ((Graphics2D) gp).rotate(p.getOrientation(), 0, 0);
            var ap = new java.awt.Polygon();
            var shp = shape.toPolygon();
            for (var pt : shp.getVertices()) {
                ap.addPoint((int) (pt.getX() * scale), (int) (pt.getY() * scale));
            }
            gp.fillPolygon(ap);
            if (showArrow) {
                ((Graphics2D) gp).setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
                gp.setColor(Color.BLACK);
                gp.drawPolygon(new Polygon(new int[]{-10, -10, 20}, new int[]{-10, 10, 0}, 3));
            }
        }

        private static class IntPoint {
            int x, y;

            public IntPoint(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        private IntPoint getPoint(Position point) {
            return new IntPoint(
                    getWidth() / 2 + (int) ((point.getX() - offsetX) * scale),
                    getHeight() / 2 + (int) ((point.getY() - offsetY) * scale)
            );
        }
    }
}
