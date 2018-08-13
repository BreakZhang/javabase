package fuckingJavaConcurrency;


import fuckingJavaConcurrency.util.Utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 无同步的修改在另一个线程中会读不到
 * 线程之间的数据同步 volatile
 * <a href="http://hllvm.group.iteye.com/group/topic/34932">请问R大 有没有什么工具可以查看正在运行的类的c/汇编代码</a>提到了<b>代码提升</b>的问题。
 *
 * @author Jerry Lee (oldratlee at gmail dot com)
 * @see <a href="http://hllvm.group.iteye.com/group/topic/34932">请问R大 有没有什么工具可以查看正在运行的类的c/汇编代码</a>
 */
public class NoPublishDemo {
    boolean stop = false;
//    AtomicBoolean stop=new AtomicBoolean();

    public static void main(String[] args) {
        // LoadMaker.makeLoad();

        NoPublishDemo demo = new NoPublishDemo();

        Thread thread = new Thread(demo.getConcurrencyCheckTask());
        thread.start();

        Utils.sleep(1000);
        System.out.println("Set stop to true in main!");
        demo.stop = true;
//        demo.stop.set(true);
        System.out.println("Exit main.");

    }

    ConcurrencyCheckTask getConcurrencyCheckTask() {
        return new ConcurrencyCheckTask();
    }

    private class ConcurrencyCheckTask implements Runnable {
        @Override
        public void run() {
            System.out.println("ConcurrencyCheckTask started!");
            // 如果主线中stop的值可见，则循环会退出。
            // 在我的开发机上，几乎必现循环不退出！（简单安全的解法：在running属性上加上volatile）
            while (!stop) {
//            while (!stop.get()) {
            }
            System.out.println("ConcurrencyCheckTask stopped!");
        }
    }
}
