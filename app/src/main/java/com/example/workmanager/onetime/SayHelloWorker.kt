package com.example.workmanager.onetime

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class SayHelloWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success(
            Data.Builder()
                .putString(KEY_OUTPUT_DATA, "Hello!")
                .build()
        )
    }

    companion object {
        const val TAG = "SAY_HELLO_WORKER"
        const val KEY_OUTPUT_DATA = "KEY_OUTPUT_DATA"

        fun makeRequest() = OneTimeWorkRequestBuilder<SayHelloWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
    }
}