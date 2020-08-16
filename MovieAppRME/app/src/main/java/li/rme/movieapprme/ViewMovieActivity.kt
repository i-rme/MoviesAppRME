package li.rme.movieapprme

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_view_movie.*
import java.io.InputStream
import java.net.URL


class ViewMovieActivity : AppCompatActivity() {

    fun getJsonFromURL(wantedURL: String): String {
        return URL(wantedURL).readText()
    }

    fun LoadImageFromWebOperations(url: String?): Drawable? {
        return try {
            val `is`: InputStream = URL(url).content as InputStream
            Drawable.createFromStream(`is`, "src name")
        } catch (e: java.lang.Exception) {
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_movie)

        val moviePosition = intent.getStringExtra("moviePosition");

        var currentMovie = Movies.array[moviePosition.toInt()]


        val backgroundThread = object : Thread() {
            override fun run() {
                try {

                    val movieExternalInfo =
                        getJsonFromURL("http://www.omdbapi.com/?t="+currentMovie.title+"&apikey=65d4a083")


                    val builder = GsonBuilder()
                    val o: Any = builder.create().fromJson(movieExternalInfo, Any::class.java)
                    val oMap = (o as Map<*, *>)

                    val url =
                        URL(oMap.get("Poster") as String?)
                    val bmp =
                        BitmapFactory.decodeStream(url.openConnection().getInputStream())


                    runOnUiThread(java.lang.Runnable {
                        imageView.setImageBitmap(bmp)
                        tvWriter.append(" " + oMap.get("Writer") as String?);
                    })

                } catch (e: Exception) {
                    e.printStackTrace();
                }
            }
        }

        backgroundThread.start();


        tvId.append(" " + currentMovie.id.toString());
        tvTitle.append(" " + currentMovie.title);
        tvDescription.append(" " + currentMovie.description);
        tvDirector.append(" " + currentMovie.director);
        tvYear.append(" " + currentMovie.year.toString());
        tvRuntime.append(" " + currentMovie.runtime.toString() + " minutes");
        tvRating.append(" " + currentMovie.rating.toString() + " out of 10");
        tvVotes.append(" " + currentMovie.votes.toString());
        tvRevenue.append(" " + currentMovie.revenue.toString() + " millions of US$");


        btnActors.setOnClickListener {
            val intent = Intent(baseContext, ViewActorActivity::class.java);
            intent.putExtra("moviePosition", moviePosition.toString())
            startActivity(intent);
        }

        btnGenres.setOnClickListener {
            val intent = Intent(baseContext, ViewGenreActivity::class.java);
            intent.putExtra("moviePosition", moviePosition.toString())
            startActivity(intent);
        }

        btnEdit.setOnClickListener {
            val intent = Intent(baseContext, EditMovieActivity::class.java);
            intent.putExtra("moviePosition", moviePosition.toString())
            startActivity(intent);
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
