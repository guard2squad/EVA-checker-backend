package com.g2s.alpha.exceptions

class ManagerDeleteFailedException(managerId: String) : RuntimeException("Failed to delete manager with ID $managerId")
