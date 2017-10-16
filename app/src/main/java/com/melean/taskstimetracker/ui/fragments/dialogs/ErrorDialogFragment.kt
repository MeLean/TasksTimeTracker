package com.melean.taskstimetracker.ui.fragments.dialogs


import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView

import com.melean.taskstimetracker.R

class ErrorDialogFragment : DialogFragment() {

    private var mMessage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mMessage = arguments.getString(ARG_MESSAGE)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity
        val view = activity.layoutInflater
                .inflate(R.layout.dialog_fragment_error, LinearLayout(activity), false)

        // Set values
        (view.findViewById(R.id.tw_error_dialog_message) as TextView).text = mMessage
        view.findViewById(R.id.btn_error_dialog_ok).setOnClickListener { dialog.dismiss() }

        // Build dialog
        val builder = Dialog(activity)
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
        builder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.setContentView(view)
        builder.setCancelable(true)
        return builder
    }

    companion object {
        val TAG = "com.melean.taskstimetracker.ui.fragments.dialogs.ErrorDialogFragment"
        private val ARG_MESSAGE = "com.melean.taskstimetracker.ui.fragments.dialogs.message"

        // TODO: Rename and change types and number of parameters
        fun newInstance(message: String): ErrorDialogFragment {
            val fragment = ErrorDialogFragment()
            val args = Bundle()
            args.putString(ARG_MESSAGE, message)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
