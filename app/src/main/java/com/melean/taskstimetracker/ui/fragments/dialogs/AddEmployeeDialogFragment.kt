package com.melean.taskstimetracker.ui.fragments.dialogs


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.melean.taskstimetracker.R

class AddEmployeeDialogFragment : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.dialog_fragment_add_employee, container, false)
    }

    companion object {

        val TAG = "com.melean.taskstimetracker.ui.fragments.dialogs.add_employee_fragment"
    }

}// Required empty public constructor
