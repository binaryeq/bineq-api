package io.github.bineq.datasets.analysis;

import com.google.common.base.Preconditions;
import io.github.bineq.ByteCodeAnalysis;
import io.github.bineq.Bytecode;
import io.github.bineq.datasets.EQ;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.bineq.CLICommons.*;

/**
 * Special script to analyse anonymous inner classes across builds.
 * @author jens dietrich
 */
public class AnalyseAnonymousInnerClasses {

    private final static Logger LOG = LoggerFactory.getLogger(AnalyseAnonymousInnerClasses.class);

    public static Option OUTPUT = Option.builder()
        .argName("out")
        .option("out")
        .hasArg()
        .required(true)
        .desc("the name of the file where to save details in tsv format")
        .build();

    public static void main (String[] args) throws IOException {


        Options options = new Options();
        options.addOption(DATASET_ROOT);
        options.addOption(OUTPUT);

        CommandLine cli = null;
        Path root = null;
        CommandLineParser parser = new DefaultParser();
        Path out = null;
        try {
            cli = parser.parse(options, args);
            String rootFolderName = cli.getOptionValue(DATASET_ROOT);
            Preconditions.checkNotNull(rootFolderName,"the value of -root must not be null");
            root = Path.of(rootFolderName);
            out = Path.of(cli.getOptionValue(OUTPUT));

        } catch (Exception e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -cp <path-to-built-jar> " + AnalyseAnonymousInnerClasses.class.getName(), options);
            e.printStackTrace();
            System.exit(1);
        }
        assert cli!=null;
        Path root2 = root ; // final for lambdas


        AtomicInteger aiclCounter = new AtomicInteger();
        AtomicInteger nonmatchingAiclCounter = new AtomicInteger();
        AtomicInteger nonmatchingAiclSameCompilerCounter = new AtomicInteger();

        List<String> nonMatchingrecordsTSVEncoded = new ArrayList<>();
        nonMatchingrecordsTSVEncoded.add(toTSV("jar","class","compiler-name-1","compiler-version-1","compiler-name-2","compiler-version-2","members-1","members-2"));

        // collect
        Set<String> anoInnerClasses = new HashSet<>();
        Map<String,List<Set<String>>> memberSummariesPerOuterClass = new HashMap<>();

        new EQ().records(root)
            .filter(record -> record.isClass_1AnonInnerClass() || record.isClass_2AnonInnerClass())
            .forEach(record -> {
                Bytecode bytecode1 = new Bytecode(root2, root2.resolve(record.getContainer_1()), record.getClass_1());
                Bytecode bytecode2 = new Bytecode(root2, root2.resolve(record.getContainer_2()), record.getClass_2());

                aiclCounter.incrementAndGet();

                boolean bytecode1IsValid = bytecode1.validate();
                boolean bytecode2IsValid = bytecode2.validate();
                if (bytecode1IsValid && bytecode2IsValid) {
                    Set<String> memberSummary1 = null;
                    Set<String> memberSummary2 = null;
                    anoInnerClasses.add(bytecode1.getJarFileOrFolder()+"!/"+bytecode1.getClazzFile());
                    anoInnerClasses.add(bytecode2.getJarFileOrFolder()+"!/"+bytecode2.getClazzFile());
                    try {
                        memberSummary1 = ByteCodeAnalysis.computeMemberSummary(bytecode1);
                        String outerClass = getOuterClass(bytecode1);
                        List<Set<String>> memberSummaries = memberSummariesPerOuterClass.computeIfAbsent(outerClass,k -> new ArrayList<>());
                        memberSummaries.add(memberSummary1);
                    }
                    catch (Exception x) {
                        LOG.error("Error computing member summary for " + bytecode1,x);
                    }
                    try {
                        memberSummary2 = ByteCodeAnalysis.computeMemberSummary(bytecode2);
                        String outerClass = getOuterClass(bytecode2);
                        List<Set<String>> memberSummaries = memberSummariesPerOuterClass.computeIfAbsent(outerClass,k -> new ArrayList<>());
                        memberSummaries.add(memberSummary2);                    }
                    catch (Exception x) {
                        LOG.error("Error computing member summary for " + bytecode2,x);
                    }

                    if (!memberSummary1.equals(memberSummary2)) {
                        nonmatchingAiclCounter.incrementAndGet();
                        if (record.getCompiler_name_1().equals(record.getCompiler_name_2())) {
                            nonmatchingAiclSameCompilerCounter.incrementAndGet();
                        }
                        nonMatchingrecordsTSVEncoded.add(toTSV(
                            record.getContainer_1(),
                            record.getClass_1(),
                            record.getCompiler_name_1(),
                            record.getCompiler_major_version_1()+"."+record.getCompiler_minor_version_1()+"."+record.getCompiler_patch_version_1(),
                            record.getCompiler_name_2(),
                            record.getCompiler_major_version_2()+"."+record.getCompiler_minor_version_2()+"."+record.getCompiler_patch_version_2(),
                            memberSummary1.stream().sorted().collect(Collectors.joining(",")),
                            memberSummary2.stream().sorted().collect(Collectors.joining(","))
                        ));
                    }
                }
            });

        Files.write(out,nonMatchingrecordsTSVEncoded);

        // investigate multiple ano-inn-cl in same outer class with same summary
        AtomicInteger dupsFoundCounter = new AtomicInteger();
        for (String outerClass:memberSummariesPerOuterClass.keySet()) {
            List<Set<String>> memberSummaries = memberSummariesPerOuterClass.get(outerClass);
            Set<Set<String>> memberSummariesAsSet = memberSummaries.stream().collect(Collectors.toSet());
            if (!memberSummaries.equals(memberSummariesAsSet)) {
                LOG.info("multiple equal member summaries found for ano inner classes within: " + outerClass);
                dupsFoundCounter.incrementAndGet();
            }
        }

        LOG.info("" + dupsFoundCounter.get() + " outer classes with multiple ano-inner classes with same member summary");
        LOG.info(""+aiclCounter.get()+ " pairs of anonymous inner classes analysed");
        LOG.info(""+anoInnerClasses.size()+ " anonymous inner classes analysed");
        LOG.info(""+nonmatchingAiclCounter.get()+ " pairs of anonymous inner classes with non-matching members found");
        LOG.info(""+nonmatchingAiclSameCompilerCounter.get()+ " pairs of anonymous inner classes with non-matching members found (different versions of same compiler)");
        LOG.info("details written to " + out);
    }

    private static String getOuterClass(Bytecode bytecode) {
        return ""+bytecode.getJarFileOrFolder()+"!/"+ bytecode.getClazzFile().replaceAll("\\$\\d*","");
    }

    private static String toTSV(String... values) {
        return Stream.of(values).collect(Collectors.joining("\t"));
    }


}
