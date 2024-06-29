package io.github.bineq.datasets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * EQ dataset, with some additional attributes.
 * Only records where both classes have been compiled with the same major compiler version are considered.
 * @author jens dietrich
 */
public final class EQSameMajorCompilerVersion extends EQ {

    static final Predicate<EQRecord> FILTER = eqRecord ->
        Objects.equals(eqRecord.getCompiler_name_1(),eqRecord.getCompiler_name_2()) &&
        Objects.equals(eqRecord.getCompiler_major_version_1(),eqRecord.getCompiler_major_version_2());

    @Override
    public String name() {
        return "EQ-SameMjCompVer";
    }

    @Override
    public Stream<EQRecord> records(Path root) throws IOException {
        return super.records(root).filter(FILTER);
    }
}
