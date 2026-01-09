package com.Verzat.VerzatTechno.Util;

public class ContactNumberUtil {
    public static String formatIndianNumber(String phoneNumber) {

        if (phoneNumber == null) {
            return null;
        }

        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");

        if (phoneNumber.length() == 10) {
            return "+91" + phoneNumber;
        }

        if (phoneNumber.length() == 12 && phoneNumber.startsWith("91")) {
            return "+" + phoneNumber;
        }

        if (phoneNumber.startsWith("+91") && phoneNumber.length() == 13) {
            return phoneNumber;
        }

        throw new IllegalArgumentException("Invalid Indian mobile number");
    }
}
