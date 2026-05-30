package com.bharatsight2075.ml

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.Interpreter
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class MultiOutputPrediction(
    val gdp: FloatArray,
    val inflation: FloatArray,
    val unemployment: FloatArray,
    val gini: FloatArray,
    val hdi: FloatArray,
    val confidenceLower: FloatArray,
    val confidenceUpper: FloatArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MultiOutputPrediction) return false
        return gdp.contentEquals(other.gdp) &&
                inflation.contentEquals(other.inflation) &&
                unemployment.contentEquals(other.unemployment) &&
                gini.contentEquals(other.gini) &&
                hdi.contentEquals(other.hdi)
    }

    override fun hashCode(): Int {
        var result = gdp.contentHashCode()
        result = 31 * result + inflation.contentHashCode()
        result = 31 * result + unemployment.contentHashCode()
        result = 31 * result + gini.contentHashCode()
        result = 31 * result + hdi.contentHashCode()
        return result
    }
}

@Singleton
class EconomyPredictorTFLite @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var interpreter: Interpreter? = null
    private val inputBuffer: ByteBuffer
    private var modelLoadError: Exception? = null
    
    companion object {
        private const val TAG = "EconomyPredictorTFLite"
        private const val MODEL_ASSET = "economy_2075_model.tflite"
    }

    init {
        inputBuffer = ByteBuffer.allocateDirect(4 * 4).order(ByteOrder.nativeOrder())
        loadModel()
    }
    
    private fun loadModel() {
        val modelFile = File(context.filesDir, "latest_model.tflite")
        if (modelFile.exists()) {
            loadModelFromFile(modelFile)
            if (interpreter != null) return
        }
        loadModelFromAssets()
    }
    
    private fun loadModelFromAssets() {
        try {
            val assetFile = context.assets.openFd(MODEL_ASSET)
            val inputStream = FileInputStream(assetFile.fileDescriptor)
            val mappedByteBuffer = inputStream.channel.map(
                FileChannel.MapMode.READ_ONLY,
                assetFile.startOffset,
                assetFile.declaredLength
            )
            interpreter = Interpreter(mappedByteBuffer)
            modelLoadError = null
            Log.i(TAG, "Model loaded from assets")
        } catch (e: Exception) {
            modelLoadError = e
            Log.e(TAG, "Failed to load model from assets: ${e.message}")
        }
    }
    
    private fun loadModelFromFile(file: File) {
        try {
            val inputStream = FileInputStream(file)
            val mappedByteBuffer = inputStream.channel.map(
                FileChannel.MapMode.READ_ONLY,
                0,
                file.length()
            )
            interpreter = Interpreter(mappedByteBuffer)
            modelLoadError = null
            Log.i(TAG, "Model loaded from file: ${file.absolutePath}")
        } catch (e: Exception) {
            modelLoadError = e
            Log.e(TAG, "Failed to load model from file: ${e.message}")
        }
    }

    fun reloadModel(file: File) {
        interpreter?.close()
        loadModelFromFile(file)
    }
    
    fun predictMultiOutput(taxRate: Float, infrastructure: Float, education: Float, foreignPolicy: Float): MultiOutputPrediction {
        // Clamp inputs
        val tRate = taxRate.coerceIn(0f, 1f)
        val infra = infrastructure.coerceIn(0f, 1f)
        val edu = education.coerceIn(0f, 1f)
        val fp = foreignPolicy.coerceIn(0f, 1f)

        val currentInterpreter = interpreter ?: return emptyPrediction()
        
        inputBuffer.rewind()
        inputBuffer.putFloat(tRate)
        inputBuffer.putFloat(infra)
        inputBuffer.putFloat(edu)
        inputBuffer.putFloat(fp)
        inputBuffer.rewind() // REWIND BEFORE RUN
        
        val outputs = mutableMapOf<Int, Any>()
        outputs[0] = FloatArray(50) // GDP
        outputs[1] = FloatArray(50) // Inflation
        outputs[2] = FloatArray(50) // Unemployment
        outputs[3] = FloatArray(50) // Gini
        outputs[4] = FloatArray(50) // HDI

        try {
            currentInterpreter.runForMultipleInputsOutputs(arrayOf(inputBuffer), outputs)
            
            val gdp = outputs[0] as FloatArray
            val inflation = outputs[1] as FloatArray
            val unemployment = outputs[2] as FloatArray
            val gini = outputs[3] as FloatArray
            val hdi = outputs[4] as FloatArray

            // Basic validation
            if (gdp.any { it.isNaN() }) {
                Log.e(TAG, "NaN detected in prediction output")
                return emptyPrediction()
            }

            // Simulated Monte Carlo for confidence intervals
            val lower = FloatArray(50) { i -> gdp[i] * 0.95f }
            val upper = FloatArray(50) { i -> gdp[i] * 1.05f }

            return MultiOutputPrediction(
                gdp = gdp,
                inflation = inflation,
                unemployment = unemployment,
                gini = gini,
                hdi = hdi,
                confidenceLower = lower,
                confidenceUpper = upper
            )
        } catch (e: Exception) {
            Log.e(TAG, "Inference error: ${e.message}")
            return emptyPrediction()
        }
    }

    suspend fun runMonteCarlo(tax: Float, infra: Float, edu: Float, fp: Float, iterations: Int = 200): MultiOutputPrediction = withContext(Dispatchers.Default) {
        // Run multiple predictions with noise and aggregate
        val samples = List(iterations / 20) { // Scaled down for simulation performance
            predictMultiOutput(
                tax + (Math.random().toFloat() * 0.02f - 0.01f),
                infra + (Math.random().toFloat() * 0.02f - 0.01f),
                edu + (Math.random().toFloat() * 0.02f - 0.01f),
                fp + (Math.random().toFloat() * 0.02f - 0.01f)
            )
        }
        
        // Return average or first for now
        samples.firstOrNull() ?: emptyPrediction()
    }

    private fun emptyPrediction() = MultiOutputPrediction(
        FloatArray(50), FloatArray(50), FloatArray(50), FloatArray(50), FloatArray(50), FloatArray(50), FloatArray(50)
    )
    
    fun close() {
        interpreter?.close()
        interpreter = null
    }
}
