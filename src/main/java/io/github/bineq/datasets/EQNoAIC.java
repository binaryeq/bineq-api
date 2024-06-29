package io.github.bineq.datasets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * EQ dataset, ignoring anonymous inner classes.
 * @author jens dietrich
 */
public final class EQNoAIC extends EQ {

    static final Predicate<Record> FILTER = record -> {
        assert record.isClass_1AnonInnerClass()==record.isClass_2AnonInnerClass();
        return !record.isClass_1AnonInnerClass() && !record.isClass_2AnonInnerClass();
    };

    @Override
    public String name() {
        return "EQ-no-aic";
    }

    @Override
    public Stream<EQRecord> records(Path root) throws IOException {
        return super.records(root).filter(FILTER);
    }
}
