package com.g2s.alpha.exceptions

class AdminNotRegisteredException(name: String) :
    RuntimeException("Admin with name $name is not registered.")