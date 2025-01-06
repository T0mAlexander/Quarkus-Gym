# Quarkus

### Requisitos:

- [Java 21](https://download.java.net/java/GA/jdk21.0.2/f2283984656d49d69e91c558476027ac/13/GPL/openjdk-21.0.2_linux-x64_bin.tar.gz) (obrigatório)

- [Quarkus CLI 3.17.5](https://github.com/quarkusio/quarkus/releases/download/3.17.5/quarkus-cli-3.17.5.tar.gz) (opcional)

## Executando a aplicação em modo de desenvolvimento

```shell script
./gradlew quarkusDev
```

ou

```shell
quarkus dev
```

> **_NOTE:_** É necessário que tenha o Quarkus instalado na versão **3.17.5**

## Compilando e executando a aplicação

The application can be packaged using:

```shell script
./gradlew build
```

ou

```shell
quarkus build
```

Irá gerar o artefato chamado `quarkus-run.jar` na pasta `build/quarkus-app`.
Atente-se de que não se trata de uma _über-jar_ assim que as dependências são copiadas para a pasta `build/quarkus-app/lib/`.

A aplicação agora é executável usando o comando `java -jar build/quarkus-app/quarkus-run.jar`.

Se você quer compilar um artefato em _über-jar_, execute o seguinte comando:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

A aplicação, empacotada como uma _über-jar_, agora está executável usando `java -jar build/*-runner.jar`.

## Criando um nativo executável

O artefato nativo se trata de um binário executável que possui perfomance otimizada da sua aplicação. Ao invés da aplicação ser executada pela clássica JVM, é executada pela GraalVM Mandrel, uma versão otimizada da GraalVM oficial da Oracle dedicada especialmente para aplicações feitas em Quarkus

Você pode criar um nativo executável usando o comando:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

>💡 Verifique a versão da biblioteca **GNU C Library** com `ldd -version`. Caso esteja fora do intervalo de **2.32** ~ **2.34**, gere o nativo executável com Docker

ou

```shell
quarkus build --native
```

Você poderá então executar seu nativo executável como se fosse um Shell Script como demonstra neste comando abaixo: 

```shell
./build/quarkus-1.0.0-runner
```

Se desejas aprender mais sobre compilação de artefatos nativos do Quarkus, consulte a [documentação sobre Quarkus com Gradle](https://quarkus.io/guides/gradle-tooling).
