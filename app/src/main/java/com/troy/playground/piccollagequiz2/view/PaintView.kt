package com.troy.playground.piccollagequiz2.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.troy.playground.piccollagequiz2.PathInfo
import com.troy.playground.rxbus.PathUpdater

class PaintView : View {

    companion object {
        const val MODE_PENCIL = 0
        const val MODE_ERASER = 1

        const val TOUCH_TOLERANCE = 4f
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private var pencilPaint = Paint()
    private var eraserPaint = Paint()
    private var canvas: Canvas? = null
    private var path = Path()
    private var currentPathInfo = PathInfo(path, pencilPaint)
    private var pathInfos = ArrayList<PathInfo>()

    private var posX = 0f
    private var posY = 0f

    init {
        setupPencilPaint()
        setupEraserPaint()
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT)

        for (p in pathInfos) {
            canvas.drawPath(p.path, p.paint)
        }

        canvas.drawPath(currentPathInfo.path, currentPathInfo.paint)
    }

    private fun touchStart(x: Float, y: Float) {
        currentPathInfo.path?.reset()
        currentPathInfo.path?.moveTo(x, y)
        posX = x
        posY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - posX)
        val dy = Math.abs(y - posY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            currentPathInfo.path?.quadTo(posX, posY, (x + posX) / 2, (y + posY) / 2)
            posX = x
            posY = y
        }
    }

    private fun touchUp() {
        currentPathInfo.path?.lineTo(posX, posY)
        // commit the path to our offscreen
        canvas?.drawPath(currentPathInfo.path, currentPathInfo.paint)
        pathInfos.add(currentPathInfo)

        PathUpdater.getInstance().notify(currentPathInfo)
        currentPathInfo = PathInfo(Path(), currentPathInfo.paint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }

    private fun setupPencilPaint() {
        pencilPaint.color = Color.RED
        pencilPaint.style = Paint.Style.STROKE
        pencilPaint.strokeWidth = 20f
    }

    private fun setupEraserPaint() {
        eraserPaint.style = Paint.Style.STROKE
        eraserPaint.strokeWidth = 40f
        eraserPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    fun switchMode(mode : Int) {
        when(mode) {
            MODE_PENCIL ->{
                currentPathInfo.paint = pencilPaint
            }
            MODE_ERASER ->{
                currentPathInfo.paint = eraserPaint
            }
        }
    }

    fun updatePathInfos(pathInfos: ArrayList<PathInfo>) {
        this.pathInfos = pathInfos
        invalidate()
    }
}