package io.github.bineq;

import io.github.bineq.datasets.EQRecord;
import io.github.bineq.datasets.EQ;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class DataSetParserTests {

    static Path DATA = Path.of(DataSetParserTests.class.getResource("/testdataset/").getFile());

    @Test
    public void testEQ() throws IOException {
        Assumptions.assumeTrue(Files.exists(DATA));
        Stream<EQRecord> records = new EQ().records(DATA);
        Optional<EQRecord> firstRecord  = records.findFirst();
        assertTrue(firstRecord.isPresent());
        EQRecord record = firstRecord.get();
        assertNotNull(record);

        assertEquals("jars/EQ/openjdk-8.0.372/bcel-6.4.0-tests.jar",record.getContainer_1());
        assertEquals("jars/EQ/openjdk-9.0.1/bcel-6.4.0-tests.jar",record.getContainer_2());
        assertEquals("/org/apache/bcel/AbstractTestCase.class",record.getClass_1());
        assertEquals("/org/apache/bcel/AbstractTestCase.class",record.getClass_2());
        assertEquals("openjdk",record.getCompiler_name_1());
        assertEquals("openjdk",record.getCompiler_name_2());
        assertEquals("8",record.getCompiler_major_version_1());
        assertEquals("9",record.getCompiler_major_version_2());
        assertEquals("0",record.getCompiler_minor_version_1());
        assertEquals("0",record.getCompiler_minor_version_2());
        assertEquals("372",record.getCompiler_patch_version_1());
        assertEquals("1",record.getCompiler_patch_version_2());


        assertEquals("-",record.getCompiler_extra_config_1());
        assertEquals("-",record.getCompiler_extra_config_2());
        assertEquals("commons-bcel",record.getProject_name());
        assertEquals("6",record.getProject_major_version());
        assertEquals("4",record.getProject_minor_version());
        assertEquals("0",record.getProject_patch_version());
        assertEquals("foo1",record.getGenerated_by_1());
        assertEquals("foo2",record.getGenerated_by_2());
        assertEquals("false",record.getBytecode_jep181_1());
        assertEquals("true",record.getBytecode_jep181_2());
        assertEquals("false",record.getBytecode_jep280_1());
        assertEquals("true",record.getBytecode_jep280_2());
        assertEquals("test",record.getScope_1());
        assertEquals("test",record.getScope_2());
        assertEquals("0",record.getN_anon_inner_classes_1());
        assertEquals("0",record.getN_anon_inner_classes_2());
    }
}
