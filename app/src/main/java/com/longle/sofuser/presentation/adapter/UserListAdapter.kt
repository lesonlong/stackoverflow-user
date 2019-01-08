package com.longle.sofuser.presentation.adapter

import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.domain.model.User
import com.squareup.picasso.Picasso
import com.longle.sofuser.databinding.ListItemUserBinding

/**
 * UserListAdapter is used to display data for each user in the list
 */
class UserListAdapter(users: ObservableList<User>) :
    ObservableRecyclerViewAdapter<User, UserListAdapter.Holder>(users) {

  lateinit var onImageClickedListener: (user: User) -> Unit

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
    return Holder(
        ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemClickListener, onImageClickedListener)
  }

  override fun onBindViewHolder(holder: Holder, position: Int) {
    holder.bind(getItem(position))
  }

  class Holder(
      private val binding: ListItemUserBinding,
      private val onItemClickListener: ((item: Any) -> Unit)?,
      private val onImageClickedListener: ((user: User) -> Unit)?) :
      RecyclerView.ViewHolder(binding.root) {

    private lateinit var user: User

    fun bind(user: User) {
      this.user = user

      Picasso.get().load(user.coverImgUrl).resize(100, 50).into(binding.image)
      binding.name.text = user.name
      binding.description.text = user.description
      binding.minutes.text = user.status

      binding.root.setOnClickListener { onItemClickListener?.invoke(user) }
      binding.image.setOnClickListener { onImageClickedListener?.invoke(user) }
    }
  }
}
