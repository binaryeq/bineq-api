package io.github.bineq.datasets.analysis;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import io.github.bineq.datasets.*;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.bineq.CLICommons.DATASET_ROOT;

/**
 * Analyse the N1EQ datasets. Output is just values printed to the console.
 * @author jens dietrich
 */
public class NEQ1Analysis {

    public static Option OUT = Option.builder()
        .argName("output")
        .option("out")
        .hasArg()
        .required(true)
        .desc("the result file (.txt)")
        .build();

    static final Map<String,Predicate<NEQ1Record>> FILTERS = new LinkedHashMap<>() {};

    static {
        FILTERS.put("all", record -> true);
        FILTERS.put("BinBC-only", record -> !record.getBinary_compatible() && record.getSource_compatible() && record.getSemantic_compatible());
        FILTERS.put("SrcBC-only", record -> !record.getSource_compatible() && record.getBinary_compatible() && record.getSemantic_compatible());
        FILTERS.put("SrcBC+BinBC", record -> !record.getSource_compatible() && !record.getBinary_compatible());
        FILTERS.put("SemBC", record -> !record.getSemantic_compatible());
   }

    final static Logger LOG = LoggerFactory.getLogger(NEQ1Analysis.class);

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
            // pre-collect records for faster processing -- requires more memory
            List<NEQ1Record> records = new NEQ1().readRecords(root).collect(Collectors.toList());
            for (String filterName:FILTERS.keySet()) {
                Predicate<NEQ1Record> filter = FILTERS.get(filterName);
                long count = records.parallelStream().filter(filter).count();
                String countAsString = NumberFormat.getInstance().format(count);
                rows.add(filterName + " : " + countAsString);
            }

            Files.write(out,rows);
            LOG.info("results written to " + out);

        } catch (Exception e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -cp <path-to-built-jar> " + NEQ1Analysis.class.getName(), options);
            e.printStackTrace();
            System.exit(1);
        }
        assert cli != null;

    }

}
