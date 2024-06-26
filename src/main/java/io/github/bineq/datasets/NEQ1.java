package io.github.bineq.datasets;

import java.nio.file.Path;

/**
 * NEQ1 dataset, with some additional attributes.
 * @author jens dietrich
 */
public class NEQ1 extends DataSet<NEQ1Record> {

    @Override
    public String name() {
        return "NEQ1";
    }

    @Override
    public boolean isEquivalenceOracle() {
        return false;
    }

    @Override
    public void initSpecial(NEQ1Record record,String[] data, int offset) {
        int i = 26;
        record.setSource_compatible(Boolean.valueOf(data[i++]));
        record.setBinary_compatible(Boolean.valueOf(data[i++]));
        record.setSemantic_compatible(Boolean.valueOf(data[i]));
    }

    @Override
    public NEQ1Record create() {
        return new NEQ1Record();
    }

    @Override
    public Path getRecordPath(Path root) {
        return root.resolve("NEQ1.tsv");
    }

    @Override
    public Path getSchemaPath(Path root) {
        return root.resolve("NEQ1-schema.tsv");
    }
}
