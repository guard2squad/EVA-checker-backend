package com.g2s.alpha.exceptions

class ConflictEmailException(email: String) : RuntimeException("Email $email is conflicted.")
