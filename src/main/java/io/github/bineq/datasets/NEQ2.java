package io.github.bineq.datasets;

import java.nio.file.Path;

/**
 * NEQ2 dataset, with some additional attributes.
 * @author jens dietrich
 */
public class NEQ2 extends DataSet<NEQ2Record> {


//    private String description = null;
//    private int line = 0;
//    private String method_descriptor = null;
//    private String method_name = null;
//    private String mutator = null;

    @Override
    public String name() {
        return "NEQ2";
    }

    @Override
    public boolean isEquivalenceOracle() {
        return false;
    }

    @Override
    public void initSpecial(NEQ2Record record,String[] data, int offset) {
        int i = offset;
        record.setDescription(data[i++]);
        record.setLine(Integer.parseInt(data[i++]));
        record.setMethod_descriptor(data[i++]);
        record.setMethod_name(data[i++]);
        record.setMutator(data[i++]);
    }

    @Override
    public NEQ2Record create() {
        return new NEQ2Record();
    }

    @Override
    public Path getRecordPath(Path root) {
        return root.resolve("NEQ2.tsv");
    }

    @Override
    public Path getSchemaPath(Path root) {
        return root.resolve("NEQ2-schema.tsv");
    }
}
