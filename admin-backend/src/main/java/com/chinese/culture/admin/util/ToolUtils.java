package com.chinese.culture.admin.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 工具类管理器
 * 统一管理所有第三方工具类的使用
 */
public class ToolUtils {

    private ToolUtils() {}

    /**
     * 字符串工具类
     */
    public static class Str {
        /**
         * 判断字符串是否为空
         */
        public static boolean isEmpty(String str) {
            return StrUtil.isEmpty(str);
        }

        /**
         * 判断字符串是否不为空
         */
        public static boolean isNotEmpty(String str) {
            return StrUtil.isNotEmpty(str);
        }

        /**
         * 判断字符串是否为空白
         */
        public static boolean isBlank(String str) {
            return StringUtils.isBlank(str);
        }

        /**
         * 判断字符串是否不为空白
         */
        public static boolean isNotBlank(String str) {
            return StringUtils.isNotBlank(str);
        }

        /**
         * 格式化字符串
         */
        public static String format(String template, Object... params) {
            return StrUtil.format(template, params);
        }
    }

    /**
     * 日期工具类
     */
    public static class Date {
        /**
         * 当前时间
         */
        public static java.util.Date now() {
            return DateUtil.date();
        }

        /**
         * 格式化日期
         */
        public static String format(java.util.Date date, String pattern) {
            return DateUtil.format(date, pattern);
        }

        /**
         * 字符串转日期
         */
        public static java.util.Date parse(String dateStr, String pattern) {
            return DateUtil.parse(dateStr, pattern);
        }
    }

    /**
     * 集合工具类
     */
    public static class Collection {
        /**
         * 判断集合是否为空
         */
        public static boolean isEmpty(java.util.Collection<?> collection) {
            return CollectionUtils.isEmpty(collection);
        }

        /**
         * 判断集合是否不为空
         */
        public static boolean isNotEmpty(java.util.Collection<?> collection) {
            return CollectionUtils.isNotEmpty(collection);
        }

        /**
         * 创建ArrayList
         */
        public static <E> ArrayList<E> newArrayList() {
            return Lists.newArrayList();
        }

        /**
         * 创建HashSet
         */
        public static <E> HashSet<E> newHashSet() {
            return Sets.newHashSet();
        }

        /**
         * 创建HashMap
         */
        public static <K, V> HashMap<K, V> newHashMap() {
            return Maps.newHashMap();
        }
    }

    /**
     * JSON工具类
     */
    public static class Json {
        /**
         * 对象转JSON字符串
         */
        public static String toJsonStr(Object object) {
            return JSONUtil.toJsonStr(object);
        }

        /**
         * JSON字符串转对象
         */
        public static <T> T toBean(String jsonStr, Class<T> beanClass) {
            return JSON.parseObject(jsonStr, beanClass);
        }
    }

    /**
     * 文件工具类
     */
    public static class File {
        /**
         * 读取文件内容
         */
        public static String readString(String path) {
            return FileUtil.readString(path, StandardCharsets.UTF_8);
        }

        /**
         * 写入文件内容
         */
        public static void writeString(String path, String content) {
            FileUtil.writeString(content, path, StandardCharsets.UTF_8);
        }

        /**
         * 读取输入流内容
         */
        public static String toString(InputStream input) {
            try {
                return IOUtils.toString(input, StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * ID生成工具类
     */
    public static class Id {
        /**
         * 生成UUID
         */
        public static String uuid() {
            return IdUtil.fastSimpleUUID();
        }

        /**
         * 生成雪花算法ID
         */
        public static long snowflake() {
            return IdUtil.getSnowflakeNextId();
        }
    }

    /**
     * 加密工具类
     */
    public static class Crypto {
        /**
         * MD5加密
         */
        public static String md5(String content) {
            return SecureUtil.md5(content);
        }

        /**
         * SHA256加密
         */
        public static String sha256(String content) {
            return SecureUtil.sha256(content);
        }
    }

    /**
     * 类型转换工具类
     */
    public static class Convert {
        /**
         * 转换为字符串
         */
        public static String toStr(Object value) {
            return cn.hutool.core.convert.Convert.toStr(value);
        }

        /**
         * 转换为Integer
         */
        public static Integer toInt(Object value) {
            return cn.hutool.core.convert.Convert.toInt(value);
        }

        /**
         * 转换为Long
         */
        public static Long toLong(Object value) {
            return cn.hutool.core.convert.Convert.toLong(value);
        }

        /**
         * 转换为Double
         */
        public static Double toDouble(Object value) {
            return cn.hutool.core.convert.Convert.toDouble(value);
        }

        /**
         * 转换为Boolean
         */
        public static Boolean toBool(Object value) {
            return cn.hutool.core.convert.Convert.toBool(value);
        }
    }

    /**
     * 断言工具类
     */
    public static class Assert {
        /**
         * 检查表达式是否为true
         */
        public static void isTrue(boolean expression, String message) {
            Preconditions.checkArgument(expression, message);
        }

        /**
         * 检查对象是否为null
         */
        public static void notNull(Object object, String message) {
            Preconditions.checkNotNull(object, message);
        }

        /**
         * 检查字符串是否不为空
         */
        public static void notEmpty(String str, String message) {
            Preconditions.checkArgument(StringUtils.isNotEmpty(str), message);
        }

        /**
         * 检查集合是否不为空
         */
        public static void notEmpty(java.util.Collection<?> collection, String message) {
            Preconditions.checkArgument(CollectionUtils.isNotEmpty(collection), message);
        }
    }
}
