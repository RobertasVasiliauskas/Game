import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    float WIDTH = 1200;
    float HEIGHT = 900;

    float dx = 0.05f, dy = 0.05f;

    public GamePanel() {
        setBackground(Color.WHITE);
        setLayout(null);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        setBounds(0, 0, (int)WIDTH, (int)HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(150, 300, 300, 150);
    }
}
