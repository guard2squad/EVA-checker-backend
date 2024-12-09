package com.g2s.alpha.exceptions

class UserNotFoundException(userId: String?, email: String?) :
    RuntimeException("User with ID $userId with Email $email not found.")
