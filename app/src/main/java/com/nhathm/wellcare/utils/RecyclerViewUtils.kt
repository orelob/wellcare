package com.nhathm.wellcare.utils.view

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.nhathm.wellcare.utils.getCompatActivity

fun RecyclerView.adapter(
    adapter: RecyclerView.Adapter<*>?,
    isSnap: Boolean? = null,
    hasFixed: Boolean? = null
) {
    layoutManager =
        LinearLayoutManager(context.getCompatActivity()!!, LinearLayoutManager.HORIZONTAL, false)
    this.adapter = adapter
    if (isSnap == true) {
        val snapHelper = LinearSnapHelper()
        if (onFlingListener == null) snapHelper.attachToRecyclerView(this)
    }
    if (hasFixed == true) setHasFixedSize(true)
}

fun RecyclerView.adapter(
    adapter: RecyclerView.Adapter<*>?,
    spanCount: Int,
    hasFixed: Boolean? = null
) {
    this.layoutManager = GridLayoutManager(context.getCompatActivity(), spanCount)
    this.adapter = adapter
    if (hasFixed == true) setHasFixedSize(true)
}

fun RecyclerView.adapterGrid(
    adapter: RecyclerView.Adapter<*>?,
    numOfColumns: Float,
    hasFixed: Boolean? = null
) {
    this.layoutManager = GridLayoutManager(
        context.getCompatActivity()!!,
        context.getCompatActivity()!!.numOfColumns(numOfColumns)
    )
    this.adapter = adapter
    if (hasFixed == true) setHasFixedSize(true)
}

private fun Context.numOfColumns(columnWidthDp: Float): Int {
    val displayMetrics = resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    return (screenWidthDp / columnWidthDp + 0.5).toInt()
}
