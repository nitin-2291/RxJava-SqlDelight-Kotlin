package com.example.task

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.task.Adapter.CustomAdapter
import com.example.task.db.Database
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChannelList : AppCompatActivity() {

    var token :String = ""
    //lateinit var token :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_list)

        token = intent.getStringExtra("token")!!

        //Toast.makeText(applicationContext, token, Toast.LENGTH_LONG).show();

        supportActionBar?.apply {
            title = "Channel List"

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        getChannelsList()

    }

    private fun getChannelsList() {

        val progressDialog = ProgressDialog(this@ChannelList)
        progressDialog.setTitle("Channel List")
        progressDialog.setMessage("Loading")
        progressDialog.show()

        val database = Database(AndroidSqliteDriver(Database.Schema, this, "Database.db"))

        val requestInterface = Retrofit.Builder()
            .baseUrl("https://mofa.onice.io/teamchatapi/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestInterface::class.java)

        requestInterface.getChannels(token,true,true,false)
            .subscribeOn(Schedulers.io())
            //.takeUntil(Observable.error(new TimeoutException()).delay(5, TimeUnit.MINUTES, true))
            //.timeout(60, TimeUnit.SECONDS, Completable.error(TimeoutException("TIME OUT")))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ChannelListData> {
                override fun onNext(channellist: ChannelListData) {

                    Log.e("EXE :", "NEXT");
                    Log.e("EXE :", "NEXT" + channellist.toString());

                    for (i in channellist.channels){
                        database.setupdbQueries.insertChannels(i.id.toString(),i.name.toString(),i.creator.toString(),i.groupEmail.toString(),i.groupFolderName.toString())
                    }

                    val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
                    recyclerview.layoutManager = LinearLayoutManager(applicationContext)


                    val channelListData = database.setupdbQueries.getAllChannels().executeAsList();
                   // val channelListData:ArrayList<RecyclerDataClass> = ArrayList<RecyclerDataClass>()

                    val adapter = CustomAdapter(channelListData)

                    // Setting the Adapter with the recyclerview
                    recyclerview.adapter = adapter

                }

                override fun onError(e: Throwable) {

                    Log.e("EXE :", "ERROR" + e.toString());
                    progressDialog.dismiss();

                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show();
                }

                override fun onComplete() {
                    Log.e("EXE :", "COMPLETE");
                    progressDialog.dismiss();
                    Toast.makeText(applicationContext, "Data populated", Toast.LENGTH_LONG).show();

                }

                override fun onSubscribe(d: Disposable) {
                }
            })

    }

    override fun onSupportNavigateUp(): Boolean {

       // Toast.makeText(applicationContext, "BACK PRESSED", Toast.LENGTH_LONG).show();

        val database = Database(AndroidSqliteDriver(Database.Schema, this, "Database.db"))

        database.setupdbQueries.delete()
        database.setupdbQueries.deleteChannels()

        onBackPressed()
        return true
    }
}