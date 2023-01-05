package com.example.ecommerce.Shopkeeper


import android.widget.EditText
import android.os.Bundle

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ecommerce.R
import android.widget.TextView
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.example.ecommerce.Model.usersShopkeepers
import com.google.firebase.database.DatabaseError

/**
 * A simple [Fragment] subclass.
 * Use the [shopkeeperFragLogin.newInstance] factory method to
 * create an instance of this fragment.
 */
class shopkeeperFragLogin : Fragment() {
    private var inputPhoneShopkeeper: EditText? = null
    private var inputShopIdShopkeeper: EditText? = null
    private var inputPasswordShopkeeper: EditText? = null
    private val parentDbName = "Users"

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_shopkeeper_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val forgotpass: TextView
        forgotpass = view.findViewById(R.id.forgot_pass_shopkeeper)
        forgotpass.setOnClickListener {
            startActivity(
                Intent(
                    activity, shopkeeperVerify::class.java
                )
            )
        }
        val login = view.findViewById<Button>(R.id.login_shopkeeper)
        inputPhoneShopkeeper = view.findViewById<View>(R.id.phone_shopkeeper) as EditText
        inputShopIdShopkeeper = view.findViewById<View>(R.id.shop_id) as EditText
        inputPasswordShopkeeper = view.findViewById<View>(R.id.password_shopkeeper) as EditText
        login.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                LoginShopkeeper()
            }

            private fun LoginShopkeeper() {
                val phone = inputPhoneShopkeeper!!.text.toString()
                val shopID = inputShopIdShopkeeper!!.text.toString()
                val password = inputPasswordShopkeeper!!.text.toString()
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(activity, "Please enter your phone number.", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(shopID)) {
                    Toast.makeText(
                        activity,
                        "Please provide your shop registration number.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(activity, "Please write your password.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    AllowAccessToAccount(shopID, phone, password)
                }
            }

            private fun AllowAccessToAccount(shopID: String, phone: String, password: String) {
                val RootRef: DatabaseReference
                RootRef = FirebaseDatabase.getInstance().getReference("Shopkeepers")
                RootRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(datasnapshot: DataSnapshot) {
                        if (datasnapshot.child(parentDbName).child(shopID).exists()) {
                            val usersData1 =
                                datasnapshot.child(parentDbName).child(shopID).getValue(
                                    usersShopkeepers::class.java
                                )
                            if (usersData1 != null && usersData1.phone == phone) {
                                if (usersData1.shopID == shopID) {
                                    if (usersData1.password == password) {
                                        Toast.makeText(
                                            activity,
                                            "Logged in Successfully.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        val intent = Intent(activity, shopkeeperMain::class.java)
                                        intent.putExtra("Id", shopID)
                                        intent.putExtra("shopName", usersData1.shopname)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            activity,
                                            "Password is incorrect.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        activity,
                                        "Error! Shop ID and Phone number don't match.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Sorry! Phone number " + " doesn't match with Shop-ID",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                activity,
                                "Account with $shopID does not exist",
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
         * @return A new instance of fragment shopkeeperFragLogin.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): shopkeeperFragLogin {
            val fragment = shopkeeperFragLogin()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}