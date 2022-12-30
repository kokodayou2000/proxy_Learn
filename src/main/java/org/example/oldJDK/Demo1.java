package org.example.oldJDK;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Proxy;

public class Demo1 {
    public static void main(String[] args) {

        final Student a = new Student().setName("Lombok setName");

        final  Name name =  (Name)Proxy.newProxyInstance(Demo1.class.getClassLoader(), new Class[]{Name.class}, (proxy, method, args1) -> {
            System.out.println("方法名："+method.getName());
            System.out.println("参 数："+JSON.toJSONString(args1));
            Object ret = method.invoke(a, args1);
            System.out.println("返回值："+JSON.toJSONString(ret));
            if (a.name != null){
                System.out.println("getName ->> "+a.name);
            }
            return proxy;
        });

        name.out("Hello World!");

        name.andThen("andThen A")
                .andThen("andThen B");

        String innerName = name.getInnerName();
        

        System.out.println("=======");
    }

    interface  Name {
        void out(String str);
        String getInnerName();

        Name andThen(String str);

    }


    @Data
    @Accessors(chain = true)
    static class Student implements Name {
        String name;

        @Override
        public void out(String str) {
            System.out.println("A out "+str);
        }

        @Override
        public String getInnerName() {
            return "A Name";
        }

        @Override
        public Name andThen(String str) {
            System.out.println("AndTehn "+str);
            return this;
        }
    }
}