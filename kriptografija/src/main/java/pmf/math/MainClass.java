package pmf.math;

import java.net.URL;
import javax.swing.SwingUtilities;
import pmf.math.router.Router;

public class MainClass {
  static {
    URL url = MainClass.class.getClassLoader().getResource("TeorijaBrojeva.dll");
    assert url != null;
    System.load(url.getPath());
  }
  public static void main(String[] args) {
    Router router = new Router();
    SwingUtilities.invokeLater(router::Main);
  }
}
