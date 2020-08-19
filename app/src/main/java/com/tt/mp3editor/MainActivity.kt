package com.tt.mp3editor


import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tt.mp3editor.`class`.AudioModel
import com.tt.mp3editor.adapter.MP3Adapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.single_row.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KTypeProjection.Companion.STAR


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val my_list = getAllAudioFromDevice(this)

        mp3_list_recyclerView.layoutManager = LinearLayoutManager(this)
        mp3_list_recyclerView.adapter = MP3Adapter(my_list,this)

    }


    private fun getAllAudioFromDevice(context: Context): ArrayList<AudioModel> {
        var tempAudioModel = ArrayList<AudioModel>()

        val selection:String = MediaStore.Audio.Media.IS_MUSIC + " !=0"
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Audio.AudioColumns.DATA,
                                                MediaStore.Audio.AudioColumns.TITLE,
                                                MediaStore.Audio.AudioColumns.ALBUM,
                                                MediaStore.Audio.AudioColumns.ARTIST)
        val c:Cursor? = context.contentResolver.query(uri,projection, selection, null,null)

        if(c!=null){

            while (c.moveToNext()){
                val path = c.getString(0)
                val name = c.getString(1)
                val album = c.getString(2)
                val artist = c.getString(3)
                val audioModel = AudioModel(path = path, title = name, artist = artist,album = album, icon = null)
                tempAudioModel.add(audioModel)

            }
            c.close()
        }
        return tempAudioModel
    }

}



/*
TODO
 1) read mp3 from phone
 2) present mp3 file
 3) cut from... to...
 4) save new mp3 file as a new file
 5) show admob after succesfully saved
 */