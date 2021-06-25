package com.example.recyclerview30

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color.argb
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

data class Item(var amt: String, var cur: String, var dis: String, var OP: String, var date: String)

class MyRecyclerViewAdapter(private val list: List<Item>) : RecyclerView.Adapter<MyViewHolder>() {

    private lateinit var mListner: onItemClickListner

    interface  onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListner){
        mListner = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.my_view_holder, parent, false)
        return MyViewHolder(view, mListner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.amtView.text = list[position].amt + list[position].cur
        holder.disView.text = list[position].dis
        holder.dateView.text = list[position].date
        if (list[position].OP == "Expense")
            holder.color.setStrokeColor(argb(0, 42,24,220))
        else if (list[position].OP == "I lent")
            holder.color.setStrokeColor(argb(255, 0,0,255))
        else if (list[position].OP == "I owe")
            holder.color.setStrokeColor(argb(255, 255,0,0))
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class MyViewHolder(view: View, listener: MyRecyclerViewAdapter.onItemClickListner) : RecyclerView.ViewHolder(view) {

    val amtView: TextView = view.findViewById(R.id.amt)
    val disView: TextView = view.findViewById(R.id.dis)
    val dateView: TextView = view.findViewById(R.id.textView3)
    val color: MaterialCardView = view.findViewById(R.id.MCV)

    init {
       view.setOnClickListener {
           listener.onItemClick(adapterPosition)
       }

    }
}

abstract class SwipeToDelete: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}

class Database (list: ArrayList<Item>, cnt: Int) {

    var list = ArrayList<Item>()
    public var cnt: Int = 0
    public var Amt_Owe = 0
    public var Amt_Lent = 0
    public var Amt_Exp = 0

    public fun Add (item : Item) {
        list.add(0, item)
    }

    public fun Del (index : Int) {
        list.removeAt(index)
    }

    public fun List () : ArrayList<Item> {
        return list
    }

    public fun Cnt () : Int {
        return cnt
    }
}
