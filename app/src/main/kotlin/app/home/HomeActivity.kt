package app.home

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jorge.template.blank.R
import kotlin.random.Random

internal class HomeActivity : AppCompatActivity() {
  private companion object {
    const val TAG_ROLL_FRAGMENT = "ROLL_FRAGMENT"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportFragmentManager.apply {
      findFragmentByTag(TAG_ROLL_FRAGMENT).run {
        this ?: AsyncTaskContainerFragment()
      }.let {
        beginTransaction()
            .replace(android.R.id.content, it, TAG_ROLL_FRAGMENT)
            .commit()
      }
    }
  }
}

internal class AsyncTaskContainerFragment : Fragment() {
  private var task : RollAsyncTask? = null

  init {
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
      inflater.inflate(R.layout.fragment_main, container, false)!!

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    view.apply {
      findViewById<View>(R.id.my_roll_button).setOnClickListener {
        if (task != null && !task!!.isCancelled) {
          task!!.cancel(true)
        }
        task = RollAsyncTask(findViewById(R.id.my_roll_textview)).apply {
          execute()
        }
      }
      // This is needed to give the fragment the new target view after the layout is recreated
      task?.resultView = findViewById(R.id.my_roll_textview)
    }
  }

  override fun onDestroyView() {
    task?.resultView = null
    super.onDestroyView()
  }
}

internal class RollAsyncTask(var resultView: TextView?) : AsyncTask<Void, Int, Int?>() {
  private companion object {
    const val ROLL_DURATION_MILLIS = 40000
    const val ROLL_INTERVAL_MILLIS = 500L
  }

  private var spent = 0L

  override fun onPreExecute() {
    resultView?.text = "Start rolling!"
  }

  override fun doInBackground(vararg params: Void?): Int? {
    var currentRoll: Int? = null
    while (spent < ROLL_DURATION_MILLIS) {
      Thread.sleep(ROLL_INTERVAL_MILLIS)
      spent += ROLL_INTERVAL_MILLIS
      currentRoll = Random(System.currentTimeMillis()).nextInt(1, 7)
      publishProgress(currentRoll)
    }
    return currentRoll
  }

  override fun onProgressUpdate(vararg values: Int?) {
    resultView?.text = "New roll: ${values[0]}"
  }

  override fun onPostExecute(result: Int?) {
    when (result) {
      null -> throw IllegalStateException("No rolls occurred")
      else -> resultView?.text = "Final roll: $result"
    }
  }
}
