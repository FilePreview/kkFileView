

package cn.keking.markdown.utility;

import cn.keking.markdown.constant.magic.SYMBOL;

/**
 * @author harry
 */
public class StringUtility {

    public static String getDigit(String str, int start) {
        char c;
        StringBuilder digit = new StringBuilder(10);
        while (start<str.length()&&Character.isDigit(c = str.charAt(start++))) {
            digit.append(c);
        }
        return digit.toString();
    }

    public static int getPrefixCount(String str, String prefix) {
        int count = 0;
        StringBuilder prefixBuilder = new StringBuilder();
        while (str.startsWith(prefixBuilder.append(prefix).toString())) {
            count++;
        }
        return count;
    }

    public static boolean isNullOrEmpty(Object str) {
        return str == null || SYMBOL.EMPTY.equals(str.toString().trim());
    }

    public static boolean existInArray(Object[] array, Object key) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (Object s : array) {
            if (s == null) {
                continue;
            }
            if (s.toString().trim().equalsIgnoreCase(key.toString().trim())) {
                return true;
            }
        }
        return false;
    }
}
