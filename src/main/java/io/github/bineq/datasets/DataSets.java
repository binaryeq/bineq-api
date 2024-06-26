package io.github.bineq.datasets;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Gather all known datasets.
 * @author jens dietrich
 */
public class DataSets {

    final static Logger LOG = LoggerFactory.getLogger(DataSets.class);

    /**
     * Get a list of all known datasets.
     * @return
     */
    public static List<DataSet> getAll() {
        return getAll(null);
    }


    /**
     * Get a list of datasets.
     * @param names a comma separated list of equivalence names. If null or empty, an empty list will be returned.
     * @return
     */
    public static List<DataSet> getAllOrEmpty(String names) {
        if (names==null || names.trim().isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        else return getAll(names);
    }

    /**
     * Get a list of datasets.
     * @param names a comma separated list of dataset names. If set, only datasets with names present in this list will be returned. If null, all available datasets will be returned.
     * @return
     */
    public static List<DataSet> getAll(String names) {
        ServiceLoader<DataSet> loader = ServiceLoader.load(DataSet.class);
        List<DataSet> datasets = null;
        if (names==null || names.trim().isEmpty()) {
            LOG.info("no filter set, returning all datasets available");
            datasets = loader.stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
        }
        else {
            Set<String> eqNames = Stream.of(names.split(",")).collect(Collectors.toSet());
            LOG.info("loading datasets matching any of the following names: {" + names + "}");
            datasets = loader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(e -> eqNames.contains(e.name()))
                .collect(Collectors.toList());
        }
        LOG.info("available datasets: " + datasets.stream().map(e -> e.getClass().getName()).collect(Collectors.joining(",","{","}")));
        return datasets;
    }


    /**
     * Get a single dataset.
     * @param name
     * @return
     */
    public static DataSet get(String name) {
        Preconditions.checkNotNull(name);
        Preconditions.checkArgument(name.contains(","),"the argument must be a single name, not a list");
        List<DataSet> list = getAll(name);
        Preconditions.checkState(list.size()>0,"no dataset found with name " + name);
        Preconditions.checkState(list.size()<2,"more than one dataset found with name " + name);
        return list.get(0);
    }

    /**
     * Get the names of all known datasets.
     * @return a list of names
     */
    public static List<String> getNames() {
        return getAll().stream().map(e->e.name()).collect(Collectors.toList());
    }

    /**
     * Get names concatenated into a single string.
     * @return
     */
    public static String getNamesAsString() {
        return getNames().stream().collect(Collectors.joining(",","{","}"));
    }

}
