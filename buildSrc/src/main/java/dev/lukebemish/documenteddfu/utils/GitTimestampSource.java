package dev.lukebemish.documenteddfu.utils;

import org.gradle.api.provider.ValueSource;
import org.gradle.api.provider.ValueSourceParameters;
import org.gradle.process.ExecOperations;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public abstract class GitTimestampSource implements ValueSource<String, ValueSourceParameters.None> {

    private final ExecOperations execOperations;

    @Inject
    public GitTimestampSource(ExecOperations execOperations) {
        this.execOperations = execOperations;
    }

    @Nullable
    @Override
    public String obtain() {
        var out = new ByteArrayOutputStream();
        execOperations.exec(spec -> {
            spec.setExecutable("git");
            spec.setArgs(List.of("log", "-1", "--format=%at"));
            spec.setStandardOutput(out);
        });
        long timestamp = Long.parseLong(out.toString().trim()) * 1000;
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date(timestamp));
    }
}
