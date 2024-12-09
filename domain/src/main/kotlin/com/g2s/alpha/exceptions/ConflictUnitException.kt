package com.g2s.alpha.exceptions

class ConflictUnitException(zonecode: String, buildingNumber: String, unitNumber: String) :
    RuntimeException("Zonecode $zonecode, building number $buildingNumber, unit $unitNumber is conflicted")
