package com.datazuul.urn.nbn;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Documentation of algorithm: http://www.pruefziffernberechnung.de/U/URN.shtml</p>
 * 
 * <p>This calculation of the check digit is only valid for namespace "URN:NBN:DE". It is not valid for any other URN.</p>
 * 
 * <ol>
 * <li>Replace every single char of the given URN to a number according to the specified mapping table.</li>
 * <li>After the character substitution, the check digit can be calculated. All digits are weighted (multiplied) from left to right, starting with the first digit, with their position in the digit sequence.</li>
 * <li>The products are summed up.</li>
 * <li>The product total is divided by the last digit of the URN.</li>
 * <li>The ones place of the quotient is the check digit.</li>
 * </ol>
 */
public class UrnNbnDeCheckDigitCalculator {

  private final static Map<String, Integer> MAPPING_TO_NUMBERS = Stream.of(new Object[][]{
    {"0", 1},
    {"1", 2},
    {"2", 3},
    {"3", 4},
    {"4", 5},
    {"5", 6},
    {"6", 7},
    {"7", 8},
    {"8", 9},
    {"9", 41},
    {"a", 18},
    {"b", 14},
    {"c", 19},
    {"d", 15},
    {"e", 16},
    {"f", 21},
    {"g", 22},
    {"h", 23},
    {"i", 24},
    {"j", 25},
    {"k", 42},
    {"l", 26},
    {"m", 27},
    {"n", 13},
    {"o", 28},
    {"p", 29},
    {"q", 31},
    {"r", 12},
    {"s", 32},
    {"t", 33},
    {"u", 11},
    {"v", 34},
    {"w", 35},
    {"x", 36},
    {"y", 37},
    {"z", 38},
    {"_", 43}, // underscore
    {".", 47}, // full stop
    {":", 17}, // colon
    {"-", 39}, // dash
    {"/", 45}, // slash
  }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

  /**
   * Important: it is important to also pass trailing dash!
   *
   * @param urnWithoutCheckDigit e.g. "urn:nbn:de:bvb:12-bsb00103137-"
   * @return calculated URN with appended check digit, e.g. urn:nbn:de:bvb:12-bsb00103137-3"
   */
  public static String appendCheckDigit(String urnWithoutCheckDigit) {
    String checkDigit = calc(urnWithoutCheckDigit);
    return urnWithoutCheckDigit + checkDigit;
  }

  public static String calc(String urnWithoutCheckDigit) {
    String numberString = convertToNumberString(urnWithoutCheckDigit);
    Integer lastNumber = lastNumber(numberString);
    int sumOfProducts = calcSumOfProducts(numberString);
    String checkDigit = createCheckDigit(sumOfProducts, lastNumber);
    return checkDigit;
  }

  protected static int calcSumOfProducts(String numberString) {
    String[] charStrings = numberString.split("");
    int sumOfProducts = 0;
    for (int i = 1; i <= numberString.length(); i++) {
      sumOfProducts += (Integer.valueOf(charStrings[i - 1]) * i);
    }
    return sumOfProducts;
  }

  protected static String convertToNumberString(String urnWithoutCheckDigit) {
    StringBuilder sb = new StringBuilder();
    String input = urnWithoutCheckDigit.toLowerCase();
    String[] charStrings = input.split("");
    for (String charString : charStrings) {
      sb.append(MAPPING_TO_NUMBERS.get(charString));
    }
    return sb.toString();
  }

  protected static String createCheckDigit(int sumOfProducts, Integer lastNumber) {
    String quotient = String.valueOf(sumOfProducts / lastNumber); // division of two integers gives an integer (what we want: cut decimals)
    String checkDigit = quotient.substring(quotient.length() - 1);
    return checkDigit;
  }

  protected static Integer lastNumber(String numberString) {
    String lastNumber = numberString.substring(numberString.length() - 1);
    return Integer.valueOf(lastNumber);
  }

  private UrnNbnDeCheckDigitCalculator() {
  }
}
