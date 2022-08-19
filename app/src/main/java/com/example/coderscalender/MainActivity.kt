package com.example.coderscalender

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchdata()
//        for(i in contestData){
//            println(i.site + " " + i.name + " " + i.start_time + " " + i.end_time + "\n")
//        }


    }

    private fun fetchdata(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://kontests.net/api/v1/all"
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,
            url,
            null,
            { response->
//                val JsonArray = response.getJSONArray()
                    println("Volley Started\n")
                    val contestData : ArrayList<Contest> = ArrayList()
                    for (i in 0 until response.length()  ) {
                        val newsJsonObject = response.getJSONObject(i)
                        val time : String = newsJsonObject.getString("start_time")
                        val site : String = newsJsonObject.getString("site")
                        val timeT = newsJsonObject.getString("end_time")
                        val url = newsJsonObject.getString("url")
                        val name = newsJsonObject.getString("name")
                        val contest = Contest(
                            site = newsJsonObject.getString("site"),
                            start_time = newsJsonObject.getString("start_time"),
                            end_time = newsJsonObject.getString("end_time"),
                            name  = newsJsonObject.getString("name"),
                            url = newsJsonObject.getString("url")
                        )
                        contestData.add(contest)
//                                for(i in contestData){
//            println(i.site + " " + i.name + " " + i.start_time + " " + i.end_time + "\n")
//        }


                       // Log.i("response", "Response successfully Recieved ${time} ${name} ${url} ${site} $timeT ")


                    }
                //println("json Parsed\n")
                buildRecyclerView(contestData)
            },
            {
                Log.d("Error", "Some error occured")
                Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            }

        )
        queue.add(jsonArrayRequest)
    }
    private fun buildRecyclerView(contestData : ArrayList<Contest>){

        val listAdapter = ContestListAdapter(contestData, this@MainActivity)
        rvcalender.setHasFixedSize(true)

        rvcalender.layoutManager = LinearLayoutManager(this@MainActivity)

        rvcalender.adapter = listAdapter
        //println("Adapter Successfully Created")

    }

}