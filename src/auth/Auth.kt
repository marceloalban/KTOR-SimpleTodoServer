package com.alban.todoserver.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.alban.todoserver.models.User
import java.util.*


private const val secret = "zAP5MBA4B4Ijz0MZaS48"
private const val issuer = "ktor.io"
private const val validityInMs = 36_000_00 * 1 // 1 hours
private val algorithm = Algorithm.HMAC512(secret)

val verifier: JWTVerifier = JWT
    .require(algorithm)
    .withIssuer(issuer)
    .build()

fun makeToken(user: User): String = JWT.create()
    .withSubject("Authentication")
    .withIssuer(issuer)
    .withClaim("id", user.id)
    .withClaim("name", user.name)
    .withClaim("email", user.email)
    .withExpiresAt(getExpiration())
    .sign(algorithm)

private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)