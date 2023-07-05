package com.maruchin.core.model

import java.util.UUID

@JvmInline
value class ID(val value: String) {

    companion object {

        val random: ID
            get() = ID(UUID.randomUUID().toString())

        val empty: ID
            get() = ID("")
    }
}