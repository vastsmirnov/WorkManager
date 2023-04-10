package com.example.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkManager
import com.example.workmanager.databinding.ActivityMainBinding
import com.example.workmanager.onetime.SayHelloWorker
import com.example.workmanager.periodic.PeriodicTimeEvent
import com.example.workmanager.periodic.PeriodicTimeReaderWorker
import com.example.workmanager.recursive.RecursiveTimeEvent
import com.example.workmanager.recursive.RecursiveTimeReaderWorker
import com.example.workmanager.utils.TimeUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onStart() {
        super.onStart()

        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()

        EventBus.getDefault().unregister(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            recursiveOneTimeTv.text = TimeUtil.getCurrentTime()
            periodicTv.text = TimeUtil.getCurrentTime()
        }

        WorkManager.getInstance(this).cancelAllWorkByTag(RecursiveTimeReaderWorker.TAG)
        WorkManager.getInstance(this).enqueue(RecursiveTimeReaderWorker.makeRequest())

        WorkManager.getInstance(this).cancelAllWorkByTag(PeriodicTimeReaderWorker.TAG)
        WorkManager.getInstance(this).enqueue(PeriodicTimeReaderWorker.makeRequest())

        WorkManager.getInstance(this).cancelAllWorkByTag(SayHelloWorker.TAG)
        val request = SayHelloWorker.makeRequest()
        WorkManager.getInstance(this).enqueue(request)
        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(request.id)
            .observe(this) {
                if (it.state.isFinished) binding.oneTimeTv.text =
                    it.outputData.getString(SayHelloWorker.KEY_OUTPUT_DATA)
            }

        setContentView(binding.root)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRecursiveTimeEvent(event: RecursiveTimeEvent) {
        binding.recursiveOneTimeTv.text = event.time
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPeriodicTimeEvent(event: PeriodicTimeEvent) {
        binding.periodicTv.text = event.time
    }
}