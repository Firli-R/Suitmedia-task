package com.example.task_suitmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.task_suitmedia.api.ApiConfig
import com.example.task_suitmedia.databinding.ActivityThirdBinding
import com.example.task_suitmedia.model.User
import com.example.task_suitmedia.model.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var activityThirdBinding : ActivityThirdBinding
    private lateinit var userAdapter:UserAdapter
    private var TAG ="Third Screen"
    private var isLoading = false
    private var page: Int = 1
    private var totalPage: Int = 1
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = TAG

        super.onCreate(savedInstanceState)
        activityThirdBinding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(activityThirdBinding.root)

        layoutManager = LinearLayoutManager(this)
        setUpRecyclerView()
        userAdapter.setClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@ThirdActivity, SecondActivity::class.java).also {
                    it.putExtra(SecondActivity.EXTRA_NAME, data.first_name)
                    startActivity(it)
                }
            }
        })

        activityThirdBinding.swipeRefresh.setOnRefreshListener(this)
        getUsers(false)

        activityThirdBinding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total  = userAdapter.itemCount
                if (!isLoading && page < totalPage ){
                    if (visibleItemCount + pastVisibleItem >= total){
                        page++
                        getUsers(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
                }
            })
    }

    private fun setUpRecyclerView() {
        userAdapter = UserAdapter()
        activityThirdBinding.apply {
            recyclerview.layoutManager = layoutManager
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = userAdapter
        }
    }

    private fun getUsers(isOnRefresh: Boolean){
        isLoading = true
        if (!isOnRefresh) {
        activityThirdBinding.progressBar.visibility = View.VISIBLE}

        Handler().postDelayed({
        val parameters = HashMap<String, String>()
        parameters["page"] = page.toString()
        ApiConfig.endpoint.mSearch(parameters).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    totalPage = response.body()?.total_pages!!
                    val listUsers = response.body()?.data
                    Log.d(TAG, "onResponse: $listUsers")
                    if(listUsers!!.isNotEmpty()){
                        userAdapter.setList(listUsers)
                    }
                    activityThirdBinding.progressBar.visibility = View.GONE
                    isLoading = false
                    activityThirdBinding.swipeRefresh.isRefreshing =false
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
                Toast.makeText(this@ThirdActivity, "koneksi gagal", Toast.LENGTH_SHORT).show()
                activityThirdBinding.progressBar.visibility = View.GONE
                isLoading = false
                activityThirdBinding.swipeRefresh.isRefreshing =false
            }

        }) }, 3000)

    }

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return super.onSupportNavigateUp()
    }

    override fun onRefresh() {
        userAdapter.clearUsers()
        page = 1
        getUsers(true)
    }
}