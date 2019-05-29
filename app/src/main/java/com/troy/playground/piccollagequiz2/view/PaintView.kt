package com.troy.playground.piccollagequiz2.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.troy.playground.piccollagequiz2.PathInfo

class PaintView : View {

    companion object {
        const val MODE_PENCIL = 0
        const val MODE_ERASER = 1

        const val TOUCH_TOLERANCE = 4f
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var pencilPaint = Paint()
    private var eraserPaint = Paint()
    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private var path = Path()
    private var currentMode = MODE_PENCIL

    private var posX = 0f
    private var posY = 0f
    private val pathInfos = ArrayList<PathInfo>()
    private val undonePathInfos = ArrayList<PathInfo>()


    init {
        setupPaint()
        setupEraser()
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT)

        for (p in pathInfos) {
            canvas.drawPath(p.path, p.paint)
        }

        if (currentMode == MODE_PENCIL) {
            canvas.drawPath(path, pencilPaint)
        } else {
            canvas.drawPath(path, eraserPaint)
        }
    }

    private fun touchStart(x: Float, y: Float) {
        undonePathInfos.clear()
        path.reset()
        path.moveTo(x, y)
        posX = x
        posY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - posX)
        val dy = Math.abs(y - posY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(posX, posY, (x + posX) / 2, (y + posY) / 2)
            posX = x
            posY = y
        }
    }

    private fun touchUp() {
        path.lineTo(posX, posY)
        // commit the path to our offscreen
        if (currentMode == MODE_PENCIL) {
            canvas?.drawPath(path, pencilPaint)
            pathInfos.add(PathInfo(path, pencilPaint))
        } else {
            canvas?.drawPath(path, eraserPaint)
            pathInfos.add(PathInfo(path, eraserPaint))
        }
        path = Path()
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

    public fun switchMode(mode : Int) {
        when(mode) {
            MODE_PENCIL ->{
                currentMode = MODE_PENCIL
            }
            MODE_ERASER ->{
                currentMode = MODE_ERASER
            }
        }
    }

    fun Undo() {
        if (pathInfos.size > 0)
        {
            undonePathInfos.add(pathInfos[pathInfos.size-1])
            pathInfos.removeAt(pathInfos.size-1)
            invalidate()
        }
    }

    fun Redo() {
        if (undonePathInfos.size > 0)
        {
            pathInfos.add(undonePathInfos[undonePathInfos.size-1])
            undonePathInfos.removeAt(undonePathInfos.size-1)
            invalidate()
        }
    }

    private fun setupPaint() {
        pencilPaint.color = Color.RED
        pencilPaint.style = Paint.Style.STROKE
        pencilPaint.strokeWidth = 20f
    }

    private fun setupEraser() {
        eraserPaint.style = Paint.Style.STROKE
        eraserPaint.strokeWidth = 40f
        eraserPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
}