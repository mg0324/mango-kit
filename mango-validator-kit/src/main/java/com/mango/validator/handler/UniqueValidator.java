package com.mango.validator.handler;

import com.mango.validator.util.MyValidator;
import com.mango.validator.util.ValidContext;

import java.util.Map;

/**
 * Created by meigang on 17/10/21.
 */
public abstract class UniqueValidator extends MyValidator {
    /**
     * 抽象方法，@Unique验证器需要的
     * @param mapperMethod 条件参数
     * @param sqlparam 验证参数
     * @param vc 验证上下文
     * @param msg 错误信息
     * @return
     */
    public abstract boolean validate(UniqueValidator validator,String mapperMethod, Map<String, Object> sqlparam, ValidContext vc, String msg);

}
