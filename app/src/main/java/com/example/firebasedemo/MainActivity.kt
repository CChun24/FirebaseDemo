package com.example.firebasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    val db = FirebaseDatabase.getInstance()
    val ref = db.getReference("Student")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd : Button = findViewById(R.id.buttonAdd)
        btnAdd.setOnClickListener{
            val id = findViewById<TextView>(R.id.tfId).text.toString()
            val name = findViewById<TextView>(R.id.tfName).text.toString()
            val programme = findViewById<TextView>(R.id.tfProgramme).text.toString()

            ref.child(id).child("Name").setValue(name)
            ref.child(id).child("Programme").setValue(programme)
        }

        val getData = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()

                for(student in snapshot.children){
                    var name = student.child("Name").value

                    sb.append("$name \n")
                }

                findViewById<TextView>(R.id.tvView).text = sb.toString()

            }
        }

        val btnGet : Button = findViewById(R.id.btnGet)
        btnGet.setOnClickListener{
//            val query = ref.orderByChild("Programme").equalTo("RSF")
            ref.addValueEventListener(getData)
            ref.addListenerForSingleValueEvent(getData)
        }
    }
}