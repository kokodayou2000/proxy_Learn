package org.example.oldJDK;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Proxy;
import java.util.function.Function;

public class Demo2 {
    public static void main(String[] args) {

        final Student a = new Student();

        final  Name name =  (Name)Proxy.newProxyInstance(Demo2.class.getClassLoader(), new Class[]{Name.class,Function.class}, (proxy, method, args1) -> {

            //当返回的结果是this的时候，method.invoke执行后的返回结果就是this
            //但是链式调用呢？
            System.out.println("执行方法的返回值 >> "+method.invoke(a, args1));
            return proxy;
        });

        name.hello();
        Function f = (Function)name;
        f.apply("Hello Function");

    }

    interface  Name {
        void hello();

    }


    @Data
    @Accessors(chain = true)
    static class Student implements Name , Function {


        @Override
        public Object apply(Object o) {
            System.out.println(o);
            return o;
        }

        @Override
        public void hello() {
            System.out.println("Hello Name");
        }
    }
}