package li.rme.movieapprme

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_movie.*


class EditMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_movie)

        val moviePosition = intent.getStringExtra("moviePosition");

        btnBack.setOnClickListener {
            finish()
        }


        var currentMovie = Movies.array[moviePosition.toInt()]


        val adapterActors = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, Actors.toNamedArray()
        )
        spinnerActors.adapter = adapterActors
        spinnerActors.setSelection(currentMovie.actors[0]-1)

        val adapterGenres = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, Genres.toNamedArray()
        )
        spinnerGenres.adapter = adapterGenres
        spinnerGenres.setSelection(currentMovie.genres[0]-1)




        etId.setText(currentMovie.id.toString());
        etTitle.setText(currentMovie.title);
        etDescription.setText(currentMovie.description);
        etDirector.setText(currentMovie.director);
        etYear.setText(currentMovie.year.toString());
        etRuntime.setText(currentMovie.runtime.toString());
        etRating.setText(currentMovie.rating.toString());
        etVotes.setText(currentMovie.votes.toString());
        etRevenue.setText(currentMovie.revenue.toString());

        btnEdit.setOnClickListener {

            var json = """{
  "id": """" + etId.text + """",
  "title": """" + etTitle.text + """",
  "description": """" + etDescription.text + """",
  "director": """" + etDirector.text + """",
  "year": """" + etYear.text + """",
  "runtime": """" + etRuntime.text + """",
  "rating": """" + etRating.text + """",
  "votes": """" + etVotes.text + """",
  "revenue": """" + etRevenue.text + """",
  "genres": [
    """" + (spinnerGenres.selectedItemPosition+1)+ """"
  ],
  "actors": [
    """" + (spinnerActors.selectedItemPosition+1)+ """"
  ]
}"""

            val backgroundThread = object : Thread() {
                override fun run() {
                    try {
                        Movies.post(
                            _Config.ENDPOINT+"/mobile/user/updateMovie.php?user="+_Config.USER+"&pass="+_Config.PASSWORD,
                            json
                        )
                    } catch (e: Exception) {
                        e.printStackTrace();
                    }
                }
            }

            backgroundThread.start();

            Thread.sleep(200);
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish();
        }


    }


}
