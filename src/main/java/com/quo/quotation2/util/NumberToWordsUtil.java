package com.quo.quotation2.util;

public class NumberToWordsUtil {

    private static final String[] units = {
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
            "Seventeen", "Eighteen", "Nineteen"
    };

    private static final String[] tens = {
            "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };

    public static String convert(double amount) {
        long rupees = (long) amount;
        int paise = (int) Math.round((amount - rupees) * 100);

        String rupeesWords = convertNumber(rupees);
        String result = rupeesWords + " Rupees";

        if (paise > 0) {
            String paiseWords = convertNumber(paise);
            result += " and " + paiseWords + " Paise";
        }

        return result + " Only";
    }

    private static String convertNumber(long number) {
        if (number == 0) {
            return "Zero";
        }

        String words = "";

        if ((number / 10000000) > 0) {
            words += convertNumber(number / 10000000) + " Crore ";
            number %= 10000000;
        }

        if ((number / 100000) > 0) {
            words += convertNumber(number / 100000) + " Lakh ";
            number %= 100000;
        }

        if ((number / 1000) > 0) {
            words += convertNumber(number / 1000) + " Thousand ";
            number %= 1000;
        }

        if ((number / 100) > 0) {
            words += convertNumber(number / 100) + " Hundred ";
            number %= 100;
        }

        if (number > 0) {
            if (number < 20) {
                words += units[(int) number];
            } else {
                words += tens[(int) number / 10];
                if ((number % 10) > 0) {
                    words += " " + units[(int) number % 10];
                }
            }
        }

        return words.trim();
    }
}