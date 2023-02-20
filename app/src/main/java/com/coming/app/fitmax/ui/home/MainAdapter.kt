package com.coming.app.fitmax.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coming.app.fitmax.R
import com.coming.app.fitmax.data.DataHome

class MainAdapter(
//    private val typeOneList: MutableList<String>,
    private val typeTwoList: MutableList<DataHome>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return when (viewType) {
            0 -> {
                val view = inflater.inflate(R.layout.herder_list_home, parent, false)
                TypeOneViewHodel(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.home_item, parent, false)
                TypeTwoViewHodel(view)
            }
        }

    }

    override fun getItemCount(): Int {
        return if (typeTwoList.count() > 0) typeTwoList.count() else 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.apply {
            when (holder) {
                is TypeOneViewHodel -> {
                    holder.bind()
                }
                is TypeTwoViewHodel -> {
                    holder.bind(
                        DataHome(
                            typeTwoList[position].complete,
                            typeTwoList[position].with,
                            typeTwoList[position].number
                        )
                    )
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> 0
            else -> 1
        }
    }

    class TypeOneViewHodel(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val surfaceHeaderHome: SurfaceView = itemView.findViewById(R.id.surface_home)
        val textToday: TextView = itemView.findViewById(R.id.text_header_home_today)

        fun bind() {
            with(itemView) {

//                var bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
//                var canvas = Canvas(bitmap)
                var paintText = android.graphics.Paint().apply {
                    color = Color.RED
                    style = android.graphics.Paint.Style.FILL
                    textSize = 100f
                }
//
////                val surfaceCanvas = surfaceHeaderHome.holder.lockCanvas()
////                surfaceHeaderHome.holder.lockCanvas()?.let { canvas->
////                    canvas.drawText("HaiHoag", 20f, 60f,paintText)
////                    canvas.drawCircle(100f,200f,20f,paintText)
////                }
//                canvas.drawText("HaiHoag", 20f, 60f, paintText)
//                canvas.drawCircle(50f, 50f, 20f, paintText)

//                canvas.drawCircle(50f, 50f, 20f, paintText)
//                Log.d("canvas==>", canvas.toString())
//                surfaceHeaderHome.holder.unlockCanvasAndPost(canvas)
//                surfaceHeaderHome.holder.unlockCanvasAndPost(canvas)
//                textToday.text = "hai Hoang"
            }
        }

    }

    class TypeTwoViewHodel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textComplete: TextView = itemView.findViewById(R.id.text_complete)
        var textWith: TextView = itemView.findViewById(R.id.text_with)
        var text113: TextView = itemView.findViewById(R.id.text_113)


        fun bind(data: DataHome) {
            with(itemView) {
                textWith.text = data.with
                textComplete.text = data.complete
                text113.text = data.number.toString()
            }
        }

    }

}