package com.yyjj.validator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Supplier;

import org.springframework.util.StringUtils;

public class Validator {

    public static final String INVALID_PARAM = "invalid param";

    private static final String REQUIRE_MESSAGE = "不能为空";

    private Validator() {
    }

    private static class ValidatorInstance {
        private static final Validator INSTANCE = new Validator();
    }

    public static Validator getInstance() {
        return ValidatorInstance.INSTANCE;
    }

    public Validator require(Object checkObj, String objName) {

        if (null == checkObj) {
            throw new ValidationException(new StringBuilder(objName).append(REQUIRE_MESSAGE).toString());
        }
        if (checkObj instanceof String) {
            if (StringUtils.isEmpty(checkObj)) {
                throw new ValidationException(new StringBuilder(objName).append(REQUIRE_MESSAGE).toString());
            }
        }
        return this;
    }

    public Validator rejectIfTrue(Supplier<Boolean> checkObj, String errmsg) {
        if (checkObj.get()) {
            throw new ValidationException(errmsg);
        }
        return this;
    }

    public Validator rejectIfFalse(Supplier<Boolean> checkObj, String errmsg) {
        if (!checkObj.get()) {
            throw new ValidationException(errmsg);
        }
        return this;
    }

    public Validator notNull(Object checkObj, String errmsg) {
        if (null == checkObj) {
            throw new ValidationException(errmsg);
        }
        return this;
    }

    public Validator notEmpty(Collection<?> checkObj, String errmsg) {
        if (checkObj == null || checkObj.isEmpty()) {
            throw new ValidationException(errmsg);
        }
        return this;
    }

    public Validator gtZero(BigDecimal num, String errmsg) {
        return gt(num, BigDecimal.ZERO, errmsg);
    }

    public Validator gteZero(BigDecimal num, String errmsg) {
        return gte(num, BigDecimal.ZERO, errmsg);
    }

    public Validator ltZero(BigDecimal num, String errmsg) {
        return lt(num, BigDecimal.ZERO, errmsg);
    }

    public Validator lteZero(BigDecimal num, String errmsg) {
        return lte(num, BigDecimal.ZERO, errmsg);
    }

    public Validator gt(BigDecimal num1, BigDecimal num2, String errmsg) {
        if (null == num1 || null == num2 || num1.compareTo(num2) <= 0) {
            throw new ValidationException(errmsg);
        }
        return this;
    }

    public Validator gte(BigDecimal num1, BigDecimal num2, String errmsg) {
        if (null == num1 || null == num2 || num1.compareTo(num2) < 0) {
            throw new ValidationException(errmsg);
        }
        return this;
    }

    public Validator lt(BigDecimal num1, BigDecimal num2, String errmsg) {
        if (null == num1 || null == num2 || num1.compareTo(num2) >= 0) {
            throw new ValidationException(errmsg);
        }
        return this;
    }

    public Validator lte(BigDecimal num1, BigDecimal num2, String errmsg) {
        if (null == num1 || null == num2 || num1.compareTo(num2) > 0) {
            throw new ValidationException(errmsg);
        }
        return this;
    }

}
