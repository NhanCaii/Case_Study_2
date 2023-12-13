package util;

import java.text.DecimalFormat;

public class FormatNumberWithCommas {

    public static String formatNumberWithCommas(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }

//    public static void main(String[] args) {
//        int number1 = 1000;
//        int number2 = 4500340;
//
//        String formattedNumber1 = formatNumberWithCommas(number1);
//        String formattedNumber2 = formatNumberWithCommas(number2);
//
//        System.out.println("Formatted number 1: " + formattedNumber1); // In ra 1,000
//        System.out.println("Formatted number 2: " + formatNumberWithCommas(2345678)); // In ra 1,000,000
//    }

}
