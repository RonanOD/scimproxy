package info.simplecloud.core.annotations;

import info.simplecloud.core.types.DummyType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Attribute {
    String name();

    Class<?> handler();

    Class<?> type() default DummyType.class;

    Class<?> internalHandler() default DummyType.class;

    Class<?> internalType() default DummyType.class;
    
    String internalName() default "dummy";
    
    Class<?> xmlDoc() default DummyType.class;
}
