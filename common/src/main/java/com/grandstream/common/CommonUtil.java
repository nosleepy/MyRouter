package com.grandstream.common;

import com.grandstream.myrouter.api.MyRouter;

public class CommonUtil {
    public static int add(int a, int b) {
        ICalculate calculate = (ICalculate) MyRouter.getInstance().getImpl("/calculate");
        return calculate.add(a, b);
    }
}
