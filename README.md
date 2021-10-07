# Jogo Urso JavaFX
`jog urso`

## Maven
### Gerando o Archetype
```sh
mvn archetype:generate \
    -DarchetypeGroupId=org.openjfx \
    -DarchetypeArtifactId=javafx-archetype-simple \
    -DarchetypeVersion=0.0.3 \
    -DgroupId=groupId \
    -DartifactId=artifactId \
    -Dversion=version \
    -Djavafx-version=11
```
Este archetype utiliza o [javafx-maven-plugin](https://github.com/openjfx/javafx-maven-plugin) para rodar aplicações JavaFX 11+


### Rodando o Projeto
`mvn javafx:run`

Não esquecer de setar o JAVA_HOME


