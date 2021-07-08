#include "main.h"
#include <math.h>
#include <boost/multiprecision/cpp_int.hpp>
#include <vector>
using namespace boost::multiprecision;

JNIEXPORT jboolean JNICALL Java_pmf_math_algoritmi_TeorijaBrojeva_prost
  (JNIEnv * env, jclass c, jlong broj) {
      int i;
      if(broj == 1) return false;
      for(i = 2; i <= sqrt(broj); i++) {
        if(broj%i == 0) return false;
      }
      return true;
  }

JNIEXPORT jlong JNICALL Java_pmf_math_algoritmi_TeorijaBrojeva_modularnoPotenciranje
  (JNIEnv * env, jclass c, jlong baza, jlong exp, jlong mod) {
    namespace mp = boost::multiprecision;
    mp::cpp_int val_baza = baza;
    mp::cpp_int val_exp = exp;
    mp::cpp_int val_mod = mod;
    std::vector<mp::cpp_int> ostaci;
    std::vector<int> potencije;
    mp::cpp_int x = val_baza % val_mod;
    ostaci.push_back(x);
    potencije.push_back(1);
    int potencija = 2, i;
    while(potencija <= val_exp) {
        x = mp::pow(x, 2) % val_mod;
        ostaci.push_back(x);
        potencije.push_back(potencija);
        potencija *= 2;
    }
    potencija /= 2;
    /*for(i = potencija; i < val_exp; i++) {
        x = (x*(val_baza%val_mod)) % val_mod;
    }*/
    while(true) {
        int j = 0;
        while(potencija+ potencije[j] <= val_exp) j++;
        if(j == 0) break;
        j--;
        x = (x*ostaci[j]) % val_mod;
        potencija += potencije[j];
    }
    int ret = static_cast<int>(x);
    return ret;
  }
