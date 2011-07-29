package info.simplecloud.core.ng;

import info.simplecloud.core.ng.comparators.BooleanComparator;
import info.simplecloud.core.ng.comparators.ComplexComparator;
import info.simplecloud.core.ng.comparators.PluralComparator;
import info.simplecloud.core.ng.comparators.StringComarator;
import info.simplecloud.core.ng.handlers.BooleanHandler;
import info.simplecloud.core.ng.handlers.ComplexHandler;
import info.simplecloud.core.ng.handlers.PluralHandler;
import info.simplecloud.core.ng.handlers.StringHandler;

import java.util.List;

public class Types {
    public static final ComplexComparator COMPARATOR_ASCENDING_COMPLEX         = new ComplexComparator("test", true, String.class);
    public static final ComplexComparator COMPARATOR_DESCENDING_COMPLEX        = new ComplexComparator("test", false, String.class);
    public static final PluralComparator  COMPARATOR_ASCENDING_PLURAL_SIMPLE   = new PluralComparator(StringComarator.ASCENDING);
    public static final PluralComparator  COMPARATOR_DESCENDING_PLURAL_SIMPLE  = new PluralComparator(StringComarator.DESCENDING);
    public static final PluralComparator  COMPARATOR_ASCENDING_PLURAL_COMPLEX  = new PluralComparator(COMPARATOR_ASCENDING_COMPLEX);
    public static final PluralComparator  COMPARATOR_DESCENDING_PLURAL_COMPLEX = new PluralComparator(COMPARATOR_DESCENDING_COMPLEX);

    public static final MetaData          META_SIMPLE_STRING                   = new MetaData(String.class, StringHandler.INSTANCE, null,
                                                                                       "s", StringComarator.ASCENDING,
                                                                                       StringComarator.DESCENDING);
    public static final MetaData          META_SIMPLE_BOOLEAN                  = new MetaData(Boolean.class, BooleanHandler.INSTANCE, null,
                                                                                       "s", BooleanComparator.ASCENDING,
                                                                                       BooleanComparator.DESCENDING);
    public static final MetaData          META_COMPLEX                         = new MetaData(ComplexType2.class, ComplexHandler.INSTANCE,
                                                                                       null, "c", COMPARATOR_ASCENDING_COMPLEX,
                                                                                       COMPARATOR_DESCENDING_COMPLEX);
    public static final MetaData          META_PLURAL_SIMPLE                   = new MetaData(List.class, PluralHandler.INSTANCE,
                                                                                       META_SIMPLE_STRING, "ps",
                                                                                       COMPARATOR_ASCENDING_PLURAL_SIMPLE,
                                                                                       COMPARATOR_DESCENDING_PLURAL_SIMPLE);
    public static final MetaData          META_PLURAL_COMPLEX                  = new MetaData(List.class, PluralHandler.INSTANCE,
                                                                                       META_COMPLEX, "pc",
                                                                                       COMPARATOR_ASCENDING_PLURAL_COMPLEX,
                                                                                       COMPARATOR_DESCENDING_PLURAL_COMPLEX);
}
