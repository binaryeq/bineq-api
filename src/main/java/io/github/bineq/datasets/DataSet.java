package io.github.bineq.datasets;

import io.github.bineq.Bytecode;
import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Dataset definitions.
 * @author jens dietrich
 */
public abstract class DataSet<RT extends Record> implements Serializable {

    public static final String SEP = "\t";

    public abstract Path getRecordPath(Path root);
    public abstract Path getSchemaPath(Path root);

    /**
     * Returns true if for those records equality is expected, and false otherwise (i.e. non-equality is expected).
     * @return
     */
    public abstract boolean isEquivalenceOracle();

    public abstract String name();

    // initialise record type specific records
    public abstract void initSpecial(RT record,String[] data, int offset);
    // instantiate a new record
    public abstract RT create();

    public Stream<RT> records(Path root) throws IOException {
        Path path = getRecordPath(root);
        LineNumberReader reader = new LineNumberReader(new FileReader(path.toFile()));
        return reader.lines().skip(1)
            .map(line -> load(line,reader.getLineNumber()))
            .filter(record -> validate(root,record));
    }

    // parses lines and consumes the record stream, so a bit more expensive than necessary -- should be cached
    public int size(Path root) throws IOException {
        return (int) records(root).count();
    }

    RT load(String line,int lineNumber) {
        String[] tokens = line.split(SEP);
        RT record = create();

        // provenance fields
        record.setDataset(this);
        record.setLineNumber(lineNumber);

        // init fields in super
        record.setContainer_1(tokens[0]);
        record.setContainer_2(tokens[1]);
        record.setClass_1(tokens[2]);
        record.setClass_2(tokens[3]);
        initSpecial(record,tokens,4);
        return record;
    }

    public static boolean validate(Path root,Record record)  {
        return validate(root,record,false);
    }

    public static boolean validate(Path root,Record record,boolean allowIdenticalBytecodes)  {
        Bytecode bytecode1 = new Bytecode(root,root.resolve(record.getContainer_1()),record.getClass_1());
        Bytecode bytecode2 = new Bytecode(root,root.resolve(record.getContainer_2()),record.getClass_2());
        if (!bytecode1.validate()) {
            return false;
        }
        if (!bytecode2.validate()) {
            return false;
        }
        if (allowIdenticalBytecodes) {
            return true;
        }
        else {
            try {
                byte[] bc1 = bytecode1.getBytecode();
                byte[] bc2 = bytecode2.getBytecode();
                // bytecode must be different as we exclude reflexive records from dataset
                return !Arrays.equals(bc1,bc2);
            }
            catch (IOException x) {
                return false;
            }
        }
    }

}
