package com.github.mg0324.fast.util;

import com.github.mg0324.fast.bean.FastResult;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class FastKit {

    private ListeningExecutorService guavaExecutor;

    public static FastResult success(String id){
        return successToData(id,null,null);
    }

    public static FastResult success(String id,String message){
        return successToData(id,null,message);
    }

    public static FastResult successToData(String id,Map data,String message){
        FastResult fastResult = new FastResult();
        fastResult.setId(id);
        fastResult.setState(1);
        fastResult.setData(data);
        fastResult.setMessage(message);
        return fastResult;
    }

    public static FastResult error(String id){
        return errorToData(id,null,null);
    }

    public static FastResult error(String id,String message){
        return errorToData(id,null,message);
    }

    public static FastResult errorToData(String id,Map data,String message){
        FastResult fastResult = new FastResult();
        fastResult.setId(id);
        fastResult.setState(0);
        fastResult.setData(data);
        fastResult.setMessage(message);
        return fastResult;
    }

    public static Object getProxy(Object obj) {
        FastProxy fastProxy = new FastProxy();
        return fastProxy.getInstance(obj);
    }

    public static Object getProxy(Object obj,int threadNum) {
        FastProxy fastProxy = new FastProxy();
        return fastProxy.getInstance(obj,threadNum);
    }

    /**
     * 初始化线程池,可以监听返回值的
     * @param max 最大线程数
     * @return
     */
    public ListeningExecutorService initGuavaExecutor(int max) {
        guavaExecutor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(max));
        return guavaExecutor;
    }

    /**
     * 关闭线程池，释放资源
     */
    public void shutdownExecutor(){
        if(null != guavaExecutor && !guavaExecutor.isShutdown()){
            guavaExecutor.shutdown();
        }
    }

    /**
     * 提交任务到线程池
     * @param listListenableFuture future集合用于调用.get()获取结果
     * @param callable 提交的callable任务
     */
    public void submit(List<ListenableFuture<FastResult>> listListenableFuture, Callable<FastResult> callable){
        ListenableFuture future = guavaExecutor.submit(callable);
        listListenableFuture.add(future);
    }

    /**
     * 获取任务执行结果
     * @param listListenableFuture 并行futrue集合
     * @return 返回结果
     */
    public Map<String,FastResult> getResult(List<ListenableFuture<FastResult>> listListenableFuture){
        Map<String,FastResult> resultMap = Maps.newHashMap();
        for(ListenableFuture<FastResult> listenableFuture : listListenableFuture){
            try {
                FastResult result = listenableFuture.get();
                resultMap.put(result.getId(),result);
            } catch (Exception e) {
                int hashCode = listenableFuture.hashCode();
                resultMap.put(hashCode+"", FastKit.error(hashCode+"",e.getMessage()));
            }
        }
        return resultMap;
    }
}
