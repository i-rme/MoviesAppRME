package li.rme.movieapprme

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

class SplashActivity : AppCompatActivity() {

    // TO CHANGE THE SERVER AND LOGIN INFORMATION USE THE _Config CLASS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val backgroundThread = object : Thread() {
            override fun run() {

                val apiMoviesPref: SharedPreferences = getSharedPreferences("apiMoviesPref", 0)
                val apiActorsPref: SharedPreferences = getSharedPreferences("apiActorsPref", 0)
                val apiGenresPref: SharedPreferences = getSharedPreferences("apiGenresPref", 0)
                var apiMovies = "[]"
                var apiActors = "[]"
                var apiGenres = "[]"

                var SERVER_CONNECTIVITY = true

                try {
                    HttpURLConnection.setFollowRedirects(false)
                    val con: HttpURLConnection =
                        URL(_Config.ENDPOINT).openConnection() as HttpURLConnection
                    con.setRequestMethod("HEAD")
                    con.setConnectTimeout(600)
                    con.getResponseCode() === HttpURLConnection.HTTP_OK
                } catch (e: java.lang.Exception) {
                    Log.e("ERROR", "no internet")
                    SERVER_CONNECTIVITY = false
                }

                if (SERVER_CONNECTIVITY) {
                    try {

                        apiMovies =
                            getJsonFromURL(_Config.ENDPOINT + "/mobile/user/getMovies.php?user=" + _Config.USER + "&pass=" + _Config.PASSWORD + "&complex=true")
                        apiActors =
                            getJsonFromURL(_Config.ENDPOINT + "/mobile/user/getActors.php?user=" + _Config.USER + "&pass=" + _Config.PASSWORD + "&complex=true")
                        apiGenres =
                            getJsonFromURL(_Config.ENDPOINT + "/mobile/user/getGenres.php?user=" + _Config.USER + "&pass=" + _Config.PASSWORD + "&complex=true")

                        val apiMoviesPrefEditor = apiMoviesPref.edit()
                        apiMoviesPrefEditor.putString("apiMoviesPref", apiMovies)
                        apiMoviesPrefEditor.apply()

                        val apiActorsPrefEditor = apiActorsPref.edit()
                        apiActorsPrefEditor.putString("apiActorsPref", apiActors)
                        apiActorsPrefEditor.apply()

                        val apiGenresPrefEditor = apiGenresPref.edit()
                        apiGenresPrefEditor.putString("apiGenresPref", apiGenres)
                        apiGenresPrefEditor.apply()

                    } catch (e: Exception) {
                        e.printStackTrace();
                    }
                }else{
                    apiMovies =
                        apiMoviesPref.getString("apiMoviesPref", "[]").toString()
                    apiActors =
                        apiActorsPref.getString("apiActorsPref", "[]").toString()
                    apiGenres =
                        apiActorsPref.getString("apiActorsPref", "[]").toString()

                }

                Movies.parse(apiMovies)
                Actors.parse(apiActors)
                Genres.parse(apiGenres)

                sleep(100);
                val intent = Intent(baseContext, MainActivity::class.java);
                startActivity(intent);

            }
        }

        backgroundThread.start();

    }

    fun getJsonFromURL(wantedURL: String): String {
        return URL(wantedURL).readText()
    }
}
