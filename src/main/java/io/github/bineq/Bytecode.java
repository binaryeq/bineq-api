package io.github.bineq;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Bytecode located in a jar/zip representation.
 * @author jens dietrich
 */
public class Bytecode {

    // the root is used as hint to relativise paths for caching
    private Path root = null;

    private Path jarFileOrFolder = null;
    private String clazzFile = null;
    // derived & lazily initialised
    private byte[] bytecode = null;

    public Bytecode(Path root, Path jarFileOrFolder, String clazzFile) {
        Preconditions.checkNotNull(root);
        Preconditions.checkNotNull(jarFileOrFolder);
        Preconditions.checkNotNull(clazzFile);
        Preconditions.checkState(Files.exists(jarFileOrFolder));
        Preconditions.checkState(Files.exists(root));
        this.jarFileOrFolder = jarFileOrFolder;
        this.clazzFile = clazzFile;
        this.root = root;
    }

    public Bytecode(Path jarFileOrFolder, String clazzFile) {
        Preconditions.checkNotNull(jarFileOrFolder);
        Preconditions.checkNotNull(clazzFile);
        Preconditions.checkState(Files.exists(jarFileOrFolder));
        this.jarFileOrFolder = jarFileOrFolder;
        this.clazzFile = clazzFile;
    }

    public Path getRoot() {
        return root;
    }

    public Path getJarFileOrFolder() {
        return jarFileOrFolder;
    }

    public String getClazzFile() {
        return clazzFile;
    }


    /**
     * Establish whether the bytecode is available / can be loaded.
     * This can be used by clients to construct guards to record and report such cases.
     * @return
     */
    public boolean validate() {
        // trigger lazy initialisation
        try {
            getBytecode();
            return true;
        }
        catch (Exception x) {
            return false;
        }
    }

    /**
     * Get the actual bytecode.
      * @return
     * @throws IOException
     */
    public byte[] getBytecode() throws IOException {
        if (bytecode==null) {
            if (Files.isDirectory(jarFileOrFolder)) {
                // interpret as folder
                Optional<Path> match = Files.walk(jarFileOrFolder)
                    .filter(p -> clazzFile.equals(jarFileOrFolder.relativize(p).toString()))
                    .findFirst();

                Preconditions.checkState(match.isPresent(),"File not found: " + jarFileOrFolder + "/" + clazzFile);
                bytecode = Files.readAllBytes(match.get());

            }
            else {
                // interpret as jar file
                ZipFile zip = new ZipFile(jarFileOrFolder.toFile());
                Preconditions.checkNotNull(zip);
                while (clazzFile.startsWith("/")) {
                    clazzFile = clazzFile.substring(1);
                }
                ZipEntry entry = zip.getEntry(clazzFile);
                Preconditions.checkNotNull(entry); // see validate !

                InputStream in = zip.getInputStream(entry);
                bytecode = ByteStreams.toByteArray(in);

            }
            assert bytecode!=null;
        }
        return bytecode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bytecode bytecode1 = (Bytecode) o;
        return Objects.equals(root, bytecode1.root) && Objects.equals(jarFileOrFolder, bytecode1.jarFileOrFolder) && Objects.equals(clazzFile, bytecode1.clazzFile) && Arrays.equals(bytecode, bytecode1.bytecode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root, jarFileOrFolder, clazzFile);
    }

    public boolean isInnerClass() {
        return clazzFile.contains("$");
    }
}
