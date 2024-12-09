package com.g2s.alpha.exceptions

class BuildingNotFoundException(zonecode: String, buildingNumber: String) :
    RuntimeException("Zonecode $zonecode, building number $buildingNumber is not found")