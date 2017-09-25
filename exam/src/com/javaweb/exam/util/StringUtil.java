package com.javaweb.exam.util;

/**
 *
 * @author Nathan.Yang
 *Judge the String
 */
public class StringUtil {

    public static boolean isEmpty(String data) {
        if (data == null || data.equals("")) {
            return true;
        }
        return false;
    }

    public static String htmlEncode(String txt) {
        if (txt != null) {
            txt = replace(txt, "&", "&amp;");
            txt = replace(txt, "&amp;amp;", "&amp;");
            txt = replace(txt, "&amp;quot;", "&quot;");
            txt = replace(txt, "\"", "&quot;");
            txt = replace(txt, "&amp;lt;", "&lt;");
            txt = replace(txt, "<", "&lt;");
            txt = replace(txt, "&amp;gt;", "&gt;");
            txt = replace(txt, ">", "&gt;");
            txt = replace(txt, "&amp;nbsp;", "&nbsp;");

        }
        return txt;
    }

    public static String[] split(String strSource, String strDiv) {
        int arynum = 0, intIdx = 0, intIdex = 0;
        int div_length = strDiv.length();
        if (strSource.compareTo("") != 0) {
                if (strSource.indexOf(strDiv) != -1) {
                        intIdx = strSource.indexOf(strDiv);
                        for (int intCount = 1; ; intCount++) {
                                if (strSource.indexOf(strDiv, intIdx + div_length) != -1) {
                                        intIdx = strSource.indexOf(strDiv, intIdx + div_length);
                                        arynum = intCount;
                                }
                                else {
                                        arynum += 2;
                                        break;
                                }
                        }
                }
                else {
                        arynum = 1;
                }
        }
        else {
                arynum = 0;

        }
        intIdx = 0;
        intIdex = 0;
        String[] returnStr = new String[arynum];

        if (strSource.compareTo("") != 0) {
                if (strSource.indexOf(strDiv) != -1) {
                        intIdx = strSource.indexOf(strDiv);
                        returnStr[0] = strSource.substring(0, intIdx);
                        for (int intCount = 1; ; intCount++) {
                                if (strSource.indexOf(strDiv, intIdx + div_length) != -1) {
                                        intIdex = strSource.indexOf(strDiv, intIdx + div_length);
                                        returnStr[intCount] = strSource.substring(intIdx + div_length,
                                                intIdex);
                                        intIdx = strSource.indexOf(strDiv, intIdx + div_length);
                                }
                                else {
                                        returnStr[intCount] = strSource.substring(intIdx + div_length,
                                                strSource.length());
                                        break;
                                }
                        }
                }
                else {
                        returnStr[0] = strSource.substring(0, strSource.length());
                        return returnStr;
                }
        }
        else {
                return returnStr;
        }
        return returnStr;
}

    public static String doWithNull(Object o) {
        if(o == null) {
            return "";
        } else{
            String returnValue = o.toString();
            if(returnValue.equalsIgnoreCase("null")) {
                return "";
            } else {
                return returnValue.trim();
            }
        }

    }


    public static String replace(String str, String strSub, String strRpl) {
        String[] tmp = split(str, strSub);
        String returnstr = "";
        if (tmp.length != 0) {
            returnstr = tmp[0];
            for (int i = 0; i < tmp.length - 1; i++) {
                returnstr = doWithNull(returnstr) + strRpl + tmp[i + 1];
            }
        }
        return doWithNull(returnstr);
    }

    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
          return s;
        else
          return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static String toFuzzySql(String fuzzy) {
        String []sqlTemp = fuzzy.split("\\s+");
        String returnSql="content LIKE '%"+sqlTemp[0] +"%'";
        for(int i=1; i<sqlTemp.length; i++) {
            returnSql+= " AND content LIKE '%"+ sqlTemp[i] +"%'";
        }
        return returnSql;
    }

    public static String toQuestionId(int id) {
        int tempId = id;
        String StrId = "Q0000";
        for(int temp =0; temp < 3; temp++) {
            tempId = tempId/10;
            if ( tempId >= 1) {
               StrId = StrId.substring(0, StrId.length() - 1);
            }
        }
        StrId += id;
        return StrId;
    }
}

