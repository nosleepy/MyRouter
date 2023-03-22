package com.grandstream.calculate;

import com.grandstream.common.ICalculate;
import com.grandstream.myrouter.annotation.Route;

@Route(path = "/calculate")
public class CalculateImpl implements ICalculate {
    public int add(int a, int b) {
        return a + b;
    }
}
