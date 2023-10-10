package dev.lukebemish.documenteddfu.utils;

import org.gradle.api.provider.ValueSource;
import org.gradle.api.provider.ValueSourceParameters;
import org.gradle.process.ExecOperations;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.util.List;

public abstract class GitHashSource implements ValueSource<String, ValueSourceParameters.None> {

    private final ExecOperations execOperations;

    @Inject
    public GitHashSource(ExecOperations execOperations) {
         this.execOperations = execOperations;
    }

    @Nullable
    @Override
    public String obtain() {
        var out = new ByteArrayOutputStream();
        execOperations.exec(spec -> {
            spec.setExecutable("git");
            spec.setArgs(List.of("rev-parse", "HEAD"));
            spec.setStandardOutput(out);
        });
        return out.toString().trim();
    }
}
