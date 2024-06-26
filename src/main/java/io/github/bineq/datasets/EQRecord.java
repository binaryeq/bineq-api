package io.github.bineq.datasets;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

/**
 * Record type for the EQ oracle.
 * @author jens dietrich
 */
public class EQRecord extends Record {

    private String compiler_name_1 = null;
    private String compiler_name_2 = null;
    private String compiler_major_version_1;
    private String compiler_major_version_2;
    private String compiler_minor_version_1;
    private String compiler_minor_version_2;
    private String compiler_patch_version_1;
    private String compiler_patch_version_2;
    private String compiler_extra_config_1;
    private String compiler_extra_config_2;
    private String project_name;
    private String project_major_version;
    private String project_minor_version;
    private String project_patch_version;
    private String generated_by_1;
    private String generated_by_2;
    private String bytecode_jep181_1;
    private String bytecode_jep181_2;
    private String bytecode_jep280_1;
    private String bytecode_jep280_2;
    private String scope_1;
    private String scope_2;
    private String n_anon_inner_classes_1;
    private String n_anon_inner_classes_2;


    public String getCompiler_extra_config_1() {
        return compiler_extra_config_1;
    }

    public void setCompiler_extra_config_1(String compiler_extra_config_1) {
        this.compiler_extra_config_1 = compiler_extra_config_1;
    }

    public String getCompiler_extra_config_2() {
        return compiler_extra_config_2;
    }

    public void setCompiler_extra_config_2(String compiler_extra_config_2) {
        this.compiler_extra_config_2 = compiler_extra_config_2;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_major_version() {
        return project_major_version;
    }

    public void setProject_major_version(String project_major_version) {
        this.project_major_version = project_major_version;
    }

    public String getProject_minor_version() {
        return project_minor_version;
    }

    public void setProject_minor_version(String project_minor_version) {
        this.project_minor_version = project_minor_version;
    }

    public String getProject_patch_version() {
        return project_patch_version;
    }

    public void setProject_patch_version(String project_patch_version) {
        this.project_patch_version = project_patch_version;
    }

    public String getGenerated_by_1() {
        return generated_by_1;
    }

    public void setGenerated_by_1(String generated_by_1) {
        this.generated_by_1 = generated_by_1;
    }

    public String getGenerated_by_2() {
        return generated_by_2;
    }

    public void setGenerated_by_2(String generated_by_2) {
        this.generated_by_2 = generated_by_2;
    }

    public String getBytecode_jep181_1() {
        return bytecode_jep181_1;
    }

    public void setBytecode_jep181_1(String bytecode_jep181_1) {
        this.bytecode_jep181_1 = bytecode_jep181_1;
    }

    public String getBytecode_jep181_2() {
        return bytecode_jep181_2;
    }

    public void setBytecode_jep181_2(String bytecode_jep181_2) {
        this.bytecode_jep181_2 = bytecode_jep181_2;
    }

    public String getBytecode_jep280_1() {
        return bytecode_jep280_1;
    }

    public void setBytecode_jep280_1(String bytecode_jep280_1) {
        this.bytecode_jep280_1 = bytecode_jep280_1;
    }

    public String getBytecode_jep280_2() {
        return bytecode_jep280_2;
    }

    public void setBytecode_jep280_2(String bytecode_jep280_2) {
        this.bytecode_jep280_2 = bytecode_jep280_2;
    }

    public String getScope_1() {
        return scope_1;
    }

    public void setScope_1(String scope_1) {
        this.scope_1 = scope_1;
    }

    public String getScope_2() {
        return scope_2;
    }

    public void setScope_2(String scope_2) {
        this.scope_2 = scope_2;
    }

    public String getN_anon_inner_classes_1() {
        return n_anon_inner_classes_1;
    }

    public void setN_anon_inner_classes_1(String n_anon_inner_classes_1) {
        this.n_anon_inner_classes_1 = n_anon_inner_classes_1;
    }

    public String getN_anon_inner_classes_2() {
        return n_anon_inner_classes_2;
    }

    public void setN_anon_inner_classes_2(String n_anon_inner_classes_2) {
        this.n_anon_inner_classes_2 = n_anon_inner_classes_2;
    }

    @Override
    public Map<String, String> asProperties(Path datasetRoot) {
        Map<String, String>  properties = super.asProperties(datasetRoot);
        properties.put("compiler_name_1",compiler_name_1);
        properties.put("compiler_major_version_1",compiler_major_version_1);
        properties.put("compiler_minor_version_1",compiler_minor_version_1);
        properties.put("compiler_patch_version_1",compiler_patch_version_1);
        properties.put("compiler_name_2",compiler_name_2);
        properties.put("compiler_major_version_2",compiler_major_version_2);
        properties.put("compiler_minor_version_2",compiler_minor_version_2);
        properties.put("compiler_patch_version_2",compiler_patch_version_2);
        properties.put("compiler_extra_config_1",compiler_extra_config_1);
        properties.put("compiler_extra_config_2",compiler_extra_config_2);
        properties.put("project_name",project_name);
        properties.put("project_major_version",project_major_version);
        properties.put("project_minor_version",project_minor_version);
        properties.put("project_patch_version",project_patch_version);
        properties.put("generated_by_1",generated_by_1);
        properties.put("generated_by_2",generated_by_2);
        properties.put("bytecode_jep181_1",bytecode_jep181_1);
        properties.put("bytecode_jep181_2",bytecode_jep181_2);
        properties.put("bytecode_jep280_1",bytecode_jep280_1);
        properties.put("bytecode_jep280_2",bytecode_jep280_2);
        properties.put("scope_1",scope_1);
        properties.put("scope_2",scope_2);
        properties.put("n_anon_inner_classes_1",n_anon_inner_classes_1);
        properties.put("n_anon_inner_classes_2",n_anon_inner_classes_2);

        return properties;
    }

    public String getCompiler_name_1() {
        return compiler_name_1;
    }

    public void setCompiler_name_1(String compiler_name_1) {
        this.compiler_name_1 = compiler_name_1;
    }

    public String getCompiler_name_2() {
        return compiler_name_2;
    }

    public void setCompiler_name_2(String compiler_name_2) {
        this.compiler_name_2 = compiler_name_2;
    }

    public String getCompiler_major_version_1() {
        return compiler_major_version_1;
    }

    public void setCompiler_major_version_1(String compiler_major_version_1) {
        this.compiler_major_version_1 = compiler_major_version_1;
    }

    public String getCompiler_major_version_2() {
        return compiler_major_version_2;
    }

    public void setCompiler_major_version_2(String compiler_major_version_2) {
        this.compiler_major_version_2 = compiler_major_version_2;
    }

    public String getCompiler_minor_version_1() {
        return compiler_minor_version_1;
    }

    public void setCompiler_minor_version_1(String compiler_minor_version_1) {
        this.compiler_minor_version_1 = compiler_minor_version_1;
    }

    public String getCompiler_minor_version_2() {
        return compiler_minor_version_2;
    }

    public void setCompiler_minor_version_2(String compiler_minor_version_2) {
        this.compiler_minor_version_2 = compiler_minor_version_2;
    }

    public String getCompiler_patch_version_1() {
        return compiler_patch_version_1;
    }

    public void setCompiler_patch_version_1(String compiler_patch_version_1) {
        this.compiler_patch_version_1 = compiler_patch_version_1;
    }

    public String getCompiler_patch_version_2() {
        return compiler_patch_version_2;
    }

    public void setCompiler_patch_version_2(String compiler_patch_version_2) {
        this.compiler_patch_version_2 = compiler_patch_version_2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EQRecord eqRecord = (EQRecord) o;
        return Objects.equals(compiler_name_1, eqRecord.compiler_name_1) && Objects.equals(compiler_name_2, eqRecord.compiler_name_2) && Objects.equals(compiler_major_version_1, eqRecord.compiler_major_version_1) && Objects.equals(compiler_major_version_2, eqRecord.compiler_major_version_2) && Objects.equals(compiler_minor_version_1, eqRecord.compiler_minor_version_1) && Objects.equals(compiler_minor_version_2, eqRecord.compiler_minor_version_2) && Objects.equals(compiler_patch_version_1, eqRecord.compiler_patch_version_1) && Objects.equals(compiler_patch_version_2, eqRecord.compiler_patch_version_2) && Objects.equals(compiler_extra_config_1, eqRecord.compiler_extra_config_1) && Objects.equals(compiler_extra_config_2, eqRecord.compiler_extra_config_2) && Objects.equals(project_name, eqRecord.project_name) && Objects.equals(project_major_version, eqRecord.project_major_version) && Objects.equals(project_minor_version, eqRecord.project_minor_version) && Objects.equals(project_patch_version, eqRecord.project_patch_version) && Objects.equals(generated_by_1, eqRecord.generated_by_1) && Objects.equals(generated_by_2, eqRecord.generated_by_2) && Objects.equals(bytecode_jep181_1, eqRecord.bytecode_jep181_1) && Objects.equals(bytecode_jep181_2, eqRecord.bytecode_jep181_2) && Objects.equals(bytecode_jep280_1, eqRecord.bytecode_jep280_1) && Objects.equals(bytecode_jep280_2, eqRecord.bytecode_jep280_2) && Objects.equals(scope_1, eqRecord.scope_1) && Objects.equals(scope_2, eqRecord.scope_2) && Objects.equals(n_anon_inner_classes_1, eqRecord.n_anon_inner_classes_1) && Objects.equals(n_anon_inner_classes_2, eqRecord.n_anon_inner_classes_2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), compiler_name_1, compiler_name_2, compiler_major_version_1, compiler_major_version_2, compiler_minor_version_1, compiler_minor_version_2, compiler_patch_version_1, compiler_patch_version_2, compiler_extra_config_1, compiler_extra_config_2, project_name, project_major_version, project_minor_version, project_patch_version, generated_by_1, generated_by_2, bytecode_jep181_1, bytecode_jep181_2, bytecode_jep280_1, bytecode_jep280_2, scope_1, scope_2, n_anon_inner_classes_1, n_anon_inner_classes_2);
    }



}
