import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

public class lab3_var5 extends JFrame {
    public lab3_var5(){
        super("Lab3 var5");
        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(600, 600);
        setVisible(true);
        paint();
    }
    private void paint() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.BLUE);
                Cardioid cardioid = new Cardioid(290,170,160);
                SquareStroke sq = new SquareStroke(1);
                g2d.setStroke(sq);

                g2d.draw(cardioid);

            }
        };
        this.add(panel);
    }

    public static void main(String[] args) {
        lab3_var5 window = new lab3_var5();
    }
}
