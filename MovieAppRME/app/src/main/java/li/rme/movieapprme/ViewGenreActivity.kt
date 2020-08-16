package li.rme.movieapprme

import CustomArrayGenresAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_view_actor.*

class ViewGenreActivity : AppCompatActivity() {
    lateinit var adapter: CustomArrayGenresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_genre)

        val moviePosition = intent.getStringExtra("moviePosition");
        var currentMovie = Movies.array[moviePosition.toInt()]

        val itemsString = currentMovie.genres.map { it.toString() }.toTypedArray()
        adapter =
            CustomArrayGenresAdapter(this, R.layout.row_element_middle, itemsString)
        lvMain.adapter = this.adapter
        tvTitle.append( " " + currentMovie.title )
        btnBack.setOnClickListener {
            finish()
        }
    }
}
