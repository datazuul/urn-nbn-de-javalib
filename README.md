# Check digit calculation library for "URN:NBN:DE" namespace

A Java library for calculating the check digit for URNs of the namespace "URN:NBN:DE".

The algorithm is only valid for URNs of the German National Library (DNB = Deutsche Nationalbibliothek).

Documentation of algorithm can be found here: <http://www.pruefziffernberechnung.de/U/URN.shtml>

## Algorithm

 1. Replace every single char of the given URN to a number according to the specified mapping table.
 2. After the character substitution, the check digit can be calculated. All digits are weighted (multiplied) from left to right, starting with the first digit, with their position in the digit sequence.
 3. The products are summed up.
 4. The product total is divided by the last digit of the URN.
 5. The ones place of the quotient is the check digit.

## Usage

* Get check digit:

```
String urnWithoutCheckDigit = ...;
String checkDigit = UrnNbnDeCheckDigitCalculator.calc(urnWithoutCheckDigit);
```

* Calculate check digit and append it to input:

```
String urnWithoutCheckDigit = ...;
String urnWithCheckDigitAppended = UrnNbnDeCheckDigitCalculator.addCheckDigit(urnWithoutCheckDigit);
```
