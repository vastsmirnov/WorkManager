package com.example.workmanager.recursive

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanager.utils.TimeUtil
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

class RecursiveTimeReaderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        EventBus.getDefault().post(RecursiveTimeEvent(TimeUtil.getCurrentTime()))
        WorkManager.getInstance(applicationContext).enqueue(makeRequest())

        return Result.success()
    }

    companion object {
        const val TAG = "RECURSIVE_TIME_READER_WORKER"

        fun makeRequest() = OneTimeWorkRequestBuilder<RecursiveTimeReaderWorker>()
            .addTag(TAG)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
    }
}