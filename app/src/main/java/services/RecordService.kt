package services

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Environment
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_record_tab.*
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by miguelcrespo on 10/2/18.
 */

class RecordService : Service() {
    private val mediaRecorder: MediaRecorder = MediaRecorder()
    private var file: File? = null
    private var fileName: String? = ""

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startRecording()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        stopRecording()
    }

    private fun startRecording() {
        // Just testing what happens if I start to record an audio in the fragment and without permission
        if (isExternalStorageWritable()) {
            val mainDir = File(Environment.getExternalStorageDirectory().absolutePath + "/com.happydevelopers.soundRecorderV2/")

            if (!mainDir.exists()) {
                mainDir.mkdir()
            }

            fileName = Environment.getExternalStorageDirectory().absolutePath + "/com.happydevelopers.soundRecorderV2/" + generateFileName() + ".mp4"

            file = File(fileName)

            mediaRecorder.setAudioChannels(1)
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder.setOutputFile(fileName)

            try {
                mediaRecorder.prepare()
                mediaRecorder.start()

            } catch (e: IOException) {
                Log.e("ERROR", "prepare() failed")
            }
        } else {
            Log.d(LOG_TAG, "There is a problem with the MEDIA")
        }
    }

    private fun stopRecording() {
        mediaRecorder.stop()
        mediaRecorder.release()

        Toast.makeText(applicationContext, "File saved in $fileName", Toast.LENGTH_LONG).show()
        fileName = ""
    }

    /* Checks if external storage is available for read and write */
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /* Checks if external storage is available to at least read */
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    private fun generateFileName(): String {
        val today = Calendar.getInstance()

        return "record_" + SimpleDateFormat("d_M_Y", Locale.US).format(today.time) + '_' + SimpleDateFormat("HH_mm_ss", Locale.US).format(today.time)
    }

    companion object {
        val LOG_TAG = RecordService::class.java.simpleName
    }
}