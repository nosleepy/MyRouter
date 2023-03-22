# MyRouter

简易版路由框架

#### 基本使用

app 模块修改 build.gradle 文件

```gradle
android {
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [MY_ROUTER_MODULE_NAME: project.getName()]
            }
        }
    }
}

dependencies {
    implementation project(':myrouter-api')
    annotationProcessor project(':myrouter-processor')
}
```

Application 中进行初始化

```java
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyRouter.getInstance().init(this);
    }
}
```

+ 组件通信

Activity 添加 @Route 注解

```java
@Route(path = "/launcher")
public class LauncherActivity extends AppCompatActivity {
    //...
}
```

不传参数

```java
MyRouter.getInstance().navigation("/launcher");
```

传递参数

```java
Bundle bundle = new Bundle();
bundle.putString("name", "apt");
MyRouter.getInstance().navigation("/launcher", bundle);
```

+ 模块调用

定义接口模块 calculate-interface

```java
public interface ICalculate extends IProvider {
    int add(int a, int b);
}
```

定义实现模块 calculate-impl

```java
@Route(path = "/calculate")
public class CalculateImpl implements ICalculate {
    public int add(int a, int b) {
        return a + b;
    }
}
```

定义公共调用模块 common

```java
ICalculate calculate = (ICalculate) MyRouter.getInstance().getImpl("/calculate");
int res =  calculate.add(a, b);
```

注意：common 依赖于 calculate-interface 和 calculate-impl, calculate-impl 依赖于 calculate-interface

#### 参考

+ [阿里巴巴ARouter实现moudle之间跳转](https://blog.csdn.net/yangshuaionline/article/details/89476316)
+ [手写ARouter路由框架](https://www.jianshu.com/p/a84ad0b4ba38)
+ [Android中获取指定包名下的所有类](https://blog.csdn.net/u011572517/article/details/52629996)
+ [（译）JavaPoet 官方教程](https://juejin.cn/post/6844904022600597517#heading-5)
+ [利用ARouter实现组件间通信，模块间相互调用问题](https://blog.csdn.net/tianhongfan10106/article/details/127721028)
