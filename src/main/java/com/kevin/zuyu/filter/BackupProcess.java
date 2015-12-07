package com.kevin.zuyu.filter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.kevin.zuyu.service.MysqlManager;

public class BackupProcess implements Filter {
  public MysqlManager dbManager;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    try {
      ApplicationContext context =
          (ApplicationContext) filterConfig.getServletContext().getAttribute(
              WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
      dbManager = context.getAutowireCapableBeanFactory().getBean(MysqlManager.class);
      MyTimerTask task = new MyTimerTask(dbManager);
      // 启动系统10秒钟后开始执行备份
      // 后面每个小时执行一次备份
      new MyTimer().schedule(task, 10000, 1000 * 60 * 60);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }

  public static class MyTimerTask extends TimerTask {
    MysqlManager dbManager;

    public MyTimerTask(MysqlManager manager) {
      this.dbManager = manager;
    }

    @Override
    public void run() {
      try {
        dbManager.backDB();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  public static class MyTimer extends Timer {
    /**
     * @param task:实现的定时任务
     * @param delay:项目启动制定时间后开始执行任务(单位毫秒)
     * @param period:执行定时任务的间隔时间(单位毫秒)
     *        <p>
     *        如果Task执行时间过长；会导致period执行任务延期
     *        </p>
     */
    public void schedule(TimerTask task, long delay, long period) {
      super.schedule(task, delay, period);
    }
  }
}
