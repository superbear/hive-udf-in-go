# Hive UDF in Go
Hive UDF (User-Defined Functions) in Go.

## Requirements
- [Go](https://go.dev/)
- [Maven](https://maven.apache.org/index.html)

## How to build
There is a provided `Makefile` with all the build targets.

### Build JAR
```bash
make build
```
This creates a `target/hive-udf-in-go-1.0-SNAPSHOT-jar-with-dependencies.jar` in the `target/` directory.

### Test
```bash
make test
```

## Usage

## References
- [Apache Hive](https://github.com/apache/hive/tree/master/ql/src/java/org/apache/hadoop/hive/ql/udf/generic)
- [call-go-function-from-java-demo](https://github.com/freewind-demos/call-go-function-from-java-demo)
- [go-cshared-examples](https://github.com/vladimirvivien/go-cshared-examples)
