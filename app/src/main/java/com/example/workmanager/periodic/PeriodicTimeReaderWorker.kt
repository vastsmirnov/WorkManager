package com.example.workmanager.periodic

import android.content.Context
import androidx.work.*
import com.example.workmanager.utils.TimeUtil
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

class PeriodicTimeReaderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        EventBus.getDefault().post(PeriodicTimeEvent(TimeUtil.getCurrentTime()))
        WorkManager.getInstance(applicationContext).enqueue(makeRequest())

        return Result.success()
    }

    companion object {
        const val TAG = "PERIODIC_TIME_READER_WORKER"

        fun makeRequest() = PeriodicWorkRequestBuilder<PeriodicTimeReaderWorker>(
            15,
            TimeUnit.MINUTES
        )
            .setInitialDelay(15, TimeUnit.MINUTES)
            .addTag(TAG)
            .build()
    }
}