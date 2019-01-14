package org.wahlzeit.utils;

import java.lang.annotation.Repeatable;

@Repeatable(DesignPattern.class)
public @interface PatternInstance {
    String name();
    String[] participants();
}
