package com.example.workmanager.chain

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanager.onetime.SayHelloWorker
import java.util.concurrent.TimeUnit

class SayKittyWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val hello = inputData.getString(SayHelloWorker.KEY_OUTPUT_DATA)

        return Result.success(
            Data.Builder()
                .putString(KEY_OUTPUT_DATA, "Kitty! $hello")
                .build()
        )
    }

    companion object {
        const val TAG = "SAY_KITTY_WORKER"
        const val KEY_OUTPUT_DATA = "KEY_OUTPUT_DATA"

        fun makeRequest() = OneTimeWorkRequestBuilder<SayKittyWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
    }
}