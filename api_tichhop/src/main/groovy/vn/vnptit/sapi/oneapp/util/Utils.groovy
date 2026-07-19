package vn.vnptit.sapi.oneapp.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class Utils {
    public static java.sql.Date _toSqlDate(String val, DateFormat df) {
        try {
            if (val != null) {
                return new java.sql.Date(df.parse(val).getTime());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String formatMoney(String price) {
        try {
            if (price.contains(".")) {
                price = price.split("\\.")[0];
            }
            DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            format.applyPattern("#,###,###,###");
            String s = format.format(Long.parseLong(price));
            return s.replace(",", ".");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return price;
    }

    public static String xoa_dau(String s) {
        try {
            String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            temp = pattern.matcher(temp).replaceAll("");
            return temp.replaceAll("đ", "d").replaceAll("Đ", "D");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static def extractProperties(obj) {
        obj.getClass()
                .declaredFields
                .findAll { !it.synthetic }
                .collectEntries { field ->
                    [field.name, obj."$field.name"]
                }
    }

    static boolean checkMST(String mST) {
        if (!mST) return true  // null hoặc rỗng

        // Nếu MST có độ dài 12 - là CCCD thì hợp lệ
        if (mST.length() == 12 && mST.isNumber()) return true

        boolean isTrue = false
        if (mST.length() != 14 && mST.length() != 10) return false
        if (mST.length() == 14 && mST.charAt(10) != "-" as char) return false

        int index = mST.length()
        if (!(mST[index - 1].isNumber() && mST[index - 2].isNumber() && mST[index - 3].isNumber())) return false

        int value = getInt(mST[0]) * 31 +
                getInt(mST[1]) * 29 +
                getInt(mST[2]) * 23 +
                getInt(mST[3]) * 19 +
                getInt(mST[4]) * 17 +
                getInt(mST[5]) * 13 +
                getInt(mST[6]) * 7 +
                getInt(mST[7]) * 5 +
                getInt(mST[8]) * 3

        int mod = 10 - (value % 11)
        if (Math.abs(mod) == getInt(mST[9])) {
            isTrue = true
        }
        return isTrue
    }

    static int getInt(def ch) {
        return (ch as String).isNumber() ? (ch as String).toInteger() : 0
    }

}
