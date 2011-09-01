package info.simplecloud.core.annotations;

import info.simplecloud.core.types.DummyType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Complex {
    Class<?> xmlType();
    
    Class<?> xmlDoc() default DummyType.class;
}
