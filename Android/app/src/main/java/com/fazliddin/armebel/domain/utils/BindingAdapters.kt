package com.fazliddin.armebel.domain.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fazliddin.armebel.R
import com.fazliddin.armebel.presentation.common.FurnituresListAdapter
import com.fazliddin.armebel.data.database.models.LikedFurniture
import com.fazliddin.armebel.data.remote.dto.BaseFurniture
import com.fazliddin.armebel.data.remote.dto.Category
import com.fazliddin.armebel.data.remote.dto.TypeFurniture
import com.fazliddin.armebel.presentation.ui.categories.AllCategoriesListAdapter
import com.fazliddin.armebel.presentation.ui.startscreen.CategoriesListAdapter
import com.fazliddin.armebel.presentation.ui.types.TypesListAdapter
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import com.fazliddin.armebel.data.database.models.CartFurniture
import com.fazliddin.armebel.presentation.ui.alltypes.AllTypesListAdapter
import com.fazliddin.armebel.presentation.ui.cart.CartListAdapter
import com.google.android.material.button.MaterialButton

@BindingAdapter("furnitureListData")
fun bindFurnitureListData(recyclerView: RecyclerView, data: List<BaseFurniture>?) {
    val adapter = recyclerView.adapter as FurnituresListAdapter
    adapter.submitList(data)
}

@BindingAdapter("cartListData")
fun bindCartFurnitureListData(recyclerView: RecyclerView, data: List<CartFurniture>?) {
    val adapter = recyclerView.adapter as CartListAdapter
    adapter.submitList(data)
}

@BindingAdapter("categoriesListData")
fun bindCategoriesListData(recyclerView: RecyclerView, data: List<Category>?) {
    val adapter = recyclerView.adapter as CategoriesListAdapter
    adapter.submitList(data)
}

@BindingAdapter("allCategoriesListData")
fun bindAllCategoriesListData(recyclerView: RecyclerView, data: List<Category>?) {
    val adapter = recyclerView.adapter as AllCategoriesListAdapter
    adapter.submitList(data)
}

@BindingAdapter("allTypesListData")
fun bindAllTypesListData(recyclerView: RecyclerView, data: List<TypeFurniture>?) {
    val adapter = recyclerView.adapter as AllTypesListAdapter
    adapter.submitList(data)
}

@BindingAdapter("typesListData")
fun bindTypesListData(recyclerView: RecyclerView, data: List<TypeFurniture>?) {
    val adapter = recyclerView.adapter as TypesListAdapter
    adapter.submitList(data)
}

@BindingAdapter("likeBtnTint")
fun setLikedTint(view: TextView, likedFurniture: LikedFurniture?) {
    if (likedFurniture == null) return

    if (likedFurniture.isLiked) {
        (view as MaterialButton).setIconTintResource(R.color.heart_icon_color)
    } else {
        (view as MaterialButton).setIconTintResource(R.color.white)
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide
        .with(view.context)
        .load(url)
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_broken_image)
        .into(view)
}

@BindingAdapter("textPrice")
fun setPrice(view: TextView, price: Double?) {
    val formatSybols = DecimalFormatSymbols(Locale.getDefault())
    formatSybols.groupingSeparator = ' '
    formatSybols.decimalSeparator = ','
    val myFormatter = DecimalFormat("#,##0.00", formatSybols)
    myFormatter.groupingSize = 3
    val priceString = myFormatter.format(price)

    view.text = "$priceString so'm"
}