package com.qcby.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author kevinlyz
 * @ClassName ClassScanner
 * @Description 通过类加载器
 * @Date 2019-06-08 17:26
 **/
public class ClassScanner {

    public static List<Class<?>> scannClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".","/");
        //获取类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()){
            URL resource = resources.nextElement();
            //处理资源类型是jar包的情况
            if (resource.getProtocol().contains("jar")){
                JarURLConnection jarURLConnection =
                        (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classes.addAll(getClassFromJar(jarFilePath,path));
            }else{
                // TODO: 处理jar包以外的情况
            }
        }
        return classes;
    }

    /**
     * @Author kevinlyz
     * @Description 从jar包中获取资源
     * @Date 17:37 2019-06-08
     * @Param
     * @return List<Class<?>>
     **/
    public static List<Class<?>> getClassFromJar(String jarFilePath,String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        //获取jar实例
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntrys = jarFile.entries();
        while (jarEntrys.hasMoreElements()){
            JarEntry jarEntry = jarEntrys.nextElement();
            //获取类路径名  如  com/qcby/test/Test.class
            String entryName = jarEntry.getName();
            //获取的
            if (entryName.startsWith(path)&&entryName.endsWith(".class")){
                //路径替换
                String classFullName = entryName.replace("/",".").substring(0,entryName.length()-6);
                //反射获取类信息并添加至list
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}
