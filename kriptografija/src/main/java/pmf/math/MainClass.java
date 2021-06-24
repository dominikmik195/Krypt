package pmf.math;

import javax.swing.SwingUtilities;
import pmf.math.router.Router;

public class MainClass {

  public static void main(String[] args) {
    Router router = new Router();
    SwingUtilities.invokeLater(router::Main);
  }
}
