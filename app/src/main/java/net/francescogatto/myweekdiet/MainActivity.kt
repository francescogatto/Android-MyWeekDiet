package net.francescogatto.myweekdiet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import net.francescogatto.myweekdiet.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, MainFragment.newInstance())
                    .commit()
        }
    }

    override fun onBackPressed() {
       /* if (fragmentManager.backStackEntryCount == 1) {
            moveTaskToBack(false)
        } else {
            super.onBackPressed()
        }*/

        if(fragmentManager.backStackEntryCount == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
