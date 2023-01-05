package com.example.ecommerce.Customer

import android.widget.EditText
import android.os.Bundle
import com.example.ecommerce.Customer.customerFragLogin
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ecommerce.R
import android.widget.TextView
import android.content.Intent
import com.example.ecommerce.Customer.customerVerify
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.example.ecommerce.Model.usersCustomers
import com.example.ecommerce.Customer.customerMain
import com.google.firebase.database.DatabaseError

class customerFragLogin : Fragment() {
    private var inputPhoneCustomer: EditText? = null
    private var inputPasswordCustomer: EditText? = null
    private val parentDbName = "Users"

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_customer_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val forgotpass: TextView
        forgotpass = view.findViewById(R.id.forgot_pass_customer)
        forgotpass.setOnClickListener {
            startActivity(
                Intent(
                    activity, customerVerify::class.java
                )
            )
        }
        val login = view.findViewById<View>(R.id.login_customer) as Button
        inputPhoneCustomer = view.findViewById<View>(R.id.phone_customer) as EditText
        inputPasswordCustomer = view.findViewById<View>(R.id.password_customer) as EditText
        login.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                LoginUser()
            }

            private fun LoginUser() {
                val phone = inputPhoneCustomer!!.text.toString()
                val password = inputPasswordCustomer!!.text.toString()
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(activity, "Please write your phone number.", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(activity, "Please write your password.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    AllowAccessToAccount(phone, password)
                }
            }

            private fun AllowAccessToAccount(phone: String, password: String) {
                val RootRef: DatabaseReference
                RootRef = FirebaseDatabase.getInstance().getReference("Customers")
                RootRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(datasnapshot: DataSnapshot) {
                        if (datasnapshot.child(parentDbName).child(phone).exists()) {
                            val usersData = datasnapshot.child(parentDbName).child(phone).getValue(
                                usersCustomers::class.java
                            )!!
                            if (usersData.phone == phone) {
                                if (usersData.password == password) {
                                    Toast.makeText(
                                        activity,
                                        "Logged in Successfully.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val intent = Intent(activity, customerMain::class.java)
                                    intent.putExtra("cId", phone)
                                    startActivity(intent)
                                    onDestroy()
                                } else {
                                    Toast.makeText(
                                        activity,
                                        "Password is incorrect.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                activity,
                                "Account with $phone does not exist.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        })
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment customerfragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): customerFragLogin {
            val fragment = customerFragLogin()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}