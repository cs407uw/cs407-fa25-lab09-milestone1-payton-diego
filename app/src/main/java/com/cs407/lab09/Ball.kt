package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        // Acceleration values
        val a0X = accX
        val a0Y = accY
        val a1X = xAcc
        val a1Y = yAcc

        // Calculate velocity
        val v0X = velocityX
        val v0Y = velocityY
        velocityX = v0X + 0.5f * (a1X + a0X) * dT
        velocityY = v0Y + 0.5f * (a1Y + a0Y) * dT

        // Calculate distance traveled
        val distanceX = v0X * dT + (1.0f / 6.0f) * (dT * dT) * (3 * a0X + a1X)
        val distanceY = v0Y * dT + (1.0f / 6.0f) * (dT * dT) * (3 * a0Y + a1Y)

        // Update position
        posX += distanceX
        posY += distanceY

        // Update acceleration
        accX = a1X
        accY = a1Y

        checkBoundaries()
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // Left
        if (posX < 0) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }

        // Right
        if (posX > backgroundWidth - ballSize) {
            posX = backgroundWidth - ballSize
            velocityX = 0f
            accX = 0f
        }

        // Top
        if (posY < 0) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }

        // Bottom
        if (posY > backgroundHeight - ballSize) {
            posY = backgroundHeight - ballSize
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // (Reset posX, posY, velocityX, velocityY, accX, accY, isFirstUpdate)
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f
        isFirstUpdate = true
    }
}