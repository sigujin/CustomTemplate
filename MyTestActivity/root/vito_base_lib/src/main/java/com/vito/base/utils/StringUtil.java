package com.vito.base.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

public class StringUtil {

    public static String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {//汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 获取随机字母数字组合
     *
     * @param length 字符串长度
     * @return
     */
    public static String getRandomCharAndNum(int length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
                b = random.nextBoolean();
                if (b) {
                    str += (char) (65 + random.nextInt(26));// 取得大写字母
                } else {
                    str += (char) (97 + random.nextInt(26));// 取得大写字母
                }
            } else { // 数字
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }

    public static String changeCharsToStars(String in_str, int in_spos, int in_epos) {
        if (in_str != null && in_str.length() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < in_str.length(); i++) {
                char c = in_str.charAt(i);
                if (i >= in_spos && i <= in_epos) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        return "";
    }

    public static boolean isMobileNO(String mobiles) {
        if(mobiles == null)
            return false;
        Pattern p = Pattern.compile("^(1)[3|4|5|7|8]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String chargeFormat(double in_charge) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(in_charge);//format 返回的是字符串
        return p;
    }

    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static String formateDateString(String in_str) {
        String result = "";
        String str = in_str.substring(0, 4);
        result += str + "-";

        str = in_str.substring(4, 6);
        result += str + "-";

        str = in_str.substring(6, 8);
        result += str + " ";

        str = in_str.substring(8, 10);
        result += str + ":";
        str = in_str.substring(10, 12);
        result += str + ":";
        str = in_str.substring(12);
        result += str;

        return result;
    }

    public static String chooseUrl(String in_str, int type) {
        String[] arry = null;
        arry = in_str.split(";");
//        String startStr=in_str.substring(in_str.indexOf(";") + 1);
//        String endStr=in_str.substring(in_str.endsWith(";"));
        if (type == 0)
            return arry[0];
        else return arry[arry.length];
    }

    public static String floatTotwo(float a) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String p = decimalFormat.format(a);
        return p;
    }

    public static Spannable genMultiSizeSpanable(List<Map<String, Object>> in_data, String in_str) {
        Spannable WordtoSpan = new SpannableString(in_str);
        for (Map<String, Object> temp : in_data) {
            WordtoSpan.setSpan(new AbsoluteSizeSpan((Integer) temp.get("size_sp"))
                    , (Integer) temp.get("start_pos"), (Integer) temp.get("end_pos"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return WordtoSpan;
    }
}
