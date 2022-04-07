package com.es.webserveractivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnsot.setOnClickListener {
            object:Thread() {
                override fun run() {
                    try{
                        val url= URL(urlText.text.toString())

                        val urlConnection=url.openConnection() as HttpURLConnection
                        urlConnection.requestMethod="GET"

                        if(urlConnection.responseCode==HttpURLConnection.HTTP_OK){
                            //데이터 읽기
                            val streamReader= InputStreamReader(urlConnection.inputStream)
                            val buffered=BufferedReader(streamReader)

                            val content= StringBuilder()

                            while(true){
                                val line=buffered.readLine()?: break
                                content.append(line)

                            }
                            //스트림과 커넥션 해제
                            buffered.close()
                            urlConnection.disconnect()
                            runOnUiThread{
                                resultText.text=content.toString()
                            }
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

            }.start()
        }
    }
}