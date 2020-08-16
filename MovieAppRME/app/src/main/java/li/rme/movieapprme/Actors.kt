package li.rme.movieapprme

import com.google.gson.Gson

class Actors {
    companion object {
        fun parse(apiResponse: String) {
            val gson = Gson()
            json = apiResponse
            array = gson.fromJson(
                apiResponse,
                Array<Actor>::class.java
            )
            list = array?.toMutableList<Actor>()
        }

        fun toArray(): Array<String> {
            val iterator = list?.iterator()

            var arrayActors = arrayOf<String>()
            if (iterator != null) {
                iterator.forEach {
                    arrayActors = arrayActors + it.id.toString()
                }
            }
            return arrayActors
        }

        fun toNamedArray(): Array<String> {
            val iterator = list?.iterator()

            var arrayActors = arrayOf<String>()
            if (iterator != null) {
                iterator.forEach {
                    arrayActors = arrayActors +  array?.get(it.id - 1).toString()
                }
            }
            return arrayActors
        }

        var json: String? = null
        var list: MutableList<Actor>? = null
        var array: Array<Actor>? = null
    }
}
