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

### Obtaining a DFunction

The functions themselves (referred to as 'DFunctions') can be encoded/decoded with a codec, which can be retrieved from
a DFunction Type Registry. The two DFunction Type Registries provided by this mod are for DFunctions that return booleans
(`DFunction#PREDICATES`) and DFunctions that return doubles (`DFunction#NUMBER_FUNCTIONS`).

```java
DFunction<Double> myFunction = DFunction.NUMBER_FUNCTION.codec().decode(/* a dynamic */);
```

A DFunction instance can also be created in code - each DFunctionType has a factory method. This is very useful for
data generation.

```java
DFunction<Double> myFunction = NumberDFunction.COSINE.factory().apply(ContextArg.NUMBER_A.arg());
```

To apply a DFunction, pass it a single DFContext argument. The DFContext contains the various actual arguments for the DFunction.
There are various static methods to create DFContexts for different sets of arguments.

```java
DFContext context = DFContext.entity(/* an entity */);
DFunction<Boolean> myFunction = /* ... */;
boolean result = myFunction.apply(context);
```

### Context Specifications

⚠️
Note that if the DFunction requires arguments that the context does not supply, an exception will be thrown, possibly
crashing the game!
```json5
{
  "type": "dfunc:swimming" // this DFunctionType requires an entity in context
}
```
```java
// where myFunction is a DFunction created from the above JSON
DFContext context = DFContext.numbers(0.0, 1.0);
boolean result = myFunction.apply(context); // this will throw an exception!
```

However, we can guard against this by detecting in advance whether the DFunction's _specification_ is satisfied by the
arguments we plan on giving it later. This can be done during loading, so you can fail datapack loading if the DFunction
is invalid.

```java
// where myFunction is a DFunction created from the above JSON
boolean isValid = DFContextSpec.TWO_NUMBERS.satisfies(myFunction.spec());
if (!isValid) {
    // throw an exception or something
}
```

### Creating DFunction Types

You can create your own DFunction Types using the `create` methods in the DFunction class. The generics for these methods
can get a bit complicated, but you generally have to explitly define them anyway, because javac can't infer them properly.

```java
// from Haema
public static final DFunctionType<Double, ? extends Function<ContextArg<Entity>, ? extends DFunction<Double>>> BLOOD = Registry.register(
        DFunction.NUMBER_FUNCTION.registry(),
        id("blood"),
        DFunction.<ContextArg<Entity>, Double>create(
                ContextArg.ENTITY,
                (e, ctx) -> VampireComponent.KEY.maybeGet(e.get(ctx)).map(VampireComponent::getBlood).orElse(0.0)));
```

## Usage (in JSON)

A DFunction with no explicit arguments:
```json
{
  "type": "dfunc:swimming"
}
```

A DFunction with explicit arguments:
```json
{
  "type": "dfunc:submerged_in",
  "fluid": "minecraft:water"
}
```

A DFunction with a renamed context argument (it will search for an entity argument named `target`):
```json
{
  "type": "dfunc:swimming",
  "entity": "target"
}
```

A DFunction with another DFunction providing the value for a context argument:
```json
{
  "type": "dfunc:add",
  "a": {
    "type": "dfunc:number_game_rule",
    "rule": "my_rule"
  },
  "b": {
    "type": "dfunc:number_game_rule",
    "rule": "my_rule_2"
  }
}
```

A DFunction with a constant value:
```json
3
```

combine this with the above to be more useful:
```json
{
  "type": "dfunc:add",
  "a": {
    "type": "dfunc:number_game_rule",
    "rule": "my_rule"
  },
  "b": 3
}
```
