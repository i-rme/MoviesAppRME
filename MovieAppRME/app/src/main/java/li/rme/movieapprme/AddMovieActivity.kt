package li.rme.movieapprme

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_movie.*

class AddMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        etId.setText(Movies.getNewId().toString())

        val adapterActors = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, Actors.toNamedArray()
        )
        spinnerActors.adapter = adapterActors

        val adapterGenres = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, Genres.toNamedArray()
        )
        spinnerGenres.adapter = adapterGenres

        btnBack.setOnClickListener {
            finish()
        }

        btnAdd.setOnClickListener {
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
                            _Config.ENDPOINT+"/mobile/user/addMovie.php?user="+_Config.USER+"&pass="+_Config.PASSWORD,
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
