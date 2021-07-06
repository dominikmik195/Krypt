package pmf.math.pomagala;

public class Stoperica {

  private long pocetak = -1L;
  private long kraj = -1L;

  public void pokreni() {
    pocetak = System.nanoTime();
  }

  public void zaustavi() {
    kraj = System.nanoTime();
  }

  public int vrijeme() {
    if(pocetak == -1L || kraj == -1L) {
      return 0;
    }
    // mikrosekunde
    return (int) ((kraj - pocetak) /  1000);
  }

  public int vrijemeMili() {
    if(pocetak == -1L || kraj == -1L) {
      return 0;
    }
    // milisekunde
    return (int) ((kraj - pocetak) /  1000000);
  }

  public void resetiraj() {
    pocetak = -1L;
    kraj = -1L;
  }

}
