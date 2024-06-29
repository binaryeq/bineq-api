package io.github.bineq;

import io.github.bineq.datasets.EQ;
import io.github.bineq.datasets.EQRecord;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DataSetParserAnonInnerClassExpansionTests {

    static Path DATA = Path.of(DataSetParserAnonInnerClassExpansionTests.class.getResource("/testdataset2/").getFile());

    @Test
    public void testRecordCount() throws IOException {
        Assumptions.assumeTrue(Files.exists(DATA));
        Stream<EQRecord> records = new EQ().records(DATA);
        int count = (int)records.count();
        assertEquals(3,count);
    }

    @Test
    public void testNonAnonInnerClassRecordCount() throws IOException {
        Assumptions.assumeTrue(Files.exists(DATA));
        Stream<EQRecord> records = new EQ().records(DATA);
        int count = (int)records
            .filter(record -> !record.isClass_1AnonInnerClass())
            .filter(record -> !record.isClass_2AnonInnerClass())
            .count();
        assertEquals(1,count);
    }

    @Test
    public void testAnonInnerClassRecordCount() throws IOException {
        Assumptions.assumeTrue(Files.exists(DATA));
        Stream<EQRecord> records = new EQ().records(DATA);
        int count = (int)records
            .filter(record -> record.isClass_1AnonInnerClass())
            .filter(record -> record.isClass_2AnonInnerClass())
            .count();
        assertEquals(2,count);
    }

    @Test
    public void testDetails() throws IOException {
        Assumptions.assumeTrue(Files.exists(DATA));
        List<EQRecord> records = new EQ().records(DATA).collect(Collectors.toList());
        EQRecord record1 = records.get(0);
        EQRecord record2 = records.get(1);
        EQRecord record3 = records.get(2);

        testCommonProperties(record1);
        assertEquals("/org/apache/bcel/AbstractTestCase.class",record1.getClass_1());
        assertEquals("/org/apache/bcel/AbstractTestCase.class",record1.getClass_2());
        assertEquals("2",record1.getN_anon_inner_classes_1());
        assertEquals("2",record1.getN_anon_inner_classes_2());
        assertFalse(record1.isClass_1InnerClass());
        assertFalse(record1.isClass_2InnerClass());
        assertFalse(record1.isClass_1AnonInnerClass());
        assertFalse(record1.isClass_2AnonInnerClass());

        testCommonProperties(record2);
        assertEquals("/org/apache/bcel/AbstractTestCase$1.class",record2.getClass_1());
        assertEquals("/org/apache/bcel/AbstractTestCase$1.class",record2.getClass_2());
        assertEquals("0",record2.getN_anon_inner_classes_1());
        assertEquals("0",record2.getN_anon_inner_classes_2());
        assertTrue(record2.isClass_1InnerClass());
        assertTrue(record2.isClass_2InnerClass());
        assertTrue(record2.isClass_1AnonInnerClass());
        assertTrue(record2.isClass_2AnonInnerClass());

        testCommonProperties(record3);
        assertEquals("/org/apache/bcel/AbstractTestCase$2.class",record3.getClass_1());
        assertEquals("/org/apache/bcel/AbstractTestCase$2.class",record3.getClass_2());
        assertEquals("0",record3.getN_anon_inner_classes_1());
        assertEquals("0",record3.getN_anon_inner_classes_2());
        assertTrue(record3.isClass_1InnerClass());
        assertTrue(record3.isClass_2InnerClass());
        assertTrue(record3.isClass_1AnonInnerClass());
        assertTrue(record3.isClass_2AnonInnerClass());

    }

    private void testCommonProperties(EQRecord record) {
        assertEquals("jars/EQ/openjdk-8.0.372/bcel-6.4.0-tests.jar",record.getContainer_1());
        assertEquals("jars/EQ/openjdk-9.0.1/bcel-6.4.0-tests.jar",record.getContainer_2());

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

    }
}
