package ru.nsu.romanov.checker.config

Binding binding = new Binding([
        "config": config
])

def load() {
    new GroovyShell(binding).evaluate(
            new File("./src/main/java/ru/nsu/romanov/checker/config/parse.groovy").text
            + new File("config").text)
}

load()