class Elements {
    private val elements = mutableListOf<Int>()
    private val _actives = mutableListOf<Int>()
    private val _inactives = mutableListOf<Int>()
    private var index = 0

    var size: Int
        get() = elements.size
        set(value) {
            _actives.clear()
            _inactives.clear()
            index = 0

            elements.clear()
            elements.addAll(1..value)
        }

    val actives: List<Int>
        get() = _actives

    val inactives: List<Int>
        get() = _inactives

    private val finished: Boolean
        get() = _inactives.size >= elements.size - 1

    operator fun get(i: Int): Int = elements[i]

    fun shuffle() {
        size = size
        elements.shuffle()
    }

    fun step(): Boolean {
        _actives.clear()

        val pair = elements[index] to elements[index + 1]
        if (pair.first > pair.second) {
            elements[index] = pair.second
            elements[index + 1] = pair.first
        }

        if (finished) {
            _inactives.add(index)
            return false
        }

        _actives.add(index++)
        _actives.add(index)
        if ((index + 1) >= (_inactives.minOrNull() ?: elements.size)) {
            _inactives.add(index)
            index = 0
        }

        return true
    }
}
