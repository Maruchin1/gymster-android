package com.maruchin.data.plan.model

data class Sets(val standard: Int, val drop: Int) {

    val total: Int
        get() = standard + drop

    override fun toString(): String {
        return buildString {
            append(standard)
            repeat(drop) {
                append("+")
            }
        }
    }
}