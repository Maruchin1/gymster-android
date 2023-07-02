package com.maruchin.core.model

import java.util.UUID

@JvmInline
value class ID(val value: String) {

    companion object {

        fun random(): ID {
            return ID(UUID.randomUUID().toString())
        }
    }
}