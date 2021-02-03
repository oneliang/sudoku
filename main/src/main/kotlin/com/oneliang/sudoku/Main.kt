package com.oneliang.sudoku

const val E = 0//empty
val metaDataArray = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

//for copy
val empty = arrayOf(
    arrayOf(E, E, E, E, E, E, E, E, E),
    arrayOf(E, E, E, E, E, E, E, E, E),
    arrayOf(E, E, E, E, E, E, E, E, E),
    arrayOf(E, E, E, E, E, E, E, E, E),
    arrayOf(E, E, E, E, E, E, E, E, E),
    arrayOf(E, E, E, E, E, E, E, E, E),
    arrayOf(E, E, E, E, E, E, E, E, E),
    arrayOf(E, E, E, E, E, E, E, E, E),
    arrayOf(E, E, E, E, E, E, E, E, E)
)

val data = arrayOf(
    arrayOf(3, 6, 9, E, 5, 2, 4, 7, 8),
    arrayOf(8, 5, E, 6, 7, 4, 9, 3, 1),
    arrayOf(7, 1, 4, 3, E, 8, 2, 6, 5),
    arrayOf(6, 8, 3, 9, 2, 7, 1, 5, 4),
    arrayOf(5, 9, 7, 4, 1, 6, 8, 2, E),
    arrayOf(E, 2, 1, 8, 3, 5, 6, 9, 7),
    arrayOf(1, 3, 8, 7, 6, 9, E, 4, 2),
    arrayOf(2, 4, E, 5, 8, 3, 7, 1, 9),
    arrayOf(9, E, 5, 2, 4, 1, 3, E, 6)
)
val data1 = arrayOf(
    arrayOf(E, E, E, E, 5, 7, 4, E, 2),
    arrayOf(6, E, 7, E, E, E, 1, E, 5),
    arrayOf(3, E, 5, 4, 6, E, E, E, E),
    arrayOf(E, E, 2, E, E, 4, E, 1, 3),
    arrayOf(4, E, E, 5, 8, 3, E, E, 9),
    arrayOf(E, 3, E, 9, 1, E, E, E, E),
    arrayOf(E, E, E, E, 9, 8, E, 5, E),
    arrayOf(5, E, E, 2, 3, 6, E, E, 1),
    arrayOf(1, 6, E, 7, E, E, 3, E, E)
)

val data2 = arrayOf(
    arrayOf(2, E, E, E, 8, 4, E, 7, 3),
    arrayOf(5, 7, E, E, E, E, E, 4, 6),
    arrayOf(4, 8, E, 6, 5, E, E, E, 1),
    arrayOf(E, E, E, 9, E, E, E, 5, E),
    arrayOf(9, 4, E, 3, 7, 5, E, E, E),
    arrayOf(E, E, 5, E, 2, E, E, 8, 7),
    arrayOf(1, 5, E, E, 3, E, 7, E, E),
    arrayOf(E, E, E, 7, 6, 1, E, 2, 5),
    arrayOf(E, 6, E, E, E, 9, E, E, E)
)
val data3 = arrayOf(
    arrayOf(3, E, 2, 7, E, E, E, E, 9),
    arrayOf(E, E, 8, E, E, E, E, 4, 5),
    arrayOf(E, E, 4, E, E, 1, 3, E, E),
    arrayOf(E, E, E, E, 5, 9, E, E, E),
    arrayOf(E, 9, E, E, 3, E, E, 6, E),
    arrayOf(E, E, E, 2, 6, E, E, E, E),
    arrayOf(E, E, 1, 4, E, E, 2, E, E),
    arrayOf(2, 6, E, E, E, E, 1, E, E),
    arrayOf(4, E, E, E, E, 2, 5, E, 3)
)

val data4 = arrayOf(
    arrayOf(E, 9, 5, E, E, 8, E, E, E),
    arrayOf(E, E, 2, E, E, 6, 7, E, E),
    arrayOf(E, 4, E, E, E, E, E, E, 5),
    arrayOf(E, 5, E, E, 2, E, E, E, 7),
    arrayOf(E, 6, E, E, 5, E, E, 2, E),
    arrayOf(4, E, E, E, 7, E, E, 8, E),
    arrayOf(2, E, E, E, E, E, E, 4, E),
    arrayOf(E, E, 6, 1, E, E, 3, E, E),
    arrayOf(E, E, E, 3, E, E, 2, 5, E)
)

val data5 = arrayOf(
    arrayOf(1, 7, E, E, 5, 8, 6, E, 9),
    arrayOf(E, E, E, E, E, E, E, E, 1),
    arrayOf(5, E, E, 6, 3, E, E, E, E),
    arrayOf(7, E, E, 5, E, 6, 8, E, E),
    arrayOf(8, E, 9, E, 1, E, 7, E, 5),
    arrayOf(E, E, 6, 8, E, 4, E, E, 3),
    arrayOf(E, E, E, E, 4, 9, E, E, 7),
    arrayOf(3, E, E, E, E, E, E, E, E),
    arrayOf(9, E, 7, 2, 6, E, E, 5, 4)
)

fun main(args: Array<String>) {
    val sudoku = Sudoku(data3.toData(), metaDataArray, E)
    println("-----printDataExtMap-----")
    sudoku.printDataExtMap()
    println("-----calculate-----")
    val result = sudoku.calculate()
    println("-----calculate result:${result}-----")
    println("-----printDataExtMap-----")
    sudoku.printDataExtMap()
    println("-----print-----")
    sudoku.print()

}