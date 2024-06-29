package io.github.bineq.datasets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * EQ dataset, both records are compiled with OpenJDK .
 * @author jens dietrich
 */
public final class EQOpenJDK extends EQ {

    static final Predicate<EQRecord> FILTER = eqRecord ->
        Objects.equals("openjdk",eqRecord.getCompiler_name_1()) &&
            Objects.equals("openjdk",eqRecord.getCompiler_name_2());

    @Override
    public String name() {
        return "EQ-OpenJDK";
    }

    @Override
    public Stream<EQRecord> records(Path root) throws IOException {
        return super.records(root).filter(FILTER);
    }

}
