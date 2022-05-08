package city.samaritan.pokemongo.view

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import city.samaritan.pokemongo.R

class GridSpaceItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var gridSpace = context.resources.getDimensionPixelSize(R.dimen.grid_padding)
    private var spanCount = 0

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapter = parent.adapter ?: return
        val manager = parent.layoutManager as GridLayoutManager?
        if (spanCount == 0) {
            spanCount = manager!!.spanCount
        }
        val position = parent.getChildAdapterPosition(view)
        if (position < adapter.itemCount) {
            val spanLookup = manager!!.spanSizeLookup
            val isFirst = spanLookup.getSpanIndex(position, spanCount) == 0
            val isLast = spanLookup.getSpanIndex(position, spanCount) == spanCount - 1
            when {
                isFirst -> {
                    outRect.left = gridSpace
                    outRect.right = gridSpace / 2
                }
                isLast -> {
                    outRect.left = gridSpace / 2
                    outRect.right = gridSpace
                }
                else -> {
                    outRect.left = gridSpace / 2
                    outRect.right = gridSpace / 2
                }
            }
            outRect.bottom = gridSpace
        }
    }
}