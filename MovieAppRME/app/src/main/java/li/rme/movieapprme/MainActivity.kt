package li.rme.movieapprme

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

// TO CHANGE THE SERVER AND LOGIN INFORMATION USE THE _Config CLASS

class MainActivity : AppCompatActivity() {

    lateinit var adapter: CustomMovieViewHolderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = CustomMovieViewHolderAdapter(
            context = this,
            resourceId = R.layout.row_element,
            items = Movies.list
        )

        lvMain.adapter = this.adapter
        lvMain.setOnItemClickListener { parent, view, position, _ ->
            val intent = Intent(this, ViewMovieActivity::class.java)

            intent.putExtra("moviePosition", position.toString())
            startActivity(intent)
            adapter.getView(position, view, parent)
        }

        lvMain.setOnItemLongClickListener { parent, view, position, id ->
            val intent = Intent(this, EditMovieActivity::class.java)

            intent.putExtra("moviePosition", position.toString())
            startActivity(intent)
            adapter.getView(position, view, parent)
            return@setOnItemLongClickListener true
        }


        btnContact.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent);
        }


        fabAddMovie.setOnClickListener {
            val intent = Intent(this, AddMovieActivity::class.java)
            startActivity(intent);
        }

        btnEditMovie.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "To edit a move long tap it", Toast.LENGTH_SHORT)
            toast.show()
        }


    }


}
