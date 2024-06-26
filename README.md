# BinEq API

This repository contains a simple Java API to access the BinEq benchmark.

The benchmark has to be downloaded separately into a folder, most API classes 
expect this folder as input. The core dataset is defined in `io.github.bineq.datasets.DataSet`, it cntains
a method to read records from a oracle in the benchmark as stream. 

The various benchmark oracles are represented by classes `io.github.bineq.datasets.EQ*` and `io.github.bineq.datasets.NEQ*`.

The project uses Maven to build, the command is `mvn test`. 