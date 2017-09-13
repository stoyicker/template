package app.home

import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.view.View
import android.widget.TextView
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

internal class ChronometerFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()
    private var task = ChronometerTask()

    fun setView(textView: TextView) {
        task.setView(textView)
    }

    fun toggle() {
        when (task.status) {
            AsyncTask.Status.PENDING -> task.executeOnExecutor(executor)
            AsyncTask.Status.RUNNING -> {
                if (task.isGoing) {
                    task.updateStamp()
                }
            }
            else -> throw IllegalStateException("Should never happen")
        }
        task.isGoing = !task.isGoing
    }

    fun reset() {
        task.cancel(true)
        task = ChronometerTask()
    }

    internal class ChronometerTask : AsyncTask<Void, Long, Void?>() {
        var isGoing = false
        private var view: WeakReference<View?>? = null
        private var lastStamp = 0L

        override fun onPreExecute() {
            super.onPreExecute()
            updateStamp()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            while (!isCancelled) {
                while (isGoing) {
                    Thread.sleep(1)
                    updateStamp()
                    publishProgress(lastStamp)
                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Long?) {
            super.onProgressUpdate(*values)
            if (isCancelled || view == null || view!!.get() == null) {
                return
            }
            (view!!.get() as TextView).text = values[0].toString()
        }

        fun updateStamp() {
            lastStamp++
        }

        fun setView(textView: TextView) {
            view = WeakReference(textView)
        }
    }
    companion object {
        fun newInstance() = ChronometerFragment().also {
            it.retainInstance = true
        }
    }
}
