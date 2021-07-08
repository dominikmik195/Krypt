package pmf.math;

import java.net.URL;
import java.util.Objects;
import javax.swing.SwingUtilities;
import pmf.math.router.Router;

public class MainClass {
  static {
    URL url = Objects.requireNonNull(
        MainClass.class.getClassLoader().getResource("TeorijaBrojeva.dll"));
    System.load(url.getPath());
  }
  public static void main(String[] args) {
    Router router = new Router();
    SwingUtilities.invokeLater(router::Main);
  }
}
