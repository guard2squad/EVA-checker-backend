package com.g2s.alpha.exceptions

class EmailNotRegisteredException(email: String) :
    RuntimeException("Email $email is not registered.")
