package com.oneliang.sudoku.model


class DataExt<TYPE : Comparable<TYPE>>(val value: TYPE) {
    companion object {
        const val MAX_X_Y = 9
    }

    var count = 0
    val x = Array(MAX_X_Y) { false }
    val y = Array(MAX_X_Y) { false }
    override fun toString(): String {
        val xStringBuilder = StringBuilder()
        for (i in x.indices) {
            xStringBuilder.append(i)
            xStringBuilder.append(":")
            xStringBuilder.append(x[i])
            xStringBuilder.append(",")
        }
        val yStringBuilder = StringBuilder()
        for (j in y.indices) {
            yStringBuilder.append(j)
            yStringBuilder.append(":")
            yStringBuilder.append(y[j])
            yStringBuilder.append(",")
        }
        return "value:${value}, count:$count, x:{$xStringBuilder}, y:{$yStringBuilder}"
    }
}