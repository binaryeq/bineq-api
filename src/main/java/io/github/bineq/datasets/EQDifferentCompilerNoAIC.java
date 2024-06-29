package io.github.bineq.datasets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * EQ dataset, both records are compiled with a different compiler or compiler configuration.
 * Anonymous inner classes are ignored.
 * @author jens dietrich
 */
public final class EQDifferentCompilerNoAIC extends EQ {

    static final Predicate<EQRecord> FILTER = EQDifferentCompiler.FILTER.and(EQNoAIC.FILTER);

    @Override
    public String name() {
        return "EQ-DiffComp-no-aic";
    }

    @Override
    public Stream<EQRecord> records(Path root) throws IOException {
        return super.records(root).filter(FILTER);
    }

}
