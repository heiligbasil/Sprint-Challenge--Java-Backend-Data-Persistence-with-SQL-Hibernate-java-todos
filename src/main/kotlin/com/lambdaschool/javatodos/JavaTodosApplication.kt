package com.lambdaschool.javatodos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JavaTodosApplication

fun main(args: Array<String>)
{
    runApplication<JavaTodosApplication>(*args)
}
