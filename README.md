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
    .records(benchmark)
    .forEach (record -> {
        // this structure caches bytecodes loaded from jars in memory
        Bytecodes bytecodes = record.load(benchmark);
        
        byte[] bytes1 = bytecodes.bytes1();
        byte[] bytes2 = bytecodes.bytes2();

        // now compare bytecodes, e.g. check for byte-by-byte equality, whether a certain hash is the same, etc !
        // example:
        assert Arrays.equals(bytes1,bytes2);
    });
````
