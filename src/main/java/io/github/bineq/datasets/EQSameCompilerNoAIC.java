package io.github.bineq.datasets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * EQ dataset, both records are compiled with the same compiler .
 * @author jens dietrich
 */
public final class EQSameCompilerNoAIC extends EQ {

    static final Predicate<EQRecord> FILTER = EQSameCompiler.FILTER.and(EQNoAIC.FILTER);

    @Override
    public String name() {
        return "EQ-SameComp-no-aic";
    }

    @Override
    public Stream<EQRecord> records(Path root) throws IOException {
        return super.records(root).filter(FILTER);
    }

}
