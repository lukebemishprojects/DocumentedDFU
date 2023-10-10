package dev.lukebemish.documenteddfu.utils;

import org.gradle.api.Project;
import org.gradle.api.provider.Provider;

import javax.inject.Inject;

public abstract class DocumentedDFUExtension {
    public final Provider<String> gitHash;
    public final Provider<String> gitTimestamp;

    @Inject
    public DocumentedDFUExtension(Project project) {
        this.gitTimestamp = project.getProviders().of(GitTimestampSource.class, spec -> {});
        this.gitHash = project.getProviders().of(GitHashSource.class, spec -> {});
    }
}
