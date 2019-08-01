package examples.my.android.contacts.recyclerview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.graphics.drawable.Drawable
import android.R.attr.right
import android.R.attr.left
import android.graphics.Rect
import android.util.TypedValue
import android.view.View


class ItemDecorator : RecyclerView.ItemDecoration {

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }

    private var divider: Drawable? = null
    private var context: Context? = null
    private var margin: Int = 0

    constructor(context: Context, margin: Int) {
        this.context = context;
        this.margin = margin;
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        super.onDrawOver(c, parent, state)
        drawDecor(c, parent)
    }

    private fun drawDecor(c: Canvas, parent: RecyclerView) {
        val left = parent.getPaddingLeft()
        val right = parent.getWidth() - parent.getPaddingRight()
        val childsCount = parent.childCount
        for (i in 0 until childsCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider!!.getIntrinsicHeight()
            divider!!.setBounds(left + dpToPx(margin), top, right, bottom)
            divider!!.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, divider!!.getIntrinsicHeight());
    }

    private fun dpToPx(dp: Int): Int {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context!!.resources.displayMetrics))
    }
}