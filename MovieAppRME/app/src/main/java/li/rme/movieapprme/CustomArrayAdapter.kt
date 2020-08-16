import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.row_element.view.*

class CustomArrayAdapter : ArrayAdapter<String> {

    val resourceId: Int

    constructor(context: Context, resourceId: Int, items: Array<String>) : super(
        context,
        resourceId,
        items
    ) {
        this.resourceId = resourceId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(resourceId, null)
        val text1 = view.text1 as TextView
        val text2 = view.text2 as TextView
        val value = getItem(position)
        text1.text = "Actor ID: $value"
        text2.text = "Name: $value"
        //Log.d("ADAPTER GET VIEW", value)
        return view
    }
}