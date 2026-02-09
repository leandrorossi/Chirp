package com.leandrour.core.domain.util

class DataErrorException(
    val error: DataError
): Exception()