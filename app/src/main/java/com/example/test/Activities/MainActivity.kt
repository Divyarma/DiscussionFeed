package com.example.test.Activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.example.test.ApiInterfaces.LoginInterface
import com.example.test.Constants
import com.example.test.Constants.retrofit
import com.example.test.R
import com.example.test.models.Login_body
import com.example.test.models.login
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    var pd: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var User_name: String
        var Password: String

        btn_Submit.setOnClickListener {
            showpd()
            User_name = et_userEmail.text.toString()
            Password = et_password.text.toString()

            login_btn_api(Login_body(User_name, Password))
        }

    }

    private fun login_btn_api(l: Login_body) {


        val service: LoginInterface = retrofit.create<LoginInterface>(LoginInterface::class.java)

        val listCall: Call<login> = service.LoginCheck(l)


        listCall.enqueue(object : Callback<login> {
            override fun onResponse(call: Call<login>, response: Response<login>) {

                // Check weather the response is success or not.
                if (response.isSuccessful) {
                    /** The de-serialized response body of a successful response. */
                    val response_login: login? = response.body()
                    Log.i("Response Result", "$response_login")
                    Constants.UserId = response_login!!.Result.userId
                    Constants.AccessToken = "Bearer "+response_login!!.Result.token.access
                    dismisspd()
                    startActivity(Intent(this@MainActivity, MainPostScreen::class.java))

                } else {
                    dismisspd()
                    Toast.makeText(
                        this@MainActivity,
                        " Please enter correct details. ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<login>, t: Throwable) {

                dismisspd()
                Log.i("response", "failed ")

            }


        })
    }

    private fun showpd() {
        pd = Dialog(this)
        pd!!.setContentView(R.layout.dialog_custom_progress)
        pd!!.show()
    }
    private fun dismisspd(){
        if (pd != null) {
            pd!!.dismiss()
        }
    }
}





