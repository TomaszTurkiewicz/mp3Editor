package com.tt.mp3editor

import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.tt.mp3editor.`class`.AudioModel
import kotlinx.android.synthetic.main.activity_edit_mp3.*

class EditMP3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_mp3)

        val path = intent.getStringExtra("path")
        val audioModel:AudioModel? = getSongFromDevice(this,path)

        song_name.text = audioModel?.title
    }

    private fun getSongFromDevice(context:Context, path: String?): AudioModel? {
        val selection:String = MediaStore.Audio.Media.IS_MUSIC + " !=0"
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.AudioColumns.ARTIST)
        val c: Cursor? = context.contentResolver.query(uri,projection, selection, null,null)

        if(c!=null) {
            while (c.moveToNext()) {
                if (c.getString(0).equals(path)) {
                    val id = c.getString(0)
                    val name = c.getString(1)
                    val album = c.getString(2)
                    val artist = c.getString(3)
                    return AudioModel(
                        path = id,
                        title = name,
                        artist = artist,
                        album = album,
                        icon = null
                    )
                } else {
                    //do nothing
                }
            }
            c.close()
        }
        return null
    }
}