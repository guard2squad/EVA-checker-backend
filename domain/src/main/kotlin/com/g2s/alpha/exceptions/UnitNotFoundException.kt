package com.g2s.alpha.exceptions

class UnitNotFoundException(zonecode: String, buildingNumber: String, unitNumber: String) :
    RuntimeException("Zonecode $zonecode, building number $buildingNumber, unit $unitNumber not found")