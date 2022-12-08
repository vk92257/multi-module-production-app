package com.bjs.pdanative.presentation.ui.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {

        val position: Int = parent.getChildAdapterPosition(view) // item position
        val column: Int = position % 3
        outRect.left = column * space / 3; // column * ((1f / spanCount) * spacing)
        outRect.right =
            space - (column + 1) * space / 3; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
        if (position >= 3) {
            outRect.top = space; // item top
        }
    }
}