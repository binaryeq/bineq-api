package io.github.bineq.datasets.analysis;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import io.github.bineq.datasets.*;
import io.github.bineq.datasets.DataSet;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.bineq.CLICommons.*;

/**
 * Analyse the EQ datasets. Export results as latex table.
 * @author jens dietrich
 */
public class EQAnalysis {

    public static Option OUT = Option.builder()
        .argName("output")
        .option("out")
        .hasArg()
        .required(true)
        .desc("the result file (.tex)")
        .build();

    static final EQ[] DATASETS = {
        new io.github.bineq.datasets.EQ(),
        new io.github.bineq.datasets.EQNoAIC(),
        new io.github.bineq.datasets.EQOpenJDK(),
        new io.github.bineq.datasets.EQOpenJDKNoAIC(),
        new io.github.bineq.datasets.EQSameCompiler(),
        new io.github.bineq.datasets.EQSameCompilerNoAIC(),
        new io.github.bineq.datasets.EQSameMajorCompilerVersion(),
        new io.github.bineq.datasets.EQSameMajorCompilerVersionNoAIC(),
        new io.github.bineq.datasets.EQDifferentCompiler(),
        new io.github.bineq.datasets.EQDifferentCompilerNoAIC()
    };

    static final Map<String,Predicate<EQRecord>> FILTERS = new LinkedHashMap<>() {};

    static {
        FILTERS.put("all", record -> true);
        FILTERS.put("JEP181", record -> !record.getBytecode_jep181_1().equals(record.getBytecode_jep181_2()));
        FILTERS.put("JEP280", record -> !record.getBytecode_jep280_1().equals(record.getBytecode_jep280_2()));
        FILTERS.put("CG-antlr",record -> record.getGenerated_by_1().trim().equals("antlr") || record.getGenerated_by_2().trim().equals("antlr"));
        FILTERS.put("CG-javacc",record -> record.getGenerated_by_1().trim().equals("javacc") || record.getGenerated_by_2().trim().equals("javacc"));
        FILTERS.put("CG-annot",record -> record.getGenerated_by_1().trim().equals("test-annotations") || record.getGenerated_by_2().trim().equals("test-annotations"));
    }

    final static Logger LOG = LoggerFactory.getLogger(EQAnalysis.class);

    public static void main(String[] args) throws IOException {

        Options options = new Options();
        options.addOption(DATASET_ROOT);
        options.addOption(OUT);

        CommandLine cli = null;
        Path root = null;
        Path out = null;
        CommandLineParser parser = new DefaultParser();
        try {
            cli = parser.parse(options, args);
            String rootFolderName = cli.getOptionValue(DATASET_ROOT);
            Preconditions.checkNotNull(rootFolderName, "the value of -root must not be null");
            root = Path.of(rootFolderName);
            Preconditions.checkState(Files.exists(root));
            String outName = cli.getOptionValue(OUT);
            out = Path.of(outName);

            List<String> rows = new ArrayList<>();
            List columnNames = new ArrayList();
            columnNames.add("name");
            columnNames.addAll(FILTERS.keySet().stream().collect(Collectors.toList()));


            // latex header
            rows.add("\\begin{table*}[h]");
            String frm = "";
            for (int i=0;i<columnNames.size();i++) {frm=frm+'l';}
            rows.add("\\begin{tabular}{|" + frm + "|}");
            rows.add("\t\\hline");
            String row = row(columnNames);
            rows.add(row);
            rows.add("\t\\hline \\hline");
            for (DataSet dataSet:DATASETS) {
                // pre-collect records for faster processing -- requires more memory
                List<EQRecord> records = (List<EQRecord>) dataSet.readRecords(root).collect(Collectors.toList());
                List<String> cells = new ArrayList<>();
                cells.add(dataSet.name());
                for (String filterName:FILTERS.keySet()) {
                    Predicate<EQRecord> filter = FILTERS.get(filterName);
                    long count = records.parallelStream().filter(filter).count();
                    String countAsString = NumberFormat.getInstance().format(count);
                    cells.add(countAsString);
                }
                row = row(cells);
                LOG.info("next row: " + row);
                rows.add(row);
            }
            rows.add("\t\\hline");
            rows.add("\\end{tabular}");
            rows.add("\\caption{Properties of data sets derived from EQ}");
            rows.add("\\label{tab:eq}");
            rows.add("\\end{table*}");

            Files.write(out,rows);
            LOG.info("results written to " + out);

        } catch (Exception e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -cp <path-to-built-jar> " + EQAnalysis.class.getName(), options);
            e.printStackTrace();
            System.exit(1);
        }
        assert cli != null;

    }


    private static String row(List<String> data) {
        return data.stream().collect(Collectors.joining(" & ","\t"," \\\\"));
    }

    private String row(String... data) {
        return Stream.of(data).collect(Collectors.joining(" & ","\t"," \\\\"));
    }
}
