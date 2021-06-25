package com.example.recyclerview30

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import com.example.recyclerview30.databinding.ActivityMain2Binding
import java.text.DateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cal: Calendar = Calendar.getInstance()
        val date: String = DateFormat.getDateInstance().format(cal.time)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ErrorTextView.setVisibility(View.INVISIBLE)
        binding.textView4.setText("Add expense")
        binding.textView2.setText("Date: $date")

        //Edit
        var args = intent.extras
        if (args!!.get("edit") == 1) {
            binding.editTextAmt.setText(args!!.get("amt").toString())
            binding.editTextDis.setText(args!!.get("dis").toString())
            binding.autoCompleteTextView.setText(args!!.get("cur").toString())
            binding.autoCompleteTextView2.setText(args!!.get("op").toString())
            binding.textView2.setText("Date: $date")
            binding.textView4.setText("Edit")
        }

        //Drop down menu curs
        val curs = resources.getStringArray(R.array.currancies)
        val adapter_curs = ArrayAdapter(this, R.layout.drop_down, curs)

        with(binding.autoCompleteTextView){
            setAdapter(adapter_curs)
        }

        //Drop down menu ops
        val ops = resources.getStringArray(R.array.ops)
        val adapter_ops = ArrayAdapter(this, R.layout.drop_down, ops)

        with(binding.autoCompleteTextView2){
            setAdapter(adapter_ops)
        }

        //Button
        binding.imageButton.setOnClickListener{
            if (binding.editTextAmt.text.toString() != "" && binding.editTextDis.text.toString() != "") {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("amt", binding.editTextAmt.text.toString());
                intent.putExtra("dis", binding.editTextDis.text.toString());
                intent.putExtra("cur", binding.autoCompleteTextView.text.toString());
                intent.putExtra("op", binding.autoCompleteTextView2.text.toString());
                intent.putExtra("date", binding.textView2.text.toString());
                intent.putExtra("edit", args!!.get("edit").toString().toInt())
                intent.putExtra("pos", args!!.get("pos").toString().toInt());
                startActivity(intent)
            }else {
                binding.ErrorTextView.setVisibility(View.VISIBLE)
            }
        }
    }


}