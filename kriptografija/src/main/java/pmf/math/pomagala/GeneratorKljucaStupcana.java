package pmf.math.pomagala;

import java.util.Random;
import java.util.Vector;

public class GeneratorKljucaStupcana {
  public static Vector<Integer> generiraj(int duljina) {
    Vector<Integer> rjesenje = new Vector<>();
    Vector<Integer> tmp = new Vector<>();
    for (int i = 0; i < duljina; i++) {
      tmp.add(i + 1);
    }
    Random r = new Random();
    while(tmp.size() > 0) {
      int pozicija = r.nextInt(tmp.size());
      int broj = tmp.get(pozicija);
      rjesenje.add(broj);
      tmp.removeElementAt(pozicija);
    }
    return rjesenje;
  }
}
