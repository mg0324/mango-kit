package com.mango.validator.util;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import com.baidu.unbiz.fluentvalidator.registry.impl.SimpleRegistry;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * Created by meigang on 17/9/15.
 */
public class ValidUtil{
    protected static final Logger logger = LoggerFactory.getLogger(ValidUtil.class);
    protected static Validator failFastValidator;
    protected static Validator failOverValidator;
    static{
        failFastValidator = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();
        failOverValidator = Validation.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();
    }
    private ValidUtil(){}

    public static ValidUtil newInstance(){
        return new ValidUtil();
    }
    /**
     * 验证hibernate 注解
     * @param obj 要校验的bean
     * @param groups
     * @return 校验结果对象
     */
    private static Result validHibernate(Object obj, Class<?>[] groups,boolean isFailFast){
        //获取hibernate validator实例，目前直接生成。后续可以spring bean配置注入
        Validator validator = failFastValidator;
        if(!isFailFast){
            validator = failOverValidator;
        }
        //验证hibernate-validator的注解
        Result ret = FluentValidator.checkAll()
                .setGroups(groups)
                .on(obj, new HibernateSupportedValidator().setHiberanteValidator(validator))
                .doValidate().result(toSimple());
        return ret;
    }

    public static Result validHibernateAnno(Object obj,boolean isFailFast){
        return validHibernate(obj,null,isFailFast);
    }

    public static Result validHibernateAnno(Object obj,Class<?>[] groups,boolean isFailFast){
        return validHibernate(obj,groups,isFailFast);
    }

    /**
     * 验证fluent 注解
     * @param obj 要校验的bean
     * @param groups
     * @return 校验结果对象
     */
    private static Result validFluent(Object obj, Class<?>[] groups,boolean isFailFast){
        Result ret = FluentValidator.checkAll()
                .setGroups(groups)
                .setIsFailFast(isFailFast)
                .configure(new SimpleRegistry())
                .on(obj)
                .doValidate().result(toSimple());
        return ret;
    }

    public static Result validFluentAnno(Object obj,boolean isFailFast){
        return validFluent(obj,null,isFailFast);
    }

    public static Result validFluentAnno(Object obj,Class<?>[] groups,boolean isFailFast){
        return validFluent(obj,groups,isFailFast);
    }

    /**
     * 验证自定义注解，用于实现业务场景校验
     * @param obj
     * @return
     */
    public static List<String> validMyAnno(Object obj,boolean isFailFast,MyValidator myValidator){
        return validMyAnno(obj,null,isFailFast,myValidator);
    }

    public static List<String> validMyAnno(Object obj,Class<?>[] groups,boolean isFailFast,MyValidator myValidator){
        MyValidUtil myValidUtil = new MyValidUtil();
        myValidUtil.validRefer(obj,groups,isFailFast,true);
        if(null != myValidator){
            myValidUtil.validUnique(obj,groups,isFailFast,true,myValidator);
        }
        return myValidUtil.getMsgList();
    }

    //不解析@FluentValid和@FluentValidate注解验证
    public static List<String> validateHibernateAndMy(Object obj,boolean isFailFast){
        return validateAllInWithOutFluent(obj,null,isFailFast,null);
    }

    public static List<String> validateAll(Object obj,boolean isFailFast,MyValidator myValidator){
        return validateAllIn(obj,null,isFailFast,myValidator);
    }

    public static List<String> validateAll(Object obj,boolean isFailFast){
        return validateAllIn(obj,null,isFailFast,null);
    }


    public static List<String> validateAll(Object obj,Class<?>[] groups,MyValidator myValidator){
        return validateAllIn(obj,groups,false,myValidator);
    }

    /**
     * 验证所有
     * @param obj
     * @param groups
     * @return
     */
    private static List<String> validateAllIn(Object obj,Class<?>[] groups,boolean isFailFast,MyValidator myValidator){
        Long start = System.currentTimeMillis();
        Result hiberResult = validHibernate(obj,groups,isFailFast);
        Long end = System.currentTimeMillis();
        if(hiberResult.isSuccess()){
            Long start1 = System.currentTimeMillis();
            Result fluentResult = validFluent(obj,groups,isFailFast);
            Long end1 = System.currentTimeMillis();
            if(fluentResult.isSuccess()){
                Long start2 = System.currentTimeMillis();
                List<String> msgList = validMyAnno(obj,groups,isFailFast,myValidator);
                Long end2 = System.currentTimeMillis();
                logger.info("hibernate validator耗时:"+((double)(end-start)/1000) + "s");
                logger.info("fluent validator耗时:"+((double)(end1-start1)/1000) + "s");
                logger.info("my validator耗时:"+((double)(end2-start2)/1000) + "s");
                logger.info("all validator总耗时:"+((double)(end2-start)/1000) + "s");
                return msgList;
            }else{
                logger.info("hibernate validator耗时:"+((double)(end-start)/1000) + "s");
                logger.info("fluent validator耗时:"+((double)(end1-start1)/1000) + "s");
                logger.info("all validator总耗时:"+((double)(end1-start)/1000) + "s");
                return fluentResult.getErrors();
            }
        }else{
            logger.info("hibernate validator耗时:"+((double)(end-start)/1000) + "s");
            logger.info("all validator总耗时:"+((double)(end-start)/1000) + "s");
            return hiberResult.getErrors();
        }
    }
    //不验证fluent的注解，只验证hibernate和Refer的，因为fluent的少用
    private static List<String> validateAllInWithOutFluent(Object obj,Class<?>[] groups,boolean isFailFast,MyValidator myValidator){
        Long start = System.currentTimeMillis();
        Result hiberResult = validHibernate(obj,groups,isFailFast);
        Long end = System.currentTimeMillis();
        if(hiberResult.isSuccess()){
            Long start1 = System.currentTimeMillis();
            List<String> msgList = validMyAnno(obj,groups,isFailFast,myValidator);
            Long end1 = System.currentTimeMillis();
            logger.info("hibernate validator耗时:"+((double)(end-start)/1000) + "s");
            logger.info("my validator耗时:"+((double)(end1-start1)/1000) + "s");
            logger.info("all validator总耗时:"+((double)(end1-start)/1000) + "s");
            return msgList;
        }else{
            logger.info("hibernate validator耗时:"+((double)(end-start)/1000) + "s");
            logger.info("all validator总耗时:"+((double)(end-start)/1000) + "s");
            return hiberResult.getErrors();
        }
    }

}
