package io.github.bineq.datasets;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Representation of a record in the dataset. Specific datasets extend this by adding more attributes (columns).
 * Subclasses should be used to represent those.
 * @author jens dietrich
 */
public class Record implements Serializable {

    public static final Pattern ANON_INNER_CLASS_PATTERN = Pattern.compile(".+\\$\\d+.+");

    private String container_1 = null;
    private String container_2 = null;
    private String class_1 = null;
    private String class_2 = null;


    // provenance columns
    private int lineNumber = -1;
    private DataSet dataset = null;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public DataSet getDataset() {
        return dataset;
    }

    public void setDataset(DataSet dataset) {
        this.dataset = dataset;
    }

    public String getContainer_1() {
        return container_1;
    }

    public void setContainer_1(String container_1) {
        this.container_1 = container_1;
    }

    public String getContainer_2() {
        return container_2;
    }

    public void setContainer_2(String container_2) {
        this.container_2 = container_2;
    }

    public String getClass_1() {
        return class_1;
    }

    public void setClass_1(String class_1) {
        this.class_1 = class_1;
    }

    public String getClass_2() {
        return class_2;
    }

    public void setClass_2(String class_2) {
        this.class_2 = class_2;
    }


    public boolean isClass_1InnerClass() {
        return class_1.contains("$");
    }

    public boolean isClass_2InnerClass() {
        return class_2.contains("$");
    }

    public boolean isClass_1AnonInnerClass() {
        return ANON_INNER_CLASS_PATTERN.matcher(class_1).matches();
    }

    public boolean isClass_2AnonInnerClass() {
        return ANON_INNER_CLASS_PATTERN.matcher(class_2).matches();
    }

    // do not use Properties to have control over key order for more readable provenance
    public Map<String,String> asProperties(Path datasetRoot) {
        Map<String,String> properties = new LinkedHashMap<>();
        properties.put("line",lineNumber==-1?"n/a":(""+lineNumber));
        properties.put("dataset",dataset.getRecordPath(datasetRoot).toString());
        properties.put("jar1",container_1);
        properties.put("class1",class_1);
        properties.put("jar2",container_2);
        properties.put("class2",class_2);
        return properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return lineNumber == record.lineNumber && Objects.equals(container_1, record.container_1) && Objects.equals(container_2, record.container_2) && Objects.equals(class_1, record.class_1) && Objects.equals(class_2, record.class_2) && Objects.equals(dataset, record.dataset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(container_1, container_2, class_1, class_2, lineNumber, dataset);
    }
}
