enum class HEAPTYPE {
    MAX,
    MIN
}

class BinaryHeap(val type: HEAPTYPE = HEAPTYPE.MAX) : IHeap<Int> {

    /**
     * 实际装载数据的数组
     */
    private val heap = arrayListOf<Int>()

    private var size = 0

    override fun get(): Int? = if (size > 0) heap[0] else null

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

    override fun put(element: Int) {
        if (heap.size <= size) {
            heap.add(element)
        } else {
            heap[size] = element
        }
        size++
        floatUp(size - 1)
    }

    override fun size(): Int = size

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

    private fun sinkDown(index: Int) {
        var tempIndex = index
        while (tempIndex.indexOfLeft() < size) {
            var fitChild = -1
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
            if (tempIndex < 0) break
        }
    }

    private fun swap(first: Int, second: Int) {
        val temp = heap[first]
        heap[first] = heap[second]
        heap[second] = temp
    }

}

fun Int.indexOfLeft(): Int = 2 * this + 1

fun Int.indexOfRight(): Int = 2 * this + 2

fun Int.indexOfParent(): Int = (this - 1) / 2

interface IHeap<T> {

    fun get(): T?

    fun pop(): T?

    fun put(elements: T)

    fun size(): Int

}

fun main() {
    val heap = BinaryHeap()
    val arr = arrayListOf(9, 3, 4, 1, 29, 77, 43, 27)
    arr.forEach {
        heap.put(it)
    }
    for (i in 0 until heap.size()) {
        print("${heap.pop()} ,")
    }
}