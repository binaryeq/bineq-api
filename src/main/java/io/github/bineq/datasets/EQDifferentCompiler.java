package io.github.bineq.datasets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * EQ dataset, both records are compiled with a different compiler or compiler configuration.
 * @author jens dietrich
 */
public final class EQDifferentCompiler extends EQ {

    static final Predicate<EQRecord> FILTER = eqRecord ->
        !Objects.equals(eqRecord.getCompiler_name_1(),eqRecord.getCompiler_name_2());

    @Override
    public String name() {
        return "EQ-DiffComp";
    }

    @Override
    public Stream<EQRecord> records(Path root) throws IOException {
        return super.records(root).filter(FILTER);
    }

}
