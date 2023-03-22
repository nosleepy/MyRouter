//package com.grandstream.myrouter.processor;
//
//import com.google.auto.service.AutoService;
//import com.grandstream.myrouter.annotation.Path;
//
//import java.io.IOException;
//import java.io.Writer;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import javax.annotation.processing.AbstractProcessor;
//import javax.annotation.processing.Filer;
//import javax.annotation.processing.ProcessingEnvironment;
//import javax.annotation.processing.Processor;
//import javax.annotation.processing.RoundEnvironment;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.TypeElement;
//import javax.tools.JavaFileObject;
//
//@AutoService(Processor.class)
//public class MyRouterProcessorUseJoint extends AbstractProcessor {
//    //生成文件对象
//    private Filer filer;
//    private String moduleName;
//    private String packageName;
//
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        filer = processingEnv.getFiler();
//        moduleName = processingEnv.getOptions().get("MY_ROUTER_MODULE_NAME");
//        packageName = "com.grandstream." + moduleName;
//        System.err.println("packageName = " + packageName);
//    }
//
//    //声明返回要处理哪个注解
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> types = new HashSet<>();
//        types.add(Path.class.getCanonicalName());
//        return types;
//    }
//
//    //支持Java版本
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return processingEnv.getSourceVersion();
//    }
//
//    //注解处理器的核心
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        //拿到该模块所有path注解的节点
//        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Path.class);
//        //结构化数据
//        Map<String, String> map = new HashMap<>();
//        for (Element element : elements) {
//            //实际就是类节点
//            TypeElement typeElement = (TypeElement) element;
//            Path annotation = typeElement.getAnnotation(Path.class);
//            //读取到key
//            String key = annotation.path();
//            //包名+类名
//            String activityName = typeElement.getQualifiedName().toString();
//            System.err.println("activityName = " + activityName);
//            map.put(key, activityName);
//        }
//        if (map.size() > 0) {
//            //开始写文件
//            Writer writer = null;
//            String routerImpl = String.valueOf(moduleName.charAt(0)).toUpperCase()
//                    + moduleName.substring(1)
//                    + "RouterImpl";
//            try {
//                JavaFileObject javaFileObject = filer.createSourceFile(packageName + "." + routerImpl);
//                writer = javaFileObject.openWriter();
//                writer.write("package " + packageName + ";\n" +
//                        "\n"
//                        + "import com.grandstream.myrouter.api.MyRouter;\n"
//                        + "import com.grandstream.myrouter.api.IRouter;\n"
//                        + "\n"
//                        + "public class " + routerImpl + " implements IRouter {\n"
//                        + "\n" +
//                        "    @Override\n" +
//                        "    public void putActivity() {"
//                        +   "\n");
//                Iterator<String> iterator = map.keySet().iterator();
//                while (iterator.hasNext()) {
//                    String path = iterator.next();
//                    String value = map.get(path);
//                    writer.write("        MyRouter.getInstance().putActivity(\"" + path + "\","
//                            + value + ".class);\n");
//                }
//                writer.write("    }\n}");
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (writer != null) {
//                    try {
//                        writer.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return false;
//    }
//}
