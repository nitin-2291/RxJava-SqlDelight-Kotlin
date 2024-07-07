package com.example.task

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.task.db.Database
import com.example.task.model.LoginRes
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    var token : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val host = findViewById<EditText>(R.id.host)
        val signIn = findViewById<Button>(R.id.signIn)

       // val database = Database(AndroidSqliteDriver(Database.Schema, context, "Database.db"))


        signIn.setOnClickListener {

            if (!isValidEmail(email.text)) {
                Toast.makeText(this, "Your email not is valid.", Toast.LENGTH_LONG).show();
            } else if (password.text.length < 6) {
                Toast.makeText(
                    this,
                    "Password must be at least 6 characters long.",
                    Toast.LENGTH_LONG
                ).show();
            } else {
                loginRequest(email.text.toString(), password.text.toString());
            }

        }


    }

    private fun loginRequest(email: String, password: String) {

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setTitle("Login request")
        progressDialog.setMessage("Authenticating")
        progressDialog.show()

        val database = Database(AndroidSqliteDriver(Database.Schema, this, "Database.db"))


        val requestInterface = Retrofit.Builder()
            .baseUrl("https://mofa.onice.io/teamchatapi/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestInterface::class.java)

        requestInterface.loginReq(email, password,"mofa.onice.io")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LoginRes> {
                override fun onNext(loginres: LoginRes) {

                    Log.e("EXE :", "NEXT");
                    Log.e("EXE :", "NEXT" + loginres.toString());

                    token = loginres.token

                     database.setupdbQueries.insertUser(loginres.authorized+"",
                         ""+loginres.token,
                         ""+loginres.host,
                         ""+loginres.email,
                         ""+loginres.ok)

                }

                override fun onError(e: Throwable) {

                    Log.e("EXE :", "ERROR" + e.toString());
                    progressDialog.dismiss();

                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show();
                }

                override fun onComplete() {
                    Log.e("EXE :", "COMPLETE");
                    progressDialog.dismiss();
                    Toast.makeText(applicationContext, "Login Success.", Toast.LENGTH_LONG).show();

                    val intent = Intent(applicationContext, ChannelList::class.java)
                    intent.putExtra("token",token);
                    startActivity(intent)

                }

                override fun onSubscribe(d: Disposable) {
                }
            })

    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}