package com.sultan.recyclerviewmultipleapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context: Context, private val userList: ArrayList<UserData>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        var name: TextView
        var mbNum: TextView
        var mMenus: ImageView

        /* TODO:    init block is must when we have to further work on one of above views, as now we have to use
            mMenus for setOnClickListener
         */
        init {

            name = item.findViewById<TextView>(R.id.title)
            mbNum = item.findViewById<TextView>(R.id.subTitle)
            mMenus = item.findViewById(R.id.mMenus)
            mMenus.setOnClickListener {
                popupMenus(it)
                Log.e("it", "menu is clicked ")
            }

        }



      private fun popupMenus(v:View) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(context,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.edit->{
                        val v = LayoutInflater.from(context).inflate(R.layout.add_item,null)
                        val name = v.findViewById<EditText>(R.id.etUserName)
                        val number = v.findViewById<EditText>(R.id.etUserNo)
                        AlertDialog.Builder(context)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.userName = name.text.toString()
                                position.userMb = number.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(context,"User Information is Edited",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    R.id.delete->{
                        /**set delete*/
                        AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(context,"Deleted this Information",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else-> true
                }
            }
            popupMenus.show()
  /* TODO: -------------------------------------------------------------------------------------
      Following code has to be memorized and used exact same fields ("mPopup") for getting
      icons of the menu items. but if don't want to use icons for menus then following code is not
      necessary to memorize.
      -------------------------------------------------------------------------------------------
    */
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_list_item, parent, false)
        return UserViewHolder(view)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.name.text = user.userName
        holder.mbNum.text = user.userMb
        holder.item.setOnClickListener {
            Toast.makeText(context, user.userName, Toast.LENGTH_SHORT).show()
            Log.e("user", "you clicked: ${user.userName}")
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}