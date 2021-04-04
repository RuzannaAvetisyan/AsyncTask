package ruzanna.game.asynctask

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), GetCountFragment.GetCountFragmentListener {
    private var count: Int = 0
    private var maxCount: Int = 0
    private val getCountFragment = GetCountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCountFragment.listener = this
        getCountFragment.show(supportFragmentManager, "GetCountFragment")
        val progressDialog = ProgressDialog(this@MainActivity)

        reset.setOnClickListener {
            count_now.text = count.toString()
            reset.visibility = View.INVISIBLE
        }

        add_count.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                progressDialog.setTitle("Coroutine Test")
                progressDialog.setMessage("Add ... ")
                progressDialog.show()

                val res = async(Dispatchers.Default) {
                    getCount(count_now, true)
                }
                count_now.text = res.await()
                resetButton(count_now.text.toString().toInt())
                progressDialog.dismiss()
            }
        }
        minus_count.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                progressDialog.setTitle("Coroutine Test")
                progressDialog.setMessage("Minus ... ")
                progressDialog.show()

                val res = async(Dispatchers.Default) {
                    getCount(count_now, false)
                }
                count_now.text = res.await()
                resetButton(count_now.text.toString().toInt())
                progressDialog.dismiss()
            }
        }

    }

    private fun resetButton(countNow: Int){
        if (countNow == maxCount || countNow == 0)
            reset.visibility = View.VISIBLE
        else
            reset.visibility = View.INVISIBLE
    }

    override fun getGetCounts(count: Int, maxCount: Int) {
        getCountFragment.dismiss()
        this.count = count
        count_now.text = count.toString()
        this.maxCount = maxCount
    }

    private suspend fun getCount(countNow: TextView, boolean: Boolean): String{
        delay(1000)
        val res = countNow.text.toString().toInt()
        return if(boolean){
            if(res < maxCount )
                (res + 1).toString()
            else
                res.toString()
        }else{
            if(res > 0 )
                (res - 1).toString()
            else
                res.toString()
        }
    }


}
