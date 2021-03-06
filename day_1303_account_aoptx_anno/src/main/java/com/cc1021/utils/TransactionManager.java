package com.cc1021.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  和事务管理相关的工具类，包含了开启事务，提交事务，回滚事务，释放连接
 */
@Component("txManager")
@Aspect
public class TransactionManager {

    @Autowired
    private ConnectionUtils connectionUtils;

    @Pointcut("execution(* com.cc1021.service.impl.*.*(..))")
    private void ptl(){}

    /**
     *  开启事务
     */
    //@Before("ptl()")
    public void beginTransaction(){
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  提交事务
     */
    //@AfterReturning("ptl()")
    public void commit(){
        try {
            connectionUtils.getThreadConnection().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  回滚事务
     */
    //@AfterThrowing("ptl()")
    public void rollback(){
        try {
            connectionUtils.getThreadConnection().rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  开启事务
     */
    //@After("ptl()")
    public void release(){
        try {
            connectionUtils.getThreadConnection().close();//还回连接池中
            connectionUtils.removeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Around("ptl()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) {
        Object rtValue = null;
        try {
            //1、获取参数
            Object[] args = pjp.getArgs();
            //2、开启事务
            this.beginTransaction();
            //3、执行方法
            pjp.proceed(args);
            //4、提交事务
            this.commit();

            //返回结果
            return rtValue;

        } catch (Throwable e) {
            //5、回滚事务
            this.rollback();
            throw new RuntimeException(e);
        } finally {
            //6、释放资源
            this.release();
        }
    }
}
