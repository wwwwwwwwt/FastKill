package org.ztw.fastkill.common.utils.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class BeanUtil {

    /**
     * 忽略空属性复制对象
     * @param source：源对象
     * @param target：目标对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || ((srcValue instanceof String) && StringUtils.isEmpty((String)srcValue)))
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
