# Data Functions

Data Functions is a library mod that provides a system for mods to define functions through JSON, allowing players and
modpack authors to customise mod behaviour in datapacks without needing to write any code. 

Data Functions was created for, and is used a large amount in, [Haema](https://github.com/williambl/haema), so check there
for an example of how it's used in practice!

## Setting up

First, add the repository and dependency to your build.gradle:

```groovy
repositories {
    maven {
        name "Will BL Releases"
        url "https://maven.willbl.dev/releases"
    }
}

dependencies {
    // use modImplementation instead if required
    modApi include("com.williambl.dfunc:data_functions:${version}")
}
```
## Usage (in code)

Sorry, since the rewrite of data-functions, this section needs a rewrite!
