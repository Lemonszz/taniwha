## Taniwha
### A general purpose library to be used with Architectury mods

---
## How to use:

`build.gradle`
```
    //this should be in the allprojects{ block
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/Lemonszz/taniwha")
            credentials {
                //Don't put your token (or username) into this file, see before for a secure way to do this
                username = [GIT USERNAME]
                password = [GIT ACCESS TOKEN]
            }
        }
    }
```
---
`common/build.gradle`
```
dependencies {
    modApi("party.lemons:taniwha-fabric:${rootProject.taniwha_version}"){
        transitive = false
    }
}
```
---

`fabric/build.gradle`
```
    modApi("party.lemons:taniwha-fabric:${rootProject.taniwha_version}"){
        transitive = false;
    }
```
---
`forge/build.gradle`
```
    modApi("party.lemons:taniwha-forge:${rootProject.taniwha_version}"){
        transitive = false;
    }
```
---
`gradle.properties`
```
taniwha_version=VERSION HERE
```

### Github Authentication  
Github requires you to authenticate with them to download packages for whatever reason, see the `credentials` block in the project root's build.gradle. 

See: [Authenticating to GitHub Packages](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#authenticating-to-github-packages)  
Don't complain about it unless you're willing to host me for free :^)

The quick and dirty solution:

* Generate a key with the `read:packages` permission [here](https://github.com/settings/tokens)
* In your project's root direction create a `secrets` directory
* **ADD `secret/` TO YOUR .gitignore** 
* **MAKE SURE YOU'VE ADDED `secret/` TO YOUR .gitignore**
* Create the file `secret/secrets.properties`
```
gpr.user=[YOUR GIT USERNAME]
gpr.key=[ACCESS KEY]
```
* **MAKE SURE YOU'VE ADDED `secret/` TO YOUR .gitignore**
* Modify your root `built.gradle`'s `allprojects` block to look something like this:
```
    def apikeyPropertiesFile = rootProject.file("secret/secrets.properties");
    def apikeyProperties = new Properties()
    apikeyProperties.load(new FileInputStream(apikeyPropertiesFile))

    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/Lemonszz/taniwha")
            credentials {
                username = apikeyProperties['gpr.user'] ?: System.getenv("USERNAME")
                password = apikeyProperties['gpr.key'] ?: System.getenv("TOKEN")
            }
        }
    }
```
* DONE

