package guice.bind;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@BindingAnnotation
@Target(FIELD)
@Retention(RUNTIME)
public @interface ConsoleLog { }
