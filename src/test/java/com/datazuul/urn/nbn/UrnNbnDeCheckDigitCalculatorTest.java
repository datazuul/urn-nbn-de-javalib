package com.datazuul.urn.nbn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UrnNbnDeCheckDigitCalculatorTest {

  @Test
  public void testAppendCheckDigit() {
    String urn = UrnNbnDeCheckDigitCalculator.appendCheckDigit("urn:nbn:de:gbv:089-332175294");
    assertEquals("urn:nbn:de:gbv:089-3321752945", urn);
    
    urn = UrnNbnDeCheckDigitCalculator.appendCheckDigit("urn:nbn:de:bvb:12-bsb00103137-");
    assertEquals("urn:nbn:de:bvb:12-bsb00103137-3", urn);
  }

  @Test
  public void testCalcSumOfProducts() {
    int sumOfProducts = UrnNbnDeCheckDigitCalculator.calcSumOfProducts("1112131713141317151617221434171941394432863415");
    assertEquals(4027, sumOfProducts);
  }

  @Test
  public void testConvertToNumberString() {
    String numberString = UrnNbnDeCheckDigitCalculator.convertToNumberString("urn:nbn:de:gbv:089-332175294");
    assertEquals("1112131713141317151617221434171941394432863415", numberString);
  }

  @Test
  public void testCreateCheckDigit() {
    String checkDigit = UrnNbnDeCheckDigitCalculator.createCheckDigit(4027, 5);
    assertEquals("5", checkDigit);
  }

  @Test
  public void testLastNumber() {
    Integer lastNumber = UrnNbnDeCheckDigitCalculator.lastNumber("1324182587");
    assertEquals(7, lastNumber);
  }

  @Test
  public void testCalc() {
    String checkDigit = UrnNbnDeCheckDigitCalculator.calc("urn:nbn:de:gbv:089-332175294");
    assertEquals("5", checkDigit);
  }
}
