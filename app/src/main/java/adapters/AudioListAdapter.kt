package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.happydevelopers.soundrecorderv2.R
import kotlinx.android.synthetic.main.view_item_saved_records.view.*
import java.io.File
import java.io.FileInputStream
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
        val fileInputStream = FileInputStream(file)

        val prop = Properties()

        prop.load(fileInputStream)


        holder.itemView.textView_item_saved_records_title.text = file.name
        holder.itemView.textView_item_saved_records_duration.text = prop.getProperty("Created")

        fileInputStream.close()

    }
}

class CustomViewHolder(v: View) : RecyclerView.ViewHolder(v) {

}