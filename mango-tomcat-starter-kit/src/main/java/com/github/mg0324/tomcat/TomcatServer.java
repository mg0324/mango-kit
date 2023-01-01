package com.github.mg0324.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import javax.servlet.ServletException;
import java.io.File;

/**
 * servlet3.1 tomcat8.5 启动器
 */
public abstract class TomcatServer {
    protected Tomcat tomcat;

    /**
     * 初始化tomcat服务器
     * @param port 端口
     * @throws ServletException
     */
    public void init(int port) throws ServletException {
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setHostname("0.0.0.0");
        tomcat.setBaseDir(".");
        // tomcat 添加webapp
        StandardContext context = (StandardContext) tomcat.addWebapp(
                "/", System.getProperty("user.dir") + File.separator +
                        "src/main/webapp");
        // 设置热加载
        context.setReloadable(true);
        // 上下文监听器
        context.addLifecycleListener(new AprLifecycleListener());

        // 开始加载class中的servlet，filter，listener等资源
        String classDir = System.getProperty("user.dir") + File.separator + "target/classes";
        // 设置web资源
        WebResourceRoot root = new StandardRoot(context);
        // 添加目录资源集
        root.addPreResources(new DirResourceSet(root, "/WEB-INF/classes", classDir,"/"));
        // 设置到上下文
        context.setResources(root);
    }

    /**
     * 启动tomcat服务器
     * @throws LifecycleException
     */
    public void start() throws LifecycleException {
        tomcat.start();
        tomcat.getServer().await();
    }

    /**
     * 停止tomcat服务器
     * @throws LifecycleException
     */
    public void stop() throws LifecycleException {
        tomcat.stop();
    }

    /**
     * 获取Tomcat服务器实例
     * @return
     */
    public Tomcat getTomcat(){
        return tomcat;
    }
}
