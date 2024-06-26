package io.github.bineq.datasets;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

/**
 * Record type for the NEQ2 oracle.
 * @author jens dietrich
 */
public class NEQ2Record extends Record {

    private String description = null;
    private int line = 0;
    private String method_descriptor = null;
    private String method_name = null;
    private String mutator = null;


    @Override
    public Map<String, String> asProperties(Path datasetRoot) {
        Map<String, String>  properties = super.asProperties(datasetRoot);
        properties.put("description",description);
        properties.put("line",""+line);
        properties.put("method_descriptor",method_descriptor);
        properties.put("method_name",method_name);
        properties.put("mutator",mutator);
        return properties;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getMethod_descriptor() {
        return method_descriptor;
    }

    public void setMethod_descriptor(String method_descriptor) {
        this.method_descriptor = method_descriptor;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public String getMutator() {
        return mutator;
    }

    public void setMutator(String mutator) {
        this.mutator = mutator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NEQ2Record that = (NEQ2Record) o;
        return line == that.line && Objects.equals(description, that.description) && Objects.equals(method_descriptor, that.method_descriptor) && Objects.equals(method_name, that.method_name) && Objects.equals(mutator, that.mutator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, line, method_descriptor, method_name, mutator);
    }
}
