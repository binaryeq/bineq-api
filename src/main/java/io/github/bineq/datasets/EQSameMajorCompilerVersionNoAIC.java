package io.github.bineq.datasets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * EQ dataset, with some additional attributes.
 * Only records where both classes have been compiled with the same major compiler version are considered.
 * Anonymous inner classes are ignored.
 * @author jens dietrich
 */
public final class EQSameMajorCompilerVersionNoAIC extends EQ {

    static final Predicate<EQRecord> FILTER = EQSameMajorCompilerVersion.FILTER.and(EQNoAIC.FILTER);

    @Override
    public String name() {
        return "EQ-SameMjCompVer-no-aic";
    }

    @Override
    public Stream<EQRecord> readRecords(Path root) throws IOException {
        return super.readRecords(root).filter(FILTER);
    }
}
