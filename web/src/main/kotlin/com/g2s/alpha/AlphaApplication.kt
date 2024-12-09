package com.g2s.alpha

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.g2s.alpha"])
class AlphaApplication

fun main(args: Array<String>) {
    runApplication<AlphaApplication>(*args)
}
