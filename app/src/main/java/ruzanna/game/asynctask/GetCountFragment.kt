package ruzanna.game.asynctask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_get_count.*

class GetCountFragment: DialogFragment() {

    lateinit var listener: GetCountFragmentListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_get_count, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save_count.setOnClickListener {
            val getCount = count.text.toString().toInt()
            val getMaxCount = max_count.text.toString().toInt()
            if(getCount in 1 .. getMaxCount){
                listener.getGetCounts(getCount, getMaxCount)
            }
            else{
                Toast.makeText(context, "Count and Max count must be greater" +
                        " than 0 and Count must be greater than Max count ", Toast.LENGTH_LONG).show()
            }
        }
    }

    interface GetCountFragmentListener{
        fun getGetCounts(count:Int, maxCount:Int)
    }
}