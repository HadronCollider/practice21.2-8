package com.example.recyclerview30

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View.FOCUS_LEFT
import android.view.View.INVISIBLE
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.*
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Character.DIRECTIONALITY_LEFT_TO_RIGHT
import java.lang.Character.getType
import java.lang.reflect.Type
import java.nio.file.Files.delete

var IsFirst: Int = 1
val list = ArrayList<Item>()
var cnt = 0

var db = Database(list, cnt)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<FloatingActionButton>(R.id.FloatBut)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        val intent_act = Intent(this, MainActivity2::class.java)

        db.cnt += 1

        button.setOnClickListener{
            intent_act.putExtra("edit", 0);
            intent_act.putExtra("pos", -1);
            startActivity(intent_act)
        }

        var args = intent.extras

        //Adding an element
        if (IsFirst == 1)
        {
            IsFirst = 0
            loadData()
        }
        else {
            if (args!!.get("edit").toString().toInt() == 0) {
                db.Add(Item(args!!.get("amt").toString(), args!!.get("cur").toString(), args!!.get("dis").toString(), args!!.get("op").toString(), args!!.get("date").toString()));

                if (args!!.get("op").toString() == "I owe")
                    db.Amt_Owe += args!!.get("amt").toString().toInt()
                else if (args!!.get("op").toString() == "I lent") {
                    db.Amt_Lent += args!!.get("amt").toString().toInt()
                } else if (args!!.get("op").toString() == "Expense") {
                    db.Amt_Exp += args!!.get("amt").toString().toInt()
                }
            }
            else
            {
                db.list[args!!.get("pos").toString().toInt()].OP = args!!.get("op").toString()
                db.list[args!!.get("pos").toString().toInt()].amt = args!!.get("amt").toString()
                db.list[args!!.get("pos").toString().toInt()].cur = args!!.get("cur").toString()
                db.list[args!!.get("pos").toString().toInt()].dis = args!!.get("dis").toString()
                db.list[args!!.get("pos").toString().toInt()].date = args!!.get("date").toString()
            }
        }

        //Swipe and click
        val adapter = MyRecyclerViewAdapter(db.List())
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : MyRecyclerViewAdapter.onItemClickListner{
            override fun onItemClick(pos: Int) {
                intent_act.putExtra("op", db.list[pos].OP);
                intent_act.putExtra("cur", db.list[pos].cur);
                intent_act.putExtra("amt", db.list[pos].amt);
                intent_act.putExtra("dis", db.list[pos].dis);
                intent_act.putExtra("date", db.list[pos].date);
                intent_act.putExtra("pos", pos);
                intent_act.putExtra("edit", 1);
                startActivity(intent_act)
            }
        })

        val SwipeDel = object : SwipeToDelete(){
            override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
            ) {
                db.Del(viewHolder.adapterPosition)
                recyclerView.adapter = adapter
            }
        }
        val touchHelper = ItemTouchHelper(SwipeDel)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    fun saveData() {
        val sharedPreferences : SharedPreferences  = getSharedPreferences("shared preferences", MODE_PRIVATE);
        val editor : SharedPreferences.Editor = sharedPreferences.edit();
        val gson : Gson = Gson();
        val json : String = gson.toJson(db);
        editor.putString("task list", json);
        editor.apply();
    }

    fun loadData() : Int {
        val sharedPreferences : SharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        val gson : Gson = Gson();
        val json = sharedPreferences.getString("task list", null);
        val type = object : TypeToken<Database>() {}.type
        if (json != null)
          db = Gson().fromJson<Database>(json, type)
        return 1;
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        saveData()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

