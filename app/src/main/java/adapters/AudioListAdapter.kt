package adapters

import android.media.MediaMetadataRetriever
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.happydevelopers.soundrecorderv2.R
import kotlinx.android.synthetic.main.view_item_saved_records.view.*
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * Created by miguelcrespo on 10/3/18.
 */

class AudioListAdapter(private val mFiles: ArrayList<File>) : RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return mFiles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // Inflate layout and construct the CustomViewHolder
        val layoutInflater = LayoutInflater.from(parent.context)

        val cellForRow = layoutInflater.inflate(R.layout.view_item_saved_records, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val file = mFiles[position]

        val mmr = MediaMetadataRetriever()

        mmr.setDataSource(file.absolutePath)

        val durationText = formatToHumanReadableDuration(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))

        val df = SimpleDateFormat("yyyyMMdd'T'HHmmss'.'SSS'Z'", Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone("UTC")
        val tdf = SimpleDateFormat("dd/MM/yyyy, hh:mm a", Locale.getDefault())

        val date = df.parse(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE))

        holder.itemView.textView_item_saved_records_title.text = file.name
        holder.itemView.textView_item_saved_records_duration.text = durationText
        holder.itemView.textView_item_saved_records_date.text = tdf.format(date)
    }

    private fun formatToHumanReadableDuration(durationMs: String): String {
        val seconds = durationMs.toDouble() / 1000
        var formatted = ""

        val hours = Math.floor(seconds / 3600)
        val minutes = Math.floor(seconds % 3600 / 60)
        val scs = Math.floor(seconds % 3600 % 60)

        if (hours > 0) {
            formatted += ("0${hours.toInt()}").takeLast(2) + ":"
        }

        if (minutes > 0 || hours > 0) {
            formatted += ("0${minutes.toInt()}").takeLast(2) + ":"
        }

        if (minutes == 0.0 && hours == 0.0) {
            formatted += "00:"
        }

        formatted += ("0${scs.toInt()}").takeLast(2)

        return formatted
    }
}

class CustomViewHolder(v: View) : RecyclerView.ViewHolder(v) {

}