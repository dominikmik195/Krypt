package pmf.math.algoritmi;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AnalizaTeksta {

  public Map<String, Integer> pronadiSlova(String tekst) {
    Map<String, Integer> povratnaMapa = new HashMap<>();
    Arrays.stream(Abeceda.abeceda.split("")).forEach(slovo -> {
      povratnaMapa.put(slovo, (int) Arrays.stream(tekst.split(""))
          .filter(s -> s.equals(slovo)).count());
    });
    return sortirajMapu(povratnaMapa);
  }

  private Map<String, List<Integer>> pronadiMultigram(String tekst, int m) {
    Map<String, List<Integer>> povratnaMapa = new HashMap<>();
    for (int i = 0; i < tekst.length() - (m - 1); i++) {
      String multigram = tekst.substring(i, i + m);
      if (!povratnaMapa.containsKey(multigram)) {
        povratnaMapa.put(multigram, new LinkedList<>());
        for (int j = i; j < tekst.length() - (m - 1); j++) {
          if (tekst.substring(j, j + m).equals(multigram)) {
            povratnaMapa.get(multigram).add(j);
          }
        }
      }
    }
    return sortirajMapuSaListom(povratnaMapa);
  }

  public Map<String, List<Integer>> pronadiBigrame(String tekst) {
    return pronadiMultigram(tekst, 2);
  }

  public Map<String, List<Integer>> pronadiTrigrame(String tekst) {
    return pronadiMultigram(tekst, 3);
  }

  private Map<String, Integer> sortirajMapu(Map<String, Integer> mapa) {
    // pretvori u listu
    List<Map.Entry<String, Integer>> lista = new LinkedList<>(mapa.entrySet());
    // sortiraj
    lista.sort(Entry.comparingByValue(Comparator.reverseOrder()));
    // pretvori u mapu
    Map<String, Integer> sortiranaMapa = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> entry : lista) {
      sortiranaMapa.put(entry.getKey(), entry.getValue());
    }
    return sortiranaMapa;
  }

  private Map<String, List<Integer>> sortirajMapuSaListom(Map<String, List<Integer>> mapa) {
    // pretvori u listu
    List<Map.Entry<String, List<Integer>>> lista = new LinkedList<>(mapa.entrySet());
    // sortiraj
    lista.sort(Comparator.comparingInt(o -> o.getValue().size()));
    // pretvori u mapu
    Collections.reverse(lista);
    Map<String, List<Integer>> sortiranaMapa = new LinkedHashMap<>();
    for (Map.Entry<String, List<Integer>> entry : lista) {
      sortiranaMapa.put(entry.getKey(), entry.getValue());
    }
    return sortiranaMapa;
  }

}
