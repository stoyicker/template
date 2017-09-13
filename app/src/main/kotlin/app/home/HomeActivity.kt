package app.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import jorge.template.blank.R
import kotlinx.android.synthetic.main.activity_home.label
import kotlinx.android.synthetic.main.activity_home.reset
import kotlinx.android.synthetic.main.activity_home.start_stop

internal class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var delegate: ChronometerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val chronometerFragment = supportFragmentManager.findFragmentByTag("tag")
        if (chronometerFragment == null) {
            delegate = ChronometerFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .add(delegate, "tag")
                    .disallowAddToBackStack()
                    .commit()
        } else {
            delegate = chronometerFragment as ChronometerFragment
        }
        arrayOf(start_stop, reset).forEach { it.setOnClickListener(this@HomeActivity) }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            start_stop.id -> {
                delegate.setView(label)
                delegate.toggle()
            }
            reset.id -> {
                delegate.reset()
                label.text = ""
            }
        }
    }
}
