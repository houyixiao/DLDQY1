package com.ts.dldqy.kuangjia.utils;

/**
 * 常用工具类
 * @author wh
 */

public class Utils {
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        boolean isEmpty = false;
        if(null == str || "".equals(str) ) {
            isEmpty = true;
        }
        return isEmpty;
    }
}
