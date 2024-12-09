package com.g2s.alpha.exceptions

class UserDeleteFailedException(userId: String) : RuntimeException("Failed to delete user with ID $userId")
