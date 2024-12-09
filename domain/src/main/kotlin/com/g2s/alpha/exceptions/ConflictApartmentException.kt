package com.g2s.alpha.exceptions

class ConflictApartmentException(zonecode: String) :
    RuntimeException("Apartment with zonecode $zonecode is conflicted")
