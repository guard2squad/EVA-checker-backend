package com.g2s.alpha.exceptions

class CheckNotFoundException(checkId: String) : RuntimeException("Check with ID $checkId not found.")
