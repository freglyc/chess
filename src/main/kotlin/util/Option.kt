package util

/**
 * Optional monad.
 */
interface Option<T> {

    companion object {

        /**
         * Creates an optional with a value inside.
         */
        fun <T> some(value: T): Option<T> = Some(value)

        /**
         * Creates a empty optional.
         */
        @Suppress("UNCHECKED_CAST")
        fun <T> none(): Option<T> = None.SINGLETON as Option<T>

        /**
         * Narrows a type parameter.
         */
        @Suppress("UNCHECKED_CAST")
        fun <T> narrow(option: Option<out T>): Option<T> = option as Option<T>
    }

    /**
     * Returns whether or not there is an object.
     * True if some, false otherwise.
     */
    fun isSome(): Boolean

    /**
     * Returns whether or not there is an object.
     * True if none, false otherwise.
     */
    fun isNone(): Boolean = !isSome()

    /**
     * Gets the value inside the optional.
     */
    fun get(): T

    /**
     * Two lambdas, none and some function where some takes the internal value as a param.
     */
    fun <Q> match(noneFunc: () -> Q, someFunc: (T) -> Q): Q = if (isSome()) someFunc.invoke(get()) else noneFunc.invoke()

    /**
     * Converts optional to list of one or empty.
     */
    fun toList(): List<T> = match( { emptyList() }, { listOf(it) })

    /**
     * Gets the value if it exists, otherwise returns the alternative.
     * @param alternative - The alternative value.
     * @return value if it exists, alternative otherwise.
     */
    fun getOrElse(alternative: T): T = match({ alternative }, { it })

    /**
     * Runs predicate on contents. If true returns some, else returns none.
     */
    fun filter(predicate: (T) -> Boolean): Option<T> = map { if (predicate.invoke(it)) this else none() }

    /**
     * Returns a new option with the function applied to it if it exists.
     */
    fun <R> map(func: (T) -> Option<out R>): Option<R> = match({ none() }, { narrow(func.invoke(it)) })

    /**
     * Some.
     */
    class Some<T>(private val value: T): Option<T> {

        override fun isSome(): Boolean = true

        override fun get(): T = value

        override fun equals(other: Any?): Boolean {
            if (other !is Some<*>) return false
            if (get() == other.get()) return true
            return false
        }

        override fun toString(): String = "Some(${get().toString()})"

        override fun hashCode(): Int = 0
    }

    /**
     * None.
     */
    class None<T>: Option<T> {

        companion object { val SINGLETON = None<Any>() }

        override fun isSome(): Boolean = false

        override fun get(): T = throw NoSuchElementException("can't get() from Option.None")

        override fun equals(other: Any?): Boolean = other is None<*>

        override fun toString(): String = "None()"

        override fun hashCode(): Int = 0
    }
}
