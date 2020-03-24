package com.github.mg0324.fast.inter;

import com.github.mg0324.fast.bean.FastResult;
import com.github.mg0324.fast.util.FastKit;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 多线程异步并行加速接口
 */
public interface IFast extends Callable<FastResult> {
    /**
     * 需要加速的业务逻辑，提交callable到listenableFuture集合中
     * fastKit.submit(list,new Busi());
     * @param fastKit 加速工具
     * @param list 监听的异步future集合
     * @return 返回加速后得到的执行结果
     */
    Object invoke(FastKit fastKit, List<ListenableFuture<FastResult>> list);
}
