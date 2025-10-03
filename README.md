# Documented DataFixerUpper
[![Javadocs](https://img.shields.io/badge/javadocs-blue?style=for-the-badge)](https://projects.lukebemish.dev/DocumentedDFU/)
[![Version](https://img.shields.io/badge/dynamic/xml?style=for-the-badge&color=blue&label=Latest%20Release&prefix=v&query=metadata%2F%2Flatest&url=https%3A%2F%2Fmaven.lukebemish.dev%2Freleases%2Fdev%2Flukebemish%2Fdocumenteddfu%2Fmaven-metadata.xml)](https://maven.lukebemish.dev/releases/dev/lukebemish/documenteddfu/)

This is a set of javadoc patches which add documentation to Mojang's [DataFixerUpper](https://github.com/Mojang/DataFixerUpper) library,
alongside tooling to apply and generate these patches and publish modified versions of DFU with documentation present.

### To use

Add the maven repository to your `build.gradle` and configure gradle to replace the normal DFU with the modified,
documented variant:
```groovy
repositories {
    exclusiveContent {
        forRepository {
            maven {
                url = uri("https://maven.lukebemish.dev/releases/")
            }
        }
        filter {
            includeModule("dev.lukebemish", "documenteddfu")
        }
    }
}

configurations.configureEach {
    resolutionStrategy.eachDependency { DependencyResolveDetails details ->
        if (details.requested.group == 'com.mojang' && details.requested.name == 'datafixerupper') {
            details.useTarget "dev.lukebemish:documenteddfu:${documentedDfuVersion}"
        }
    }
}
```

### To contribute

Clone this repository and run the `setup` task. This will create folders with `modified` and `clean` DFU versions, and
apply the javadoc patches to the `modified` version. Make any modifications you would like to the `modified` version, and
then run `generatePatches` to generate doc patches (JSON representations of added/changed docs) from the modified version.
