package com.oneliang.sudoku.model

class Data<TYPE : Comparable<TYPE>>(var value: TYPE) {

    override fun toString(): String {
        return value.toString()
    }
}