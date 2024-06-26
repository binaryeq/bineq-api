package io.github.bineq.datasets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * EQ dataset, both records are compiled with ecj version 3* (openjdk11)
 * @author jens dietrich
 */
public final class EQECJ3 extends EQ {

    @Override
    public String name() {
        return "EQ-EJC3";
    }

    @Override
    public Stream<EQRecord> readRecords(Path root) throws IOException {
        return super.readRecords(root).filter(eqRecord ->
            Objects.equals("ecj",eqRecord.getCompiler_name_1()) &&
            Objects.equals("ecj",eqRecord.getCompiler_name_2()) &&
            Objects.equals("3",eqRecord.getCompiler_major_version_1()) &&
            Objects.equals("3",eqRecord.getCompiler_major_version_2())
        );
    }
}
