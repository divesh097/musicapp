package com.example.musicapp

import adaptersong
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values
import java.io.IOException

class music : AppCompatActivity() {

    private lateinit var l: RecyclerView
    private lateinit var p: ArrayList<songmodel>
    private lateinit var image: ImageView
    private lateinit var text: TextView
    private lateinit var media: MediaPlayer
    private lateinit var playButton: Button
    private  lateinit  var bar:SeekBar
    private lateinit var handl:Handler
    private var iuser=false
    private  lateinit var playlistl:RecyclerView
    private lateinit var playlistp: ArrayList<playlistmodel>
    private lateinit var q:SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        var  qq=false

        // Initialize views
        playlistl=findViewById(R.id.play)
        l = findViewById(R.id.song)
        image = findViewById(R.id.songimg)
        text = findViewById(R.id.bartext)
        playButton = findViewById(R.id.ply)

        // Initialize RecyclerView
        p = ArrayList()
        playlistp=ArrayList()
        // Populate song list (ensure valid URLs)
//
        var adap=adaptersong(this, p) { selectedSong ->
            sing(selectedSong.song.toString(), selectedSong.name.toString(), selectedSong.img.toString(),selectedSong.video.toString())
        }
        l.layoutManager = LinearLayoutManager(this)
        l.adapter = adap
//        var j = FirebaseDatabase.getInstance().getReference("-1")

        for (i in 1..8) {
             var j = FirebaseDatabase.getInstance().getReference("$i")
            j.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val songData = snapshot.getValue(songmodel::class.java)
                    if (songData != null) {
                        p.add(songData)
                        // Check the size of p after adding the data
//                    Log.d("array2",p.size.toString())
                        adap.notifyDataSetChanged()

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                    // Handle error
                }
            })
        }
//

        for(i in 0..20){
            playlistp.add(playlistmodel("playlist $i", R.drawable.p5))
        }
        playlistl.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        playlistl.adapter=playlistadapter(this,playlistp)


        // Populate song list (ensure valid URLs)
//        for (i in 1..2) {

handl=Handler(Looper.getMainLooper())
        // Initialize MediaPlayer
        media = MediaPlayer()

        bar=findViewById(R.id.aa)
        // Handle play/pause button click
        playButton.setOnClickListener {
            if (!qq) {
                qq = true
                playButton.setBackgroundResource(R.drawable.pl)
            } else {
                playButton.setBackgroundResource(R.drawable.baseline_pause_24)
                qq = false
            }
            if (media.isPlaying) {
                media.pause()
            } else {
                media.start()
                updateseek()
            }
        }




        bar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p2){
                    media.seekTo(p1)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
                iuser=true
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                iuser=false
                media.seekTo(bar!!.progress)
            }

        })
    }

    // Function to play the song
    fun sing(songFile: String, songTitle: String, songImage: String,songvideo:String) {
        playButton.setBackgroundResource(R.drawable.baseline_pause_24)
        // Update UI with song details
        Glide.with(this)
            .load(songImage)
            // Optional: add a placeholder while the image is loading
//            .error(R.drawable.p4)              // Optional: add an error image in case loading fails
            .into(image)
        text.text = songTitle

Log.d("songurl",songFile)
        // Reset and set up MediaPlayer
        media.reset()
        media.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            val songUri = Uri.parse(songFile)
            media.setDataSource(this, songUri)
            // Prepare media asynchronously
            media.prepareAsync()
            media.setOnPreparedListener {
                bar.max=media.duration
                // Start playing when prepared
                media.start()
                updateseek()

            }
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle errors (e.g., show a toast or update UI)
            text.text = "Error playing song"
        }
        val qqq:Button=findViewById(R.id.vi)
        qqq.setOnClickListener{
            media.pause()
            Intent(this,video::class.java).also {
                it.putExtra("songvideo",songvideo)
                startActivity(it)
            }
        }

    }
fun updateseek(){
    handl.postDelayed(object :Runnable{
        override fun run() {
            if(media.isPlaying ||!iuser){
                bar.progress=media.currentPosition
            }
            handl.postDelayed(this,1000)
        }

    },1000)

}
    override fun onDestroy() {
        super.onDestroy()
        if (::media.isInitialized) {
            media.release() // Release media resources
        }
    }
}