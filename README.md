# Fluid Lib
[![discord-singular](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/social/discord-singular_vector.svg)](https://discord.offsetmonkey538.top/)
## Disclaimer: this library is in development, so you might encounter problems. Please report any problems you find on github.
## What is this?
It's a library making it easier for mod developers to add fluids.  
I guess it's possible to add some things to existing fluids using datapacks and tags, but this is meant for mod developers.

## Including in your project
All you'll have to do is include the JitPack repository in your project like this:
```groovy
repositories {
    maven {
        name = "JitPack"
        url = "https://jitpack.io"
    }
}
```
Then you'll have to actually include the library: 
```groovy
dependencies {
    include modImplementation("top.offsetmonkey538:fluid-lib:[VERSION]")
}
```
Make sure to use the latest version of the library.
