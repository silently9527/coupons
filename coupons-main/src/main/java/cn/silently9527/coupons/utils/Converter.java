package cn.silently9527.coupons.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;


/**
 * 类型转换者
 *
 * @author starBlues
 * @version 1.0
 */
public class Converter {

    /**
     * Gets a String from a Object in a null-safe manner.
     * <p>
     * The String is obtained via <code>toString</code>.
     *
     * @param obj   the object to use
     * @return the value of the Object as a String, <code>null</code> if null object input
     */
    public static String getAsString(final Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    /**
     * Gets a String from a Object in a null-safe manner.
     * <p>
     * The String is obtained via <code>toString</code>.
     *
     * @param obj           the object to use
     * @param defaultValue  what to return if the value is null or if the conversion fails
     * @return the value of the Object as a String, <code>defaultValue</code> if null object input
     */
    public static String getAsString(final Object obj, String defaultValue) {
        String answer = getAsString(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a Number from a Object in a null-safe manner.
     * <p>
     * If the value is a <code>Number</code> it is returned directly.
     * If the value is a <code>String</code> it is converted using
     * {@link NumberFormat#parse(String)} on the system default formatter
     * returning <code>null</code> if the conversion fails.
     * Otherwise, <code>null</code> is returned.
     *
     * @param obj   the object to use
     * @return the value of the Object as a Number, <code>null</code> if null object input
     */
    public static Number getAsNumber(final Object obj) {
        if (obj != null) {
            if (obj instanceof Number) {
                return (Number) obj;
            } else if (obj instanceof Boolean) {
                Boolean flag = (Boolean) obj;
                return flag ? 1 : 0;
            } else if (obj instanceof String) {
                try {
                    String text = (String) obj;
                    return NumberFormat.getInstance().parse(text);
                } catch (ParseException e) {
                    // ignore exception
                }
            }
        }
        return null;
    }

    /**
     *  Converting the Object into a number,
     *  using the default value if the the conversion fails.
     *
     * @param obj           the object to use
     * @param defaultValue  what to return if the value is null or if the conversion fails
     * @return the value of the object as a number, or defaultValue if the
     *    original value is null, the object is null or the number conversion
     *    fails
     */
    public static Number getAsNumber(final Object obj, Number defaultValue) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a Boolean from a Object in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> it is returned directly.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>null</code> is returned.
     *
     * @param obj   the object to use
     * @return the value of the Object as a Boolean, <code>null</code> if null object input
     */
    public static Boolean getAsBoolean(final Object obj) {
        if (obj != null) {
            if (obj instanceof Boolean) {
                return (Boolean) obj;
            } else if (obj instanceof String) {
                return Boolean.valueOf((String) obj);
            } else if (obj instanceof Number) {
                Number n = (Number) obj;
                return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
            }
        }
        return null;
    }

    /**
     * Gets a Boolean from a Object in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> it is returned directly.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>null</code> is returned.
     *
     * @param obj           the object to use
     * @param defaultValue  what to return if the value is null or if the conversion fails
     * @return the value of the Object as a Boolean, <code>defaultValue</code> if null object input
     */
    public static Boolean getAsBoolean(final Object obj, Boolean defaultValue) {
        Boolean answer = getAsBoolean(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a boolean from a Object in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> its value is returned.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>false</code> is returned.
     *
     * @param obj   the object to use
     * @return the value of the Object as a Boolean, <code>false</code> if null object input
     */
    public static boolean getAsBooleanValue(final Object obj) {
        Boolean booleanObject = getAsBoolean(obj);
        if (booleanObject == null) {
            return false;
        }
        return booleanObject.booleanValue();
    }

    /**
     * Gets a boolean from a Object in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> its value is returned.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>false</code> is returned.
     *
     * @param obj           the object to use
     * @param defaultValue  what to return if the value is null or if the conversion fails
     * @return the value in the Map as a Boolean, <code>defaultValue</code> if null object input
     */
    public static boolean getAsBooleanValue(final Object obj, boolean defaultValue) {
        Boolean booleanObject = getAsBoolean(obj);
        if (booleanObject == null) {
            return defaultValue;
        }
        return booleanObject.booleanValue();
    }

    /**
     * Gets a Byte from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Byte is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @return the value of Object as a Byte, <code>null</code> if null object input
     */
    public static Byte getAsByte(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Byte) {
            return (Byte) answer;
        }
        return Byte.valueOf(answer.byteValue());
    }

    /**
     * Gets a Byte from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Byte is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a Byte, <code>defaultValue</code> if null object input
     */
    public static Byte getAsByte(final Object obj, Byte defaultValue) {
        Byte answer = getAsByte(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a byte from a Object in a null-safe manner.
     * <p>
     * The byte is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of the Object as a byte, <code>0</code> if null object input
     */
    public static byte getAsByteValue(final Object obj) {
        Byte byteObject = getAsByte(obj);
        if (byteObject == null) {
            return 0;
        }
        return byteObject.byteValue();
    }

    /**
     * Gets a byte from a Object in a null-safe manner.
     * <p>
     * The byte is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of the Object as a byte, <code>defaultValue</code> if null object input
     */
    public static byte getAsByteValue(final Object obj, byte defaultValue) {
        Byte byteObject = getAsByte(obj);
        if (byteObject == null) {
            return defaultValue;
        }
        return byteObject.byteValue();
    }

    /**
     * Gets a Short from a Object in a null-safe manner.
     * <p>
     * The Short is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of the Object as a Short, <code>null</code> if null object input
     */
    public static Short getAsShort(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Short) {
            return (Short) answer;
        }
        return Short.valueOf(answer.shortValue());
    }

    /**
     * Gets a Short from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Short is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a Short, <code>defaultValue</code> if null object input
     */
    public static Short getAsShort(final Object obj, Short defaultValue) {
        Short answer = getAsShort(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a short from a Object in a null-safe manner.
     * <p>
     * The short is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of the Object as a short, <code>0</code> if null object input
     */
    public static short getAsShortValue(final Object obj) {
        Short shortObject = getAsShort(obj);
        if (shortObject == null) {
            return 0;
        }
        return shortObject.shortValue();
    }

    /**
     * Gets a short from a Object in a null-safe manner.
     * <p>
     * The short is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of the Object as a short, <code>defaultValue</code> if null object input
     */
    public static short getAsShortValue(final Object obj, short defaultValue) {
        Short shortObject = getAsShort(obj);
        if (shortObject == null) {
            return defaultValue;
        }
        return shortObject.shortValue();
    }

    /**
     * Gets a Integer from a Object in a null-safe manner.
     * <p>
     * The Integer is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of the Object as a Integer, <code>null</code> if null object input
     */
    public static Integer getAsInteger(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return Integer.valueOf(answer.intValue());
    }

    /**
     * Gets a Integer from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Integer is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a Integer, <code>defaultValue</code> if null object input
     */
    public static Integer getAsInteger(final Object obj, Integer defaultValue) {
        Integer answer = getAsInteger(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a int from a Object in a null-safe manner.
     * <p>
     * The int is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of the Object as a int, <code>0</code> if null object input
     */
    public static int getAsIntValue(final Object obj) {
        Integer integerObject = getAsInteger(obj);
        if (integerObject == null) {
            return 0;
        }
        return integerObject.intValue();
    }

    /**
     * Gets a int from a Object in a null-safe manner.
     * <p>
     * The int is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of the Object as a int, <code>defaultValue</code> if null object input
     */
    public static int getAsIntValue(final Object obj, int defaultValue) {
        Integer integerObject = getAsInteger(obj);
        if (integerObject == null) {
            return defaultValue;
        }
        return integerObject.intValue();
    }

    /**
     * Gets a Long from a Object in a null-safe manner.
     * <p>
     * The Long is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of the Object as a Long, <code>null</code> if null object input
     */
    public static Long getAsLong(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Long) {
            return (Long) answer;
        }
        return Long.valueOf(answer.longValue());
    }

    /**
     * Gets a Long from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Long is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a Long, <code>defaultValue</code> if null object input
     */
    public static Long getAsLong(final Object obj, Long defaultValue) {
        Long answer = getAsLong(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a long from a Object in a null-safe manner.
     * <p>
     * The long is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of the Object as a long, <code>0L</code> if null object input
     */
    public static long getAsLongValue(final Object obj) {
        Long longObject = getAsLong(obj);
        if (longObject == null) {
            return 0L;
        }
        return longObject.longValue();
    }

    /**
     * Gets a long from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The long is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a long, <code>defaultValue</code> if null object input
     */
    public static long getAsLongValue(final Object obj, long defaultValue) {
        Long longObject = getAsLong(obj);
        if (longObject == null) {
            return defaultValue;
        }
        return longObject.longValue();
    }

    /**
     * Gets a Float from a Object in a null-safe manner.
     * <p>
     * The Float is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of the Object as a Float, <code>null</code> if null object input
     */
    public static Float getAsFloat(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Float) {
            return (Float) answer;
        }
        return Float.valueOf(answer.floatValue());
    }

    /**
     * Gets a Float from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Float is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a Float, <code>defaultValue</code> if null object input
     */
    public static Float getAsFloat(final Object obj, Float defaultValue) {
        Float answer = getAsFloat(obj);
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a float from a Object in a null-safe manner.
     * <p>
     * The float is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of Object as a float, <code>0.0F</code> if null object input
     */
    public static float getAsFloatValue(final Object obj) {
        Float floatObject = getAsFloat(obj);
        if (floatObject == null) {
            return 0f;
        }
        return floatObject.floatValue();
    }

    /**
     * Gets a float from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The float is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a float, <code>defaultValue</code> if null object input
     */
    public static float getAsFloatValue(final Object obj, float defaultValue) {
        Float floatObject = getAsFloat(obj);
        if (floatObject == null) {
            return defaultValue;
        }
        return floatObject.floatValue();
    }

    /**
     * Gets a Double from a Object in a null-safe manner.
     * <p>
     * The Double is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of the Object as a Double, <code>null</code> if null object input
     */
    public static Double getAsDouble(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Double) {
            return (Double) answer;
        }
        return Double.valueOf(answer.doubleValue());
    }

    /**
     * Gets a Double from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Double is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a Double, <code>defaultValue</code> if null object input
     */
    public static Double getAsDouble(final Object obj, Double defaultValue) {
        Double answer = getAsDouble(obj);
        if ( answer == null ) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a double from a Object in a null-safe manner.
     * <p>
     * The double is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of Object as a double, <code>0.0</code> if null object input
     */
    public static double getAsDoubleValue(final Object obj) {
        Double doubleObject = getAsDouble(obj);
        if (doubleObject == null) {
            return 0d;
        }
        return doubleObject.doubleValue();
    }

    /**
     * Gets a double from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The double is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a double, <code>defaultValue</code> if null object input
     */
    public static double getAsDoubleValue(final Object obj, double defaultValue) {
        Double doubleObject = getAsDouble(obj);
        if (doubleObject == null) {
            return defaultValue;
        }
        return doubleObject.doubleValue();
    }

    /**
     * Gets a BigInteger from a Object in a null-safe manner.
     * <p>
     * The BigInteger is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of Object as a BigInteger, <code>0</code> if null object input
     */
    public static BigInteger getAsBigInteger(final Object obj) {
        Long longObject = getAsLong(obj);
        if (longObject == null) {
            return BigInteger.ZERO;
        }
        return BigInteger.valueOf(longObject.longValue());
    }

    /**
     * Gets a BigInteger from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The BigInteger is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a BigInteger, <code>defaultValue</code> if null object input
     */
    public static BigInteger getAsBigInteger(final Object obj, BigInteger defaultValue) {
        Long longObject = getAsLong(obj);
        if (longObject == null) {
            return defaultValue;
        }
        return BigInteger.valueOf(longObject.longValue());
    }

    /**
     * Gets a BigDecimal from a Object in a null-safe manner.
     * <p>
     * The BigDecimal is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of Object as a BigDecimal, <code>0</code> if null object input
     */
    public static BigDecimal getAsBigDecimal(final Object obj) {
        Double doubleObject = getAsDouble(obj);
        if (doubleObject == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(doubleObject.doubleValue());
    }

    /**
     * Gets a BigDecimal from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The BigDecimal is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a BigDecimal, <code>defaultValue</code> if null object input
     */
    public static BigDecimal getAsBigDecimal(final Object obj, BigDecimal defaultValue) {
        Double doubleObject = getAsDouble(obj);
        if (doubleObject == null) {
            return defaultValue;
        }
        return BigDecimal.valueOf(doubleObject.doubleValue());
    }

    /**
     *
     *
     * @param obj           the object to use
     * @param clz           the type for conversion
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <R> R cast(final Object obj, final Class<R> clz) {
        R result = null;
        if (Boolean.class.equals(clz) || boolean.class.equals(clz)) {
            result = (R) getAsBoolean(obj);
        } else if (Byte.class.equals(clz) || byte.class.equals(clz)) {
            result = (R) getAsByte(obj);
        } else if (Short.class.equals(clz) || short.class.equals(clz)) {
            result = (R) getAsShort(obj);
        } else if (Integer.class.equals(clz) || int.class.equals(clz)) {
            result = (R) getAsInteger(obj);
        } else if (Long.class.equals(clz) || long.class.equals(clz)) {
            result = (R) getAsLong(obj);
        } else if (Float.class.equals(clz) || float.class.equals(clz)) {
            result = (R) getAsFloat(obj);
        } else if (Double.class.equals(clz) || double.class.equals(clz)) {
            result = (R) getAsDouble(obj);
        } else if (BigInteger.class.equals(clz)) {
            result = (R) getAsBigInteger(obj);
        } else if (BigDecimal.class.equals(clz)) {
            result = (R) getAsBigDecimal(obj);
        } else if (String.class.equals(clz)) {
            result = (R) getAsString(obj);
        }
        return result;
    }

    /**
     *
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <R> R cast(final Object obj, R defaultValue) {
        R result = null;
        if (defaultValue == null) { return result; }
        Class<?> clz = defaultValue.getClass();
        if (Boolean.class.equals(clz) || boolean.class.equals(clz)) {
            result = (R) getAsBoolean(obj, (Boolean) defaultValue);
        } else if (Byte.class.equals(clz) || byte.class.equals(clz)) {
            result = (R) getAsByte(obj, (Byte) defaultValue);
        } else if (Short.class.equals(clz) || short.class.equals(clz)) {
            result = (R) getAsShort(obj, (Short) defaultValue);
        } else if (Integer.class.equals(clz) || int.class.equals(clz)) {
            result = (R) getAsInteger(obj, (Integer) defaultValue);
        } else if (Long.class.equals(clz) || long.class.equals(clz)) {
            result = (R) getAsLong(obj, (Long) defaultValue);
        } else if (Float.class.equals(clz) || float.class.equals(clz)) {
            result = (R) getAsFloat(obj, (Float) defaultValue);
        } else if (Double.class.equals(clz) || double.class.equals(clz)) {
            result = (R) getAsDouble(obj, (Double) defaultValue);
        } else if (BigInteger.class.equals(clz)) {
            result = (R) getAsBigInteger(obj, (BigInteger) defaultValue);
        } else if (BigDecimal.class.equals(clz)) {
            result = (R) getAsBigDecimal(obj, (BigDecimal) defaultValue);
        } else if (String.class.equals(clz)) {
            result = (R) getAsString(obj, (String) defaultValue);
        }
        return result;
    }

    /**
     * 是否是基本数据类型
     * @param clazz 类型
     * @return 是基本数据类型的话, 则返回true
     */
    public static boolean isBasicDataType(Class clazz){
        if(clazz == null){
            throw new RuntimeException("ParamClass return can't be null");
        }
        return(
                clazz.equals(String.class) ||
                        clazz.equals(Integer.class)||
                        clazz.equals(Byte.class) ||
                        clazz.equals(Long.class) ||
                        clazz.equals(Double.class) ||
                        clazz.equals(Float.class) ||
                        clazz.equals(Character.class) ||
                        clazz.equals(Short.class) ||
                        clazz.equals(BigDecimal.class) ||
                        clazz.equals(BigInteger.class) ||
                        clazz.equals(Boolean.class) ||
                        clazz.equals(Date.class) ||
                        clazz.isPrimitive()
        );
    }

}

