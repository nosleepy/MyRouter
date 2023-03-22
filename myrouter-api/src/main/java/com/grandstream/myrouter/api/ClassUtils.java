package com.grandstream.myrouter.api;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

public class ClassUtils {
    public static List<String> getAllClassName(Context context, String packageName) {
        List<String> classNameList = new ArrayList<>();
        try {
            DexFile dexFile = new DexFile(context.getPackageCodePath());//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = dexFile.entries();//获取df中的元素,这里包含了所有可执行的类名,该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = enumeration.nextElement();
                if (className.contains(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    classNameList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classNameList;
    }
}
