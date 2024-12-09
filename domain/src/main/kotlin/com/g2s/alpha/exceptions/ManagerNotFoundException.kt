package com.g2s.alpha.exceptions

class ManagerNotFoundException(managerId: String) : RuntimeException("Manager with ID $managerId not found.")
