package com.lambdaschool.javatodos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class JavaTodosApplication

fun main(args: Array<String>)
{
    runApplication<JavaTodosApplication>(*args)
}
