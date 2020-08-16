package li.rme.movieapprme

import CustomArrayActorsAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_view_actor.*

class ViewActorActivity : AppCompatActivity() {
    lateinit var adapter: CustomArrayActorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_actor)

        val moviePosition = intent.getStringExtra("moviePosition");
        var currentMovie = Movies.array[moviePosition.toInt()]

        val itemsString = currentMovie.actors.map { it.toString() }.toTypedArray()
        adapter =
            CustomArrayActorsAdapter(this, R.layout.row_element_middle, itemsString)
        lvMain.adapter = this.adapter
        tvTitle.append( " " + currentMovie.title )
        btnBack.setOnClickListener {
            finish()
        }

    }
}
