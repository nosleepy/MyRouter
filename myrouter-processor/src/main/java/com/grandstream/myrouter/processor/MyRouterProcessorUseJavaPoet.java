package com.grandstream.myrouter.processor;

import com.google.auto.service.AutoService;
import com.grandstream.myrouter.annotation.Route;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class MyRouterProcessorUseJavaPoet extends AbstractProcessor {
    //生成文件对象
    private Filer filer;
    private String moduleName;
    private String packageName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        moduleName = processingEnv.getOptions().get("MY_ROUTER_MODULE_NAME");
        packageName = "com.grandstream." + moduleName;
        System.err.println("packageName = " + packageName);
    }

    //声明返回要处理哪个注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(Route.class.getCanonicalName());
        return types;
    }

    //支持Java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    //注解处理器的核心
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //拿到该模块所有path注解的节点
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Route.class);
        //结构化数据
        Map<String, String> map = new HashMap<>();
        for (Element element : elements) {
            //实际就是类节点
            TypeElement typeElement = (TypeElement) element;
            Route annotation = typeElement.getAnnotation(Route.class);
            //读取到key
            String key = annotation.path();
            //包名+类名
            String activityName = typeElement.getQualifiedName().toString();
            System.err.println("activityName = " + activityName);
            map.put(key, activityName);
        }
        if (map.size() > 0) {
            String routerImpl = String.valueOf(moduleName.charAt(0)).toUpperCase()
                    + moduleName.substring(1)
                    + "RouterImpl";
            ClassName iRouter = ClassName.get("com.grandstream.myrouter.api", "IRouter");
            TypeSpec.Builder classBuilder = TypeSpec
                    .classBuilder(routerImpl)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(iRouter);
            MethodSpec.Builder methodBuilder = MethodSpec
                    .methodBuilder("putActivity")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .returns(void.class);
            Iterator<String> iterator = map.keySet().iterator();
            ClassName myRouter = ClassName.get("com.grandstream.myrouter.api", "MyRouter");
            while (iterator.hasNext()) {
                String path = iterator.next();
                String value = map.get(path);
                methodBuilder.addStatement("$T.getInstance().putActivity($S, $L)", myRouter, path, value + ".class");
            }
            MethodSpec methodSpec = methodBuilder.build();
            classBuilder.addMethod(methodSpec);
            JavaFile javaFile = JavaFile
                    .builder(packageName, classBuilder.build())
                    .build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
