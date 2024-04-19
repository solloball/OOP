package ru.nsu.romanov.checker

Binding binding = new Binding([
        "config": config
])

new GroovyShell(binding).evaluate(new File("./src/main/java/ru/nsu/romanov/checker/parse.groovy").text
        + new File("config").text)
