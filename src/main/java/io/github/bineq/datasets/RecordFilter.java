package io.github.bineq.datasets;

import java.util.function.Predicate;

/**
 * Filter for records.
 * @author jens dietrich
 */
public interface RecordFilter extends Predicate<Record> {
    RecordFilter ALL = new RecordFilter() {
        @Override
        public String name() {
            return "all";
        }

        @Override
        public boolean test(Record record) {
            return true;
        }
    };

    String name();
}
