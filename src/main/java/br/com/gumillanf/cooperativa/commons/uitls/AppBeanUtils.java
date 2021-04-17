package br.com.gumillanf.cooperativa.commons.uitls;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.apache.commons.lang3.ArrayUtils;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.beans.BeanUtils.getPropertyDescriptor;

public class AppBeanUtils {

    public static void copyNonNullProperties(Object source, Object target, String... ignoreProperties) {
        String[] nullPropertyNames = getNullPropertyNames(source);
        copyAllProperties(source, target, (String[]) ArrayUtils.addAll(nullPropertyNames, ignoreProperties));
    }

    public static void copyNonNullProperties(Object source, Object target) {
        copyAllProperties(source, target, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
                .filter(propertyName -> {
                    return isNull(wrappedSource.getPropertyValue(propertyName))
                            || (wrappedSource.getPropertyValue(propertyName) instanceof Collection
                            && ((Collection) wrappedSource.getPropertyValue(propertyName)).isEmpty());
                }).toArray(String[]::new);
    }

    private static void copyAllProperties(Object source, Object target, String... ignoreProperties)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        try {
            Field[] targetPds = target.getClass().getDeclaredFields();
            Field[] targetPdsSupperClass = target.getClass().getSuperclass().getDeclaredFields();
            List<Field> allFields = new ArrayList<>();
            allFields.addAll(Arrays.asList(targetPds));
            allFields.addAll(Arrays.asList(targetPdsSupperClass));
            List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

            for (Field targetPd : allFields) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if ((ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                    if (sourcePd != null) {
                        Method readMethod = sourcePd.getReadMethod();
                        Object value = null;
                        value = readMethod.invoke(source);
                        if (value != null) {
                            targetPd.setAccessible(true);
                            targetPd.set(target, value);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new FatalBeanException("Could not copy property from source to target", ex);
        }
    }

}
