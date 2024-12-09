package com.g2s.alpha.exceptions

class ApartmentNotFoundException(zonecode: String) : RuntimeException("Apartment with zonecode $zonecode not found.")
