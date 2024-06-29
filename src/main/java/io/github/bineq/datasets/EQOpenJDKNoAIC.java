package io.github.bineq.datasets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * EQ dataset, both records are compiled with OpenJDK. Anonymous inner classes are ignored.
 * @author jens dietrich
 */
public final class EQOpenJDKNoAIC extends EQ {

    static final Predicate<EQRecord> FILTER = EQOpenJDK.FILTER.and(EQNoAIC.FILTER);

    @Override
    public String name() {
        return "EQ-OpenJDK-no-aic";
    }

    @Override
    public Stream<EQRecord> records(Path root) throws IOException {
        return super.records(root).filter(FILTER);
    }
}
