import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.songmodel

class adaptersong(
    var context: Context,
    var songs: ArrayList<songmodel>,
    val onSongClick: (songmodel) -> Unit
) : RecyclerView.Adapter<adaptersong.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tie: TextView = itemView.findViewById(R.id.title)
        val im: ImageView = itemView.findViewById(R.id.imh)
        val lay: LinearLayout = itemView.findViewById(R.id.row)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptersong.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.songblueprint, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p: songmodel = songs[position]
        holder.tie.text = p.name
        Glide.with(holder.itemView.context)
            .load(p.img)
            // Optional: add a placeholder while the image is loading
//            .error(R.drawable.p4)              // Optional: add an error image in case loading fails
            .into(holder.im)

        // Pass the selected song back to the activity
        holder.itemView.setOnClickListener {
            onSongClick(p)
        }
    }

    override fun getItemCount(): Int {
//        Log.d("ArraySize", "Size of songs: ${songs.size}")
        return songs.size
    }
}
