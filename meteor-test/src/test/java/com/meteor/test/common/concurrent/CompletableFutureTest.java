package com.meteor.test.common.concurrent;

import cn.hutool.core.date.DateTime;
import com.meteor.common.util.NameThreadFactory;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CompletableFuture测试类
 * @author SuperMu
 * @time 2020-06-03
 * 参考链接：https://colobu.com/2016/02/29/Java-CompletableFuture/
 */
public class CompletableFutureTest {
    public CompletableFuture<Integer> compute() {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }

    @Test
    public void threadComplete() throws Exception {
        final CompletableFuture<Integer> f = compute();
        class Client extends Thread {
            CompletableFuture<Integer> f;

            Client(String threadName, CompletableFuture<Integer> f) {
                super(threadName);
                this.f = f;
            }

            @Override
            public void run() {
                try {
                    System.out.println(this.getName() + ": " + DateTime.now());
                    System.out.println(this.getName() + ": " + f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        new Client("Client1", f).start();
        new Client("Client2", f).start();
        System.out.println("waiting");
        //设置Future.get()获取到的值
        // 主动触发计算结果
        f.complete(100);
        //以异常的形式触发计算结果（抛一个异常给get）
        //f.completeExceptionally(new Exception());
        Thread.sleep(1000);
        //计算结果完成时的处理
        f.whenCompleteAsync((v, e) -> {
            System.out.println("whenCompleteAsync:return value:" + v + "  exception:" + e);
        });

        // 计算结果完成时的处理,重新返回一个新的CompletableFuture,等待get
        CompletableFuture<DateTime> handF = f.handleAsync((v, e) -> {
            System.out.println("handleAsync:return value:" + v + "  exception:" + e);
            return DateTime.now();
        });

        System.out.println(handF.get());
        Thread.sleep(1000);
        //当计算结算完成之后,后面可以接继续一系列的thenApply,来完成值的转化.
        CompletableFuture<String> future3 = f.thenApply((element)->{
            return element+"  addPart";
        }).thenApply((element)->{
            return element+"  addTwoPart";
        });
        System.out.println(future3.get());//hello world  addPart  addTwoPart
    }

    @Test
    public void executorServiceTest() throws Exception{
        ExecutorService executorService= Executors.newFixedThreadPool(5,new NameThreadFactory("CompleteTest"));
        final CompletableFuture<Integer> f = compute();

        f.complete(100,executorService);
    }
}
