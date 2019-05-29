package com.troy.playground.piccollagequiz2.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

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
    private val path = Path()
    private var eraserMode = false

    private var posX = 0f
    private var posY = 0f

    init {
        setupPaint()
        setupEraser()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT)
        canvas.drawBitmap(bitmap, 0f, 0f, pencilPaint)
        if(!eraserMode) {
            canvas.drawPath(path, pencilPaint)
        }

    }

    private fun touchStart(x: Float, y: Float) {
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

            if (eraserMode) {
                canvas?.drawPath(path, eraserPaint)
            }
        }
    }

    private fun touchUp() {
        path.lineTo(posX, posY)
        // commit the path to our offscreen
        if (eraserMode) {
            canvas?.drawPath(path, eraserPaint)
        } else {
            canvas?.drawPath(path, pencilPaint)
        }

        // kill this so we don't double draw
        path.reset()
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
                eraserMode = false
            }
            MODE_ERASER ->{
                eraserMode = true
            }
        }
    }

    fun drawPath() {

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