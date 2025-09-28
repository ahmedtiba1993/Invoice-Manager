package com.tiba.invoice.util;

import com.ibm.icu.text.RuleBasedNumberFormat;
import java.util.Locale;

public class NumberToWordsUtil {

  public static String convert(double amount) {
    RuleBasedNumberFormat formatter =
        new RuleBasedNumberFormat(Locale.FRENCH, RuleBasedNumberFormat.SPELLOUT);

    long dinars = (long) amount;
    long millimes = Math.round((amount - dinars) * 1000);

    String result = formatter.format(dinars) + " dinars";
    if (millimes > 0) {
      result += " et " + formatter.format(millimes) + " millimes";
    }
    return result;
  }
}
