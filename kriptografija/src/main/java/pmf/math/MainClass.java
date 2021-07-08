package pmf.math;

import javax.swing.SwingUtilities;
import pmf.math.router.Router;

import java.io.File;

public class MainClass {
  static {
    File lib = new File("kriptografija/lib/TeorijaBrojeva.dll");
    System.load(lib.getAbsolutePath());
  }
  public static void main(String[] args) {
    Router router = new Router();
    SwingUtilities.invokeLater(router::Main);
  }
}
