package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.toolbarfragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.R
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.databinding.RvItemBinding
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.users.Users
import java.util.ArrayList

class RVForToolBar(var context: Context, var list: ArrayList<Users>, var listenr: OnItemTouchListener) : RecyclerView.Adapter<RVForToolBar.VH>() {

    interface OnItemTouchListener {
        fun onItemClick(users: Users, position: Int, view: View)
        fun savedClick(users: Users, position: Int, view: View)
    }

    inner class VH(var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(users: Users, position: Int) {
            if (users.isLike == true) {
                binding.saved.setBackgroundResource(R.drawable.circle_green)
                binding.saved.setImageResource(R.drawable.saved_white)
            }
            else {
                binding.saved.setBackgroundResource(R.drawable.circle_white)
                binding.saved.setImageResource(R.drawable.saved_black)
            }

            binding.name.text = users.name
            Glide.with(context).load(users.photo).into(binding.setImage)
            binding.year.text = "(${users.from}-${users.to})"

            binding.saved.setOnClickListener {
                if (users.isLike == true) {
                    users.isLike = false
                    binding.saved.setBackgroundResource(R.drawable.circle_white)
                    binding.saved.setImageResource(R.drawable.saved_black)
                }
                else {
                    users.isLike = true
                    binding.saved.setBackgroundResource(R.drawable.circle_green)
                    binding.saved.setImageResource(R.drawable.saved_white)
                }

                listenr.savedClick(users, position, it)
            }

            itemView.setOnClickListener {
                listenr.onItemClick(users, position, it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}