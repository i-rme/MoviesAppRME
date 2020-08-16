package li.rme.movieapprme

import com.google.gson.Gson

class Genres {
    companion object {
        fun parse(apiResponse: String) {
            val gson = Gson()
            json = apiResponse
            array = gson.fromJson(
                apiResponse,
                Array<Genre>::class.java
            )
            list = array?.toMutableList<Genre>()
        }

        fun toArray(): Array<String> {
            val iterator = list?.iterator()

            var arrayGenres = arrayOf<String>()
            if (iterator != null) {
                iterator.forEach {
                    arrayGenres = arrayGenres + it.id.toString()
                }
            }
            return arrayGenres
        }

        fun toNamedArray(): Array<String> {
            val iterator = list?.iterator()

            var arrayGenres = arrayOf<String>()
            if (iterator != null) {
                iterator.forEach {
                    arrayGenres = arrayGenres +  array?.get(it.id - 1).toString()
                }
            }
            return arrayGenres
        }

        var json: String? = null
        var list: MutableList<Genre>? = null
        var array: Array<Genre>? = null
    }
}
