package ruzanna.game.asynctask

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GetCountFragment.GetCountFragmentListener {
    private var count: Int = 0
    private var maxCount: Int = 0
    private val getCountFragment = GetCountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCountFragment.listener = this
        getCountFragment.show(supportFragmentManager, "GetCountFragment")
        count_now.text = count.toString()
        val progressDialog = ProgressDialog(this@MainActivity)

        add_count.setOnClickListener {
            Log.i("add_count", "test")
            val asyncTask = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Void, Int, String>() {

                override fun onPreExecute() {
                    super.onPreExecute()
                    progressDialog.setTitle("AsyncTask Test")
                    progressDialog.setMessage("Loading ... ")
                    progressDialog.show()
                }

                override fun doInBackground(vararg params: Void?): String {
                    Thread.sleep(1000)
                    val countNow = count_now.text.toString().toInt()
                    if (countNow == maxCount)
                        return "reset"
                    return (countNow + 1).toString()
                }

                override fun onPostExecute(result: String?) {
                    super.onPostExecute(result)
                    if (result != "reset"){
                        count_now.text = result
                    }
                    else{
                        reset.visibility = View.VISIBLE
                    }
                    progressDialog.dismiss()
                }
            }
            asyncTask.execute()
        }

        reset.setOnClickListener {
            Log.i("reset", "test")
            val asyncTask = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Void, Int, String>() {

                override fun onPreExecute() {
                    super.onPreExecute()
                    progressDialog.setTitle("AsyncTask Test")
                    progressDialog.setMessage("Loading ... ")
                    progressDialog.show()
                }

                override fun doInBackground(vararg params: Void?): String? {
                    Thread.sleep(1000)
                    return count.toString()
                }

                override fun onPostExecute(result: String?) {
                    super.onPostExecute(result)
                    count_now.text = result
                    reset.visibility = View.INVISIBLE
                    progressDialog.dismiss()
                }
            }
            asyncTask.execute()
        }

        minus_count.setOnClickListener {
            val asyncTask = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Void, Int, String>() {

                override fun onPreExecute() {
                    super.onPreExecute()
                    progressDialog.setTitle("AsyncTask Test")
                    progressDialog.setMessage("Loading ... ")
                    progressDialog.show()
                }

                override fun doInBackground(vararg params: Void?): String {
                    Thread.sleep(1000)
                    val countNow = count_now.text.toString().toInt()
                    if (countNow == 0)
                        return "reset"
                    return (countNow - 1).toString()
                }

                override fun onPostExecute(result: String?) {
                    super.onPostExecute(result)
                    if (result != "reset"){
                        count_now.text = result
                    }
                    else{
                        reset.visibility = View.VISIBLE
                    }
                    progressDialog.dismiss()
                }
            }
            asyncTask.execute()
        }


    }

    override fun getGetCounts(count: Int, maxCount: Int) {
        getCountFragment.dismiss()
        this.count = count
        this.maxCount = maxCount
    }
}
