package io.github.bineq.datasets;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * EQ dataset, with some additional attributes.
 * @author jens dietrich
 */
public class EQ extends DataSet<EQRecord> {

    private final static Logger LOG = LoggerFactory.getLogger(EQ.class);

    @Override
    public String name() {
        return "EQ";
    }

    @Override
    public boolean isEquivalenceOracle() {
        return true;
    }

    @Override
    public void initSpecial(EQRecord record,String[] data, int offset) {
        int i = offset;
        record.setCompiler_name_1(data[i++]);
        record.setCompiler_name_2(data[i++]);
        record.setCompiler_major_version_1(data[i++]);
        record.setCompiler_major_version_2(data[i++]);
        record.setCompiler_minor_version_1(data[i++]);
        record.setCompiler_minor_version_2(data[i++]);
        record.setCompiler_patch_version_1(data[i++]);
        record.setCompiler_patch_version_2(data[i++]);

        record.setCompiler_extra_config_1(data[i++]);
        record.setCompiler_extra_config_2(data[i++]);

        record.setProject_name(data[i++]);
        record.setProject_major_version(data[i++]);
        record.setProject_minor_version(data[i++]);
        record.setProject_patch_version(data[i++]);
        record.setGenerated_by_1(data[i++]);
        record.setGenerated_by_2(data[i++]);
        record.setBytecode_jep181_1(data[i++]);
        record.setBytecode_jep181_2(data[i++]);
        record.setBytecode_jep280_1(data[i++]);
        record.setBytecode_jep280_2(data[i++]);
        record.setScope_1(data[i++]);
        record.setScope_2(data[i++]);
        record.setN_anon_inner_classes_1(data[i++]);
        record.setN_anon_inner_classes_2(data[i++]);
    }

    @Override
    public EQRecord create() {
        return new EQRecord();
    }

    @Override
    public Path getRecordPath(Path root) {
        return root.resolve("EQ.tsv");
    }

    @Override
    public Path getSchemaPath(Path root) {
        return root.resolve("EQ-schema.tsv");
    }

    @Override
    public Stream<EQRecord> records(Path root) throws IOException {
        return super.records(root)
            .flatMap(record -> expandAnonInnerClasses(root,record).stream());
    }

    /**
     * Given a record, create new records for the anonymous inner classes it contains.
     * @param root
     * @param record
     * @return
     */
    List<? extends EQRecord> expandAnonInnerClasses(Path root,EQRecord record) {
        int anonInnerClassCount1 = 0;
        int anonInnerClassCount2 = 0;
        try {
            anonInnerClassCount1 = Integer.parseInt(record.getN_anon_inner_classes_1().trim());
        }
        catch (NumberFormatException x) {
            LOG.error("cannot parse anon inner class count " + record.getN_anon_inner_classes_1(),x);
        }
        try {
            anonInnerClassCount2 = Integer.parseInt(record.getN_anon_inner_classes_2().trim());
        }
        catch (NumberFormatException x) {
            LOG.error("cannot parse anon inner class count " + record.getN_anon_inner_classes_2(),x);
        }

        // TODO: how to handle this ..
        // assert anonInnerClassCount1==anonInnerClassCount2;

        int anonInnerClassCount = Math.min(anonInnerClassCount1,anonInnerClassCount2);
        List<EQRecord> anonInnerClasses = new ArrayList<>(anonInnerClassCount1+1);

        anonInnerClasses.add(SerializationUtils.clone(record)); // defensive, should be redundant
        for (int i=1;i<=anonInnerClassCount;i++) {
            EQRecord clone = SerializationUtils.clone(record);
            clone.setClass_1(record.getClass_1().replace(".class","$"+i+".class"));
            clone.setClass_2(record.getClass_2().replace(".class","$"+i+".class"));
            clone.setN_anon_inner_classes_1("0"); // TODO: check how we deal with nested anon inner classes
            clone.setN_anon_inner_classes_2("0");
            if (validate(root,record)) { // extra check whether bytecode actually exists
                anonInnerClasses.add(clone);
            }
        }
        return anonInnerClasses;
    }


}
