package cn.microvideo.postman;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class PostmanApplicationTests {

    @Test
    void contextLoads() throws InterruptedException {
       // Map<String, String> cMap = new ConcurrentHashMap<>();
        Map<String, String> cMap = new HashMap<>();
        AtomicInteger num = new AtomicInteger();
        Runnable runnable1 = () -> {
            System.out.println("1111");
            cMap.put("a","1");
            cMap.put("b","2");
            cMap.put("c","3");
            cMap.put("d","4");
            cMap.put("e","5");
            cMap.put("f","6");
            cMap.put("g","7");
            cMap.put("h","8");
            cMap.put("i","9");
            cMap.put("111","111");
            cMap.put("1a","1");
            cMap.put("1b","2");
            cMap.put("1c","3");
            cMap.put("1d","4");
            cMap.put("1e","5");
            cMap.put("1f","6");
            cMap.put("1g","7");
            cMap.put("1h","8");
            cMap.put("1i","9");
            cMap.put("1111","111");
            num.getAndIncrement();
        };

        Runnable runnable2 = () -> {
            System.out.println("2222");
            cMap.put("a","1");
            cMap.put("b","2");
            cMap.put("c","3");
            cMap.put("d","4");
            cMap.put("e","5");
            cMap.put("f","6");
            cMap.put("g","7");
            cMap.put("h","8");
            cMap.put("i","9");
            cMap.put("222","222");
            cMap.put("2a","1");
            cMap.put("2b","2");
            cMap.put("2c","3");
            cMap.put("2d","4");
            cMap.put("2e","5");
            cMap.put("2f","6");
            cMap.put("2g","7");
            cMap.put("2h","8");
            cMap.put("2i","9");
            cMap.put("2222","222");
            num.getAndIncrement();
        };

        Runnable runnable3 = () -> {
            System.out.println("3333");
            cMap.put("a","1");
            cMap.put("b","2");
            cMap.put("c","3");
            cMap.put("d","4");
            cMap.put("e","5");
            cMap.put("f","6");
            cMap.put("g","7");
            cMap.put("h","8");
            cMap.put("i","9");
            cMap.put("333","333");
            cMap.put("3a","1");
            cMap.put("3b","2");
            cMap.put("3c","3");
            cMap.put("3d","4");
            cMap.put("3e","5");
            cMap.put("3f","6");
            cMap.put("3g","7");
            cMap.put("3h","8");
            cMap.put("3i","9");
            cMap.put("3333","333");
            num.getAndIncrement();
        };

        Runnable runnable4 = () -> {
            System.out.println("4444");
            cMap.put("a","1");
            cMap.put("b","2");
            cMap.put("c","3");
            cMap.put("d","4");
            cMap.put("e","5");
            cMap.put("f","6");
            cMap.put("g","7");
            cMap.put("h","8");
            cMap.put("i","9");
            cMap.put("444","444");
            cMap.put("4a","1");
            cMap.put("4b","2");
            cMap.put("4c","3");
            cMap.put("4d","4");
            cMap.put("4e","5");
            cMap.put("4f","6");
            cMap.put("4g","7");
            cMap.put("4h","8");
            cMap.put("4i","9");
            cMap.put("4444","444");
            num.getAndIncrement();
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(runnable3);
        Thread thread4 = new Thread(runnable4);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();


        //不加这个，sout什么也打印不出来
        Thread.sleep(1000);
        System.out.println("hhhh :"+num.get());
        for (String s : cMap.keySet()) {
            System.out.println(s);
        }
    }

}
