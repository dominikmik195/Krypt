package pmf.math.kalkulatori;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class UvodnaStranica {

  public JPanel glavniPanel;

  public UvodnaStranica() {
    glavniPanel = new JBackgroundPanel();
  }
}

class JBackgroundPanel extends JPanel {
  private BufferedImage img;

  public JBackgroundPanel() {
    try {
      img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/uvodna-slika.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int height = getHeight();
    int width = (height * 3) / 2;
    g.drawImage(img, (getWidth() - width) / 2, 0, width, height, this);
  }
}
