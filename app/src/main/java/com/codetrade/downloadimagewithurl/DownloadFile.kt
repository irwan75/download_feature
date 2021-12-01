package com.codetrade.downloadimagewithurl

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.widget.Toast
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class DownloadFile :
    AsyncTask<String?, Int?, String?> {

    private lateinit var folder: File
    private lateinit var context : Context
    private lateinit var mProgressDialog : ProgressDialog
    var delegate : AsyncResponse? = null

    constructor(context: Context){
        this.context = context
        mProgressDialog = ProgressDialog(context)
        mProgressDialog.setMessage("Downloading New Version Please Wait...")
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        mProgressDialog.setCancelable(true)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        mProgressDialog.show()
    }

    override fun doInBackground(vararg strings: String?): String? {
        var input: InputStream? = null
        var output: OutputStream? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(strings.get(0))
            connection = url.openConnection() as HttpURLConnection
            connection.connect()

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() !== HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                    .toString() + " " + connection.getResponseMessage()
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            val fileLength: Int = connection.contentLength
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//                var file = File(context.getExternalFilesDir(null).toString() + "/" + "DownloadFile")
                var file = File(Environment.getExternalStorageDirectory().toString())
                if(!file.exists()){
                    file.mkdir()
                }
                folder = file
            }else{
                val extStorageDirectory: String =
                    Environment.getExternalStorageDirectory().toString()
//                    Environment.getExternalStorageDirectory().toString()

                folder = File(extStorageDirectory, "DownloadFile")
                folder.mkdir()
            }

            val imageFile = File(folder, strings[1])
//            filePath = imageFile.absolutePath

            try {
                imageFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // download the file
            input = connection.getInputStream()
            output = FileOutputStream(imageFile.absolutePath)
            val data = ByteArray(4096)
            var total: Long = 0
            var count: Int = 0
            while (input.read(data).also(
                    { count = it }) != -1) {
                // allow canceling with back button
                if (isCancelled) {
                    input.close()
                    return null
                }
                total += count.toLong()
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((total * 100 / fileLength).toInt())
                output.write(data, 0, count)
            }
        } catch (e: Exception) {
            return e.toString()
        } finally {
            try {
                if (output != null) output.close()
                if (input != null) input.close()
            } catch (ignored: IOException) {
            }
            if (connection != null) connection.disconnect()
        }
        return null
    }


    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(values[0]!!);
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        mProgressDialog.dismiss()
        if(result!=null){
             delegate?.processFinish("0")
        }else{
            delegate?.processFinish("1")
        }
    }

}
