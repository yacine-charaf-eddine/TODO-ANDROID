package com.example.todo.commons

import android.content.Context
import android.graphics.PointF
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class SnappingLinearLayoutManager(context: Context): LinearLayoutManager(context) {

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        val context = recyclerView?.context
        if (context != null) {
            val linearSmoothScroller: LinearSmoothScroller =
                object : LinearSmoothScroller(context) {
                    override fun getVerticalSnapPreference(): Int {
                        return -1
                    }

                    override fun computeScrollVectorForPosition(i2: Int): PointF? {
                        return super.computeScrollVectorForPosition(i2)
                    }
                }
            linearSmoothScroller.targetPosition = position
            startSmoothScroll(linearSmoothScroller)
        }
    }
}