package io.github.bineq;

import org.apache.commons.cli.Option;

/**
 * Common CLI options.
 * @author jens dietrich
 */
public class CLICommons {

    public static Option DATASET_ROOT = Option.builder()
        .argName("dataset root")
        .option("root")
        .hasArg()
        .required(true)
        .desc("the root folder of the dataset containing the records (/*.tsv) and the jars in /jars referenced in those records")
        .build();

}
