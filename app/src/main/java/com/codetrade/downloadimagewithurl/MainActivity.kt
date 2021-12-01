package com.codetrade.downloadimagewithurl

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException


class MainActivity : AppCompatActivity(), AsyncResponse {

    private lateinit var folder: File
    private val FILE_BASE_PATH = "file://"

    //        private val url = "https://resize.indiatvnews.com/en/resize/newbucket/1200_-/2020/07/shiva-1594171271.jpg"
//    private val url =
//        "https://omptestapi.lappindia.com/uploads/offerpdf/offer_350_direct.pdf"
    private val url = "https://a4.files.diawi.com/app-file/ySdXPH5heE89UXWAFnxy.apk"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAppPermissions()

//        Glide.with(this@MainActivity).load(url).into(imgDisplay)

        btnDownload.setOnClickListener {
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            startActivity(browserIntent)
            checkAppPermissions()
            var download = DownloadFile(this)
            download.execute(url, "demo.apk")
            download.delegate = this
//            Log.i("data log",Environment.getExternalStorageDirectory().toString())
        }


        btnFileExist.setOnClickListener {

//            var dialogs: DialogCustom(this)

            openFileReader()


//            showInstallOption()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                var file = File(getExternalFilesDir(null).toString() + "/" + "DownloadFile")
//                if (!file.exists()) {
//                    file.mkdir()
//                }
//                folder = file
//            } else {
//                val extStorageDirectory: String =
//                    Environment.getExternalStorageDirectory().toString()
//
//                folder = File(extStorageDirectory, "DownloadFile")
//                folder.mkdir()
//            }
//
//            val path: String = "$folder/demo.apk"
//            val file = File(path)
//
//            if (file.exists()) {
//                Toast.makeText(this, "File exist", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "File Not exist", Toast.LENGTH_LONG).show()
//            }
        }

    }




    private fun showInstallOption(
//        destination: String,
//        uri: Uri
    ) {

        // set BroadcastReceiver to install app when .apk is downloaded
//        val onComplete = object : BroadcastReceiver() {
//            override fun onReceive(
//                context: Context,
//                intent: Intent
//            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val contentUri = FileProvider.getUriForFile(
                        this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        File(Environment.getExternalStorageDirectory().toString() + "/demo.apk")
                    )
                    val install = Intent(Intent.ACTION_VIEW)
                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                    install.data = contentUri
                    startActivity(install)
                    // finish()
                } else {
                    var destination = Environment.getExternalStorageDirectory().toString() + "/demo.apk"
                    val uri = Uri.parse("$FILE_BASE_PATH$destination")

                    val install = Intent(Intent.ACTION_VIEW)
                    install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    install.setDataAndType(
                        uri,
                        "application/vnd.android.package-archive"
                    )
                    startActivity(install)
                    // finish()
                }
//            }
//        }
//        registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun openFileReader() {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(
                FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",File(Environment.getExternalStorageDirectory().toString() + "/demo.apk"))
//                Uri.fromFile(
//                    File(
//                        Environment.getExternalStorageDirectory().toString() + "/demo.apk"
//                    )
//                )
                , "application/vnd.android.package-archive"
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

//            val intent = Intent(Intent.ACTION_VIEW)
//            val uri = FileProvider.getUriForFile(
//                this,
//                BuildConfig.APPLICATION_ID + ".provider",
//                File(Environment.getExternalStorageDirectory().toString() + "/demo.apk")
//            )

//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.data = uri

//            val i = Intent(Intent.ACTION_VIEW, uri)
//
//            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            startActivity(i)

//            val install = Intent(Intent.ACTION_VIEW)
//            install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//            install.setDataAndType(
//                uri,
//                "application/vnd.android.package-archive"
//            )
//            startActivity(install)

//            Log.i("data log","${File(Environment.getExternalStorageDirectory().toString() + "/demo.apk").path}")
//
//            val install = Intent(Intent.ACTION_VIEW)
//            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
//            install.data = uri
//            startActivity(install)


//            intent.setDataAndType(

//                Uri.fromFile(
//                    File(
//                        Environment.getExternalStorageDirectory().toString() + "/demo.apk"
//                    )
//                ), "application/vnd.android.package-archive"
//            )
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
        }


//        var fileReader: FileReader
//        try {
//            fileReader = FileReader(Environment.getExternalStorageDirectory().toString()+"/demo.apk")
//            var content = fileReader.read()
//            println(content)
//        }
            catch (ffe: FileNotFoundException) {
                Log.i("data log","${ffe.message}")
//            println(ffe.message)
        } catch (ioe: IOException) {
            Log.i("data log","${ioe.message}")
//            println(ioe.message)
        }
    }

    private fun checkAppPermissions() {
        Dexter.withContext(this@MainActivity)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    }

    override fun processFinish(output: String?) {
        if (output.equals("1")) {
//            Toast.makeText(this@MainActivity, "File Downloaded", Toast.LENGTH_SHORT).show();
            openFileReader()
        } else {
            Toast.makeText(this@MainActivity, "Download Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}