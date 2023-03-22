package com.grandstream.myrouter.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRouter {
    private Context context;
    private Map<String, Class> clazzMap;

    private MyRouter() {
        clazzMap = new HashMap<>();
    }

    private static class Holder {
        private static MyRouter myRouter = new MyRouter();
    }

    public static MyRouter getInstance() {
        return Holder.myRouter;
    }

    public void init(Context context) {
        this.context = context;
        List<String> nameList = ClassUtils.getAllClassName(context, "com.grandstream");
        for (String name : nameList) {
            try {
                Class<?> clazz = Class.forName(name);
                //判断当前类是否是IRouter的实现类
                if (IRouter.class.isAssignableFrom(clazz)) {
                    IRouter iRouter = (IRouter) clazz.newInstance();
                    iRouter.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void putActivity(String path, Class clazz) {
        if (path == null || clazz == null) {
            return;
        }
        clazzMap.put(path, clazz);
    }

    public void navigation(String path, Bundle bundle) {
        if (path != null) {
            Intent intent = new Intent(context, clazzMap.get(path));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    public void navigation(String path) {
        navigation(path, null);
    }

    public IProvider getImpl(String path) {
        IProvider provider = null;
        try {
            provider = (IProvider) clazzMap.get(path).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provider;
    }
}
