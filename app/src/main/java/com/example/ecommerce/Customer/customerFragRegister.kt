package com.example.ecommerce.Customer

import android.widget.EditText
import android.os.Bundle
import com.example.ecommerce.Customer.customerFragRegister
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ecommerce.R
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.android.gms.tasks.OnCompleteListener
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.ecommerce.Customer.customerMain
import com.google.firebase.database.DatabaseError
import java.util.HashMap

class customerFragRegister : Fragment() {
    private var customerName: EditText? = null
    private var customerEmail: EditText? = null
    private var customerPhone: EditText? = null
    private var customerPassword: EditText? = null

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
        return inflater.inflate(R.layout.frag_customer_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val createAccount = view.findViewById<Button>(R.id.register_customer)
        customerName = view.findViewById(R.id.set_name_customer)
        customerEmail = view.findViewById(R.id.set_email_customer)
        customerPhone = view.findViewById(R.id.set_phone_customer)
        customerPassword = view.findViewById(R.id.set_password_customer)
        createAccount.setOnClickListener { CreateAccount() }
    }

    private fun CreateAccount() {
        val name = customerName!!.text.toString()
        val email = customerEmail!!.text.toString()
        val phone = customerPhone!!.text.toString()
        val pass = customerPassword!!.text.toString()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(activity, "Please write a name", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(activity, "Please provide an email", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(activity, "Please enter a phone number", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(activity, "Please give a password", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Please hang in there for a while.", Toast.LENGTH_LONG).show()
            ValidatephoneNumber(name, email, phone, pass)
        }
    }

    private fun ValidatephoneNumber(name: String, email: String, phone: String, pass: String) {
        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().getReference("Customers")
        RootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.child("Users").child(phone).exists()) {
                    val userdataMap = HashMap<String, Any>()
                    userdataMap["phone"] = phone
                    userdataMap["email"] = email
                    userdataMap["password"] = pass
                    userdataMap["name"] = name
                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    activity,
                                    "Your account has been created successfully !",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(activity, customerMain::class.java)
                                intent.putExtra("cId", phone)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Something went wrong, please try again later.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(activity, "$phone already exists.", Toast.LENGTH_LONG).show()
                    Toast.makeText(
                        activity,
                        "Please try again using another phone number.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
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
        fun newInstance(param1: String?, param2: String?): customerFragRegister {
            val fragment = customerFragRegister()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}