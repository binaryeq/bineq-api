# BinEq API

This repository contains a simple Java API to access the BinEq benchmark.

The benchmark has to be downloaded separately into a folder, most API classes 
expect this folder as input. The core API is defined in a single class `io.github.bineq.datasets.DataSet`, it has
a method to read records from an oracle in the benchmark as stream. 

The various benchmark oracles are represented by classes `io.github.bineq.datasets.EQ*` (equivalence oracles) and `io.github.bineq.datasets.NEQ*` (non-equivalent oracles), respectively.

The project uses Maven to build, the command is `mvn test`. 

Usage example:

```java
Path benchmark = ...;  // the benchmark has been downloaded into this folder
DataSet oracle = new EQ(); // or some other included oracle like NEQ1 etc
oracle
    .readRecords(benchmark)
    .forEach (record -> {
        // do something interesting here! 
    });
````


Records are a representation of rows in the dataset. The API facilitates extracting the actual byte code from a record and the jars associated with this record:

```java
byte[] bytecode1 = new Bytecode(
    benchmark,
    benchmark.resolve(benchmark.getContainer_1()),
    record.getClass_1()
    ).getBytecode();
byte[] bytecode2 = new Bytecode(
    benchmark,
    benchmark.resolve(benchmark.getContainer_2()),
    record.getClass_2()
    ).getBytecode();
// now compare bytecodes, e.g. check for identity, whether the _tlsh_ hash is the same, etc !
```
