package com.nnk.springboot.unit.password;

import com.nnk.springboot.config.PasswordConstraint;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.UserRepository;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PasswordEncodeTest {

    private PasswordConstraint passwordConstraint = new PasswordConstraint();
    @Mock
    private ConstraintValidatorContext context;
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder = new ConstraintValidatorContext.ConstraintViolationBuilder() {
        @Override
        public NodeBuilderDefinedContext addNode(String s) {
            return null;
        }

        @Override
        public NodeBuilderCustomizableContext addPropertyNode(String s) {
            return null;
        }

        @Override
        public LeafNodeBuilderCustomizableContext addBeanNode() {
            return null;
        }

        @Override
        public ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String s, Class<?> aClass, Integer integer) {
            return null;
        }

        @Override
        public NodeBuilderDefinedContext addParameterNode(int i) {
            return null;
        }

        @Override
        public ConstraintValidatorContext addConstraintViolation() {
            return null;
        }
    };

    @BeforeEach
    public void setup() {
       doNothing().when(context).disableDefaultConstraintViolation();
       when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
    }

    @Test
    public void testNoNumberPassword() {
      Boolean result=  passwordConstraint.isValid("veryWeakTest,", context);
        assertThat(result).isFalse();
    }
    @Test
    public void testNoUpperCasePassword() {
        Boolean result=  passwordConstraint.isValid("veryweaktest,0", context);
        assertThat(result).isFalse();
    }
    @Test
    public void testShortPassword() {
        Boolean result=  passwordConstraint.isValid("Short,0", context);
        assertThat(result).isFalse();
    }
    @Test
    public void testNoSymbolPassword() {
        Boolean result=  passwordConstraint.isValid("veryWeakTest0", context);
        assertThat(result).isFalse();
    }
    @Test
    public void testGoodPassword() {
        Boolean result=  passwordConstraint.isValid("veryGoodTest0!", context);
        assertThat(result).isTrue();
    }
}
