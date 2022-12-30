package org.example.cglib;

import lombok.Data;
import lombok.experimental.Accessors;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Demo1 {
    public static void main(String[] args) {
        final Stu stu = new Stu();

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Stu.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                //obj参数
                System.out.println("Hello Cglib");
                method.invoke(stu, objects);
                return o;
            }
        });

        final Stu student = (Stu)enhancer.create();
        student.setAge("18");
        student.setName("Tom");
        student.andThen("Hello").andThen("World");
//        student.andThen("Work").andThen("Play Game ");

    }



    @Data
    @Accessors(chain = true)
    static class Stu {
        String name;
        String age;
        Stu andThen(String str){
            System.out.println( "The "+name+" do "+str);
            return this;
        }
    }
}
