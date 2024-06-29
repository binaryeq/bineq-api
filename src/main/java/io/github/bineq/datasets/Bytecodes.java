package io.github.bineq.datasets;

import io.github.bineq.Bytecode;

import java.io.IOException;

/**
 * Represents a pair of bytecodes loaded from a record.
 * Note that Bytecode caches the actual bytecode (byte[]), so those instances should be reused.
 * @author jens dietrich
 */
public record Bytecodes(Bytecode first, Bytecode second) {

    byte[] bytes1() throws IOException {
        return first.getBytecode();
    }

    byte[] bytes2() throws IOException {
        return second.getBytecode();
    }
}
