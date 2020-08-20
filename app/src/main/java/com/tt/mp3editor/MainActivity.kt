package com.tt.mp3editor


import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.tt.mp3editor.`class`.AudioModel
import com.tt.mp3editor.adapter.MP3Adapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.single_row.*
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList
import kotlin.reflect.KTypeProjection.Companion.STAR


class MainActivity : AppCompatActivity() {

    private val READ_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check permission and display recyclerview or close
        setupPermissions()

    }

    private fun activateRecyclerView(){
        val my_list = getAllAudioFromDevice(this)
        mp3_list_recyclerView.layoutManager = LinearLayoutManager(this)
        mp3_list_recyclerView.adapter = MP3Adapter(my_list,this)
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if(permission != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Permission to read the storage is required for this app").setTitle("Permission required")
                builder.setPositiveButton("OK"){
                    dialog, id -> makeRequest()
                }
                val dialog = builder.create()
                dialog.show()
            }
            else{
                makeRequest()
            }
        }else{
            activateRecyclerView()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),READ_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            READ_REQUEST_CODE -> {
                if(grantResults.isEmpty()||grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    //TODO another alertdialog with info that app will be closed because of denied permission
                    finish()
                }else{
                    activateRecyclerView()
                }
            }
        }
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
 1) read mp3 from phone - add permission checker and alertdialog with asking for grand permission
 2) check how to repleace DATA in AudioMedia
 3) present mp3 file
 4) cut from... to...
 5) save new mp3 file as a new file
 6) show admob after succesfully saved
 */