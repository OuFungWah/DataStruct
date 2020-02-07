package binaryheap

enum class HEAPTYPE {
    MAX,
    MIN
}

/**
 * 堆（大根或小根）
 */
class BinaryHeap(val type: HEAPTYPE = HEAPTYPE.MAX) : IHeap<Int> {

    /**
     * 实际装载数据的数组
     */
    private val heap = arrayListOf<Int>()

    /**
     * 堆的大小
     */
    private var size = 0

    /**
     * 获取当前堆中最顶部的元素
     */
    override fun get(): Int? = if (size > 0) heap[0] else null

    /**
     * 获取当前堆中最顶部的元素，并将其从堆中弹出
     */
    override fun pop(): Int? {
        val result: Int? = get()
        if (result != null && size >= 1) {
            heap[0] = heap[size - 1]
            size--
            // 下沉
            sinkDown(0)
        }
        return result
    }

    /**
     * 往堆中加入元素
     */
    override fun put(element: Int) {
        if (heap.size <= size) {
            heap.add(element)
        } else {
            heap[size] = element
        }
        size++
        floatUp(size - 1)
        heap.forEach {
            print("$it ,")
        }
        println()
    }

    /**
     * 当前堆大小
     */
    override fun size(): Int = size

    /**
     * 上浮操作
     */
    private fun floatUp(index: Int) {
        var tempIndex = index
        while (tempIndex > 0) {
            if (type == HEAPTYPE.MAX) {
                if (heap[tempIndex.indexOfParent()] < heap[tempIndex]) {
                    swap(tempIndex, tempIndex.indexOfParent())
                } else {
                    break
                }
            } else {
                if (heap[tempIndex.indexOfParent()] > heap[tempIndex]) {
                    swap(tempIndex, tempIndex.indexOfParent())
                } else {
                    break
                }
            }
            tempIndex = tempIndex.indexOfParent()
        }
    }

    /**
     * 下沉操作
     */
    private fun sinkDown(index: Int) {
        var tempIndex = index
        while (tempIndex.indexOfLeft() < size) {
            var fitChild: Int
            if (type == HEAPTYPE.MAX) {
                fitChild =
                    if (heap[tempIndex.indexOfLeft()] > heap[tempIndex.indexOfRight()] || tempIndex.indexOfRight() >= size) tempIndex.indexOfLeft() else tempIndex.indexOfRight()
                if (heap[tempIndex] < heap[fitChild]) {
                    swap(tempIndex, fitChild)
                } else {
                    break
                }
            } else {
                fitChild =
                    if (heap[tempIndex.indexOfLeft()] < heap[tempIndex.indexOfRight()] || tempIndex.indexOfRight() >= size) tempIndex.indexOfLeft() else tempIndex.indexOfRight()
                if (heap[tempIndex] > heap[fitChild]) {
                    swap(tempIndex, fitChild)
                } else {
                    break
                }
            }
            tempIndex = fitChild
        }
    }

    /**
     * 元素交换操作
     */
    private fun swap(first: Int, second: Int) {
        val temp = heap[first]
        heap[first] = heap[second]
        heap[second] = temp
    }

}

/**
 * 左子节点位置
 */
fun Int.indexOfLeft(): Int = 2 * this + 1

/**
 * 右子节点位置
 */
fun Int.indexOfRight(): Int = 2 * this + 2

/**
 * 父节点位置
 */
fun Int.indexOfParent(): Int = (this - 1) / 2

interface IHeap<T> {

    fun get(): T?

    fun pop(): T?

    fun put(elements: T)

    fun size(): Int

}

fun main() {
    val heap = BinaryHeap()
    val arr = arrayListOf(9, 3, 4, 1, 89, 29, 77, 43, 27)
    arr.forEach {
        heap.put(it)
    }
    for (i in 0 until heap.size()) {
        print("${heap.pop()} ,")
    }
}