package com.sultan.recyclerviewmultipleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.sultan.recyclerviewmultipleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var userList = mutableListOf<UserData>()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter(this, userList as ArrayList<UserData>)

        binding.btnAdd.setOnClickListener {
            addUserInfo()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = adapter
    }

    private fun addUserInfo() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.add_item, null)

        val userName = view.findViewById<EditText>(R.id.etUserName)
        val userNo = view.findViewById<EditText>(R.id.etUserNo)

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(view)
        alertDialog.setPositiveButton("OK") { dialog, _ ->
            val name = userName.text.toString()
            val number = userNo.text.toString()
            userList.add(UserData("Name: $name", "Mobile Number: $number"))
            adapter.notifyDataSetChanged()
            dialog.dismiss()
            Toast.makeText(this, "User Added Successfully", Toast.LENGTH_SHORT).show()
        }
        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }
        alertDialog.create()
        alertDialog.show()
    }

}