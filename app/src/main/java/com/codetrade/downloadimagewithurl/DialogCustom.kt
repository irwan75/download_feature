package com.codetrade.downloadimagewithurl

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle

class DialogCustom : ProgressDialog {

//    private lateinit var context : Context

    companion object {
        fun newInstance(context: Context, callback: Callback, message: String): ProgressDialog {
            val dialog = ProgressDialog(context)
//            dialog.message = message
//            dialog.callback = callback
            return dialog
        }
    }

    private var message: String? = null
    private var callback: Callback? = null

    constructor(context: Context) : super(context) {
//        this.context = context
        initLayout(context)
    }


//    constructor(
//        context: Context,
//        cancelable: Boolean,
//        cancelListener: DialogInterface.OnCancelListener?
//    ) : super(context, cancelable, cancelListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(context)
    }

    private fun initLayout(context: Context) {
        setContentView(R.layout.dialog_scroll)
//        tvMessage.text = message
//        btnNo.setOnClickListener {
//            this.dismiss()
//        }
//        btnYes.setOnClickListener {
//            this.dismiss()
//            callback?.onYesSelected()
//        }
    }

    interface Callback {
        fun onYesSelected()
    }
}