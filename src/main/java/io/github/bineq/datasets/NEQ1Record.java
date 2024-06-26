package io.github.bineq.datasets;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

/**
 * Record type for the NEQ1 oracle.
 * @author jens dietrich
 */
public class NEQ1Record extends Record {

    private String description = null;
    private int line = 0;
    private boolean source_compatible = false;
    private boolean binary_compatible = false;
    private boolean semantic_compatible = false;


    @Override
    public Map<String, String> asProperties(Path datasetRoot) {
        Map<String, String>  properties = super.asProperties(datasetRoot);
        properties.put("description",description);
        properties.put("line",""+line);
        properties.put("source_compatible",""+source_compatible);
        properties.put("binary_compatible",""+binary_compatible);
        properties.put("semantic_compatible",""+semantic_compatible);
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

    public boolean getSource_compatible() {
        return source_compatible;
    }

    public void setSource_compatible(boolean source_compatible) {
        this.source_compatible = source_compatible;
    }

    public boolean getBinary_compatible() {
        return binary_compatible;
    }

    public void setBinary_compatible(boolean binary_compatible) {
        this.binary_compatible = binary_compatible;
    }

    public boolean getSemantic_compatible() {
        return semantic_compatible;
    }

    public void setSemantic_compatible(boolean semantic_compatible) {
        this.semantic_compatible = semantic_compatible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NEQ1Record that = (NEQ1Record) o;
        return line == that.line && Objects.equals(description, that.description) && Objects.equals(source_compatible, that.source_compatible) && Objects.equals(binary_compatible, that.binary_compatible) && Objects.equals(semantic_compatible, that.semantic_compatible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, line, source_compatible, binary_compatible, semantic_compatible);
    }
}
