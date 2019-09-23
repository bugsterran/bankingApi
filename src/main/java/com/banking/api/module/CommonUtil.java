package com.banking.api.module;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CommonUtil {

    private static final String STR_ZERO = "0";

    /**
     * 숫자 여부 체크
     *
     * @param s VALUE
     * @return
     */
    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static BigDecimal stringToBigDecimal(String s) {

        String num = isNumeric(s) ? s : STR_ZERO;

        return new BigDecimal(num).setScale(1);
    }

    public static boolean isNotYearFormat(String year){

        if(year.length() != 4){
            return true;
        }

        if(!isNumeric(year)){
            return true;
        }

        return false;
    }

    public static boolean isNotDeviceIdFormat(Long id){

        if(id.toString().length() != 10){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object object){

        if(object == null){
            return true;
        }

        if(object instanceof List){
            List list = (List)object;
            return list.isEmpty();
        } else if(object instanceof Map){
            Map map = (Map)object;
            return map.isEmpty();
        }else if(object instanceof String){
            return ((String) object).isEmpty();
        }

        return false;
    }
}
