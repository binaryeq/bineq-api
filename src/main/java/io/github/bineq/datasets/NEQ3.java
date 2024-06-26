package io.github.bineq.datasets;

import java.nio.file.Path;

/**
 * NEQ3 dataset, with some additional attributes.
 * @author jens dietrich
 */
public class NEQ3 extends DataSet<Record> {

    @Override
    public String name() {
        return "NEQ3";
    }

    @Override
    public boolean isEquivalenceOracle() {
        return false;
    }

    @Override
    public void initSpecial(Record record,String[] data, int offset) {
        // nothing to do here
    }

    @Override
    public Record create() {
        return new Record();
    }

    @Override
    public Path getRecordPath(Path root) {
        return root.resolve("NEQ3.tsv");
    }

    @Override
    public Path getSchemaPath(Path root) {
        return root.resolve("NEQ3-schema.tsv");
    }
}
