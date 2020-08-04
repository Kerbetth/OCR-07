package com.nnk.springboot.unit.password;

import com.nnk.springboot.userconfig.PasswordConstraint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
