package com.oneliang.sudoku

import com.oneliang.sudoku.model.DataExt
import com.oneliang.sudoku.model.Data
import kotlin.math.sqrt


fun <TYPE : Comparable<TYPE>> Array<Array<TYPE>>.toData(): Array<Array<Data<TYPE>>> {
    return Array(DataExt.MAX_X_Y) { y: Int -> Array(DataExt.MAX_X_Y) { x: Int -> Data(this[y][x]) } }
}

fun <TYPE : Comparable<TYPE>> Array<Array<Data<TYPE>>>.copy(): Array<Array<Data<TYPE>>> {
    return Array(DataExt.MAX_X_Y) { y: Int -> Array(DataExt.MAX_X_Y) { x: Int -> Data(this[y][x].value) } }
}

class Sudoku<TYPE : Comparable<TYPE>>(private val dataSet: Array<Array<Data<TYPE>>>, private val metaDataArray: Array<TYPE>, private val empty: TYPE) {
    companion object;

    private val xBigGridCount = sqrt(DataExt.MAX_X_Y.toDouble()).toInt()
    private val yBigGridCount = xBigGridCount
    private val xCountPerGrid = xBigGridCount
    private val yCountPerGrid = xCountPerGrid
    private val dataExtMap = mutableMapOf<TYPE, DataExt<TYPE>>()
    private var tryingSudoku: Sudoku<TYPE>? = null

    init {
        for (y in this.dataSet.indices) {
            val columnCount = this.dataSet[y].size
            for (x in 0 until columnCount) {
                val data = this.dataSet[y][x].value
                val dataExt: DataExt<TYPE> = this.dataExtMap.getOrPut(data) { DataExt(data) }
                dataExt.x[x] = true
                dataExt.y[y] = true
                dataExt.count++
                print(data)
                if (x < columnCount - 1) {
                    print(",")
                }
            }
            println()
        }
        for (element in this.metaDataArray) {
            if (!this.dataExtMap.containsKey(element)) {
                this.dataExtMap[element] = DataExt(element)
            }
        }
    }

    private fun dataExist(x: Int, y: Int): Boolean {
        return this.dataSet[y][x].value != this.empty
    }

    fun printDataExtMap() {
        dataExtMap.toSortedMap().forEach {
            println(it.value)
        }
    }

    fun calculate(): Boolean {
        var canNotFill = false
        var hasFill = false
        val firstBigGridCanFillGridDataList = mutableListOf<GridData<TYPE>>()
        run loop@{
            this.dataExtMap.toSortedMap().forEach { (_, dataExt) ->
                val checkValue = dataExt.value
                if (checkValue == this.empty) {
//                    println("empty value no need to care.")
                    return@forEach//continue
                }
                if (dataExt.count == DataExt.MAX_X_Y) {
                    return@forEach//continue
                }
                for (xBigGridIndex in 0 until xBigGridCount) {
                    for (yBigGridIndex in 0 until yBigGridCount) {
                        val xStart = xBigGridIndex * xCountPerGrid
                        val xEnd = (xBigGridIndex + 1) * xCountPerGrid
                        val yStart = yBigGridIndex * yCountPerGrid
                        val yEnd = (yBigGridIndex + 1) * yCountPerGrid
                        var hasDataExist = false // has not fill data
                        val dataCanFillList = mutableListOf<Pair<Int, Int>>()
                        for (x in xStart until xEnd) {
                            for (y in yStart until yEnd) {
                                val currentValue = this.dataSet[y][x].value
                                if (!hasDataExist && currentValue == checkValue) {
                                    hasDataExist = true
                                }
                                if (!dataExt.x[x] && !dataExt.y[y] && !dataExist(x, y)) {
                                    dataCanFillList.add(Pair(x, y))
                                }
                            }
                        }
                        if (!hasDataExist) {//data has not exist
                            if (dataCanFillList.size == 1) {//only can fill one grid
                                val pair = dataCanFillList[0]
                                val y = pair.second
                                val x = pair.first
                                this.dataSet[y][x].value = checkValue
                                dataExt.x[x] = true
                                dataExt.y[y] = true
                                dataExt.count++
                                this.dataExtMap[empty]!!.count--
                                println("can fill value:(${x + 1},${y + 1})=${checkValue}")
                                if (!hasFill) {
                                    hasFill = true
                                }
                            } else if (dataCanFillList.isEmpty()) {//can not fill data..
                                //will rollback
                                canNotFill = true
                                return@loop//break forEach loop
                            } else {//can fill more than one grid
                                if (firstBigGridCanFillGridDataList.isEmpty()) {//only add first time
                                    dataCanFillList.forEach {
                                        firstBigGridCanFillGridDataList.add(GridData(checkValue, it.first, it.second))
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        if (canNotFill) {
            return false
        }
        var result = false
        if (!hasFill) {//has not fill
            if (firstBigGridCanFillGridDataList.isNotEmpty()) {
                run loop@{
                    firstBigGridCanFillGridDataList.forEach { gridData ->
                        //                        dataTryingStack.add(gridData)
                        val tryingSudoku = Sudoku(this.dataSet.copy(), this.metaDataArray, this.empty)
                        tryingSudoku.tryingGridData(gridData)
                        this.tryingSudoku = tryingSudoku
                        if (!tryingSudoku.calculate()) {
                            println("trying:(${gridData.x + 1},${gridData.y + 1})=${gridData.value} failure")
                            return@forEach
                        } else {
                            println("trying:(${gridData.x + 1},${gridData.y + 1})=${gridData.value} success")
                            tryingSudoku.dataExtMap.forEach inner@{ data, dataExt ->
                                this.dataExtMap[data]?.count = dataExt.count
                                if (data == empty) {
                                    return@inner
                                }
                                dataExt.x.forEachIndexed { index, value ->
                                    this.dataExtMap[data]?.x!![index] = value
                                }
                                dataExt.y.forEachIndexed { index, value ->
                                    this.dataExtMap[data]?.y!![index] = value
                                }
                            }
                            for (y in tryingSudoku.dataSet.indices) {
                                val columnCount = tryingSudoku.dataSet[y].size
                                for (x in 0 until columnCount) {
                                    this.dataSet[y][x].value = tryingSudoku.dataSet[y][x].value
                                }
                            }
                            result = true
                            return@loop
                        }
                    }
                }
            } else {
                result = if (dataExtMap[empty]!!.count == 0) {
                    println("finish?")
                    true
                } else {
                    false
                }
            }
        } else {
            result = calculate()
        }
        return result
    }

    fun print() {
        for (y in this.dataSet.indices) {
            val columnCount = this.dataSet[y].size
            for (x in 0 until columnCount) {
                print(this.dataSet[y][x].value)
                if (x < columnCount - 1) {
                    print(",")
                }
            }
            println()
        }
    }

    private fun tryingGridData(gridData: GridData<TYPE>) {
        val x = gridData.x
        val y = gridData.y
        val data = gridData.value
        val dataExt = this.dataExtMap[data]!!
        dataExt.x[x] = true
        dataExt.y[y] = true
        dataExt.count++
        this.dataExtMap[empty]!!.count--
        this.dataSet[y][x].value = data
        println("trying:(${x},${y})=${data}")
    }

//    fun rollingBackGridData(gridData: GridData<TYPE>) {
//        val x = gridData.x
//        val y = gridData.y
//        val data = gridData.value
//        val dataExt = dataExtMap[data]!!
//        dataExt.x[x] = false
//        dataExt.y[y] = false
//        dataExt.count--
//        dataExtMap[empty]!!.count++
//        dataSet[y][x].value = empty
//        println("rollback:(${x},${y})=${data}")
//    }

    private class GridData<TYPE : Comparable<TYPE>>(val value: TYPE, val x: Int, val y: Int)
}