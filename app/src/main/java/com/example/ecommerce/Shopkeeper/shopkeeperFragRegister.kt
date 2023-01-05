package com.example.ecommerce.Shopkeeper

import android.widget.EditText
import android.os.Bundle
import com.example.ecommerce.Shopkeeper.shopkeeperFragRegister
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
import com.example.ecommerce.Shopkeeper.shopkeeperMain
import com.example.ecommerce.Shopkeeper.shopkeeperFragLogin
import com.google.firebase.database.DatabaseError
import java.util.HashMap

class shopkeeperFragRegister : Fragment() {
    private var shopkeeperName: EditText? = null
    private var shopkeeperPhone: EditText? = null
    private var shopkeeperShopName: EditText? = null
    private var shopkeeperShopId: EditText? = null
    private var shopkeeperPassword: EditText? = null

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
        return inflater.inflate(R.layout.frag_shopkeeper_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val createAccount = view.findViewById<Button>(R.id.register_shopkeeper)
        shopkeeperName = view.findViewById(R.id.set_name_shopkeeper)
        shopkeeperPhone = view.findViewById(R.id.set_phone_shopkeeper)
        shopkeeperShopName = view.findViewById(R.id.set_shop_name)
        shopkeeperShopId = view.findViewById(R.id.set_shopId_shopkeeper)
        shopkeeperPassword = view.findViewById(R.id.set_password_shopkeeper)
        createAccount.setOnClickListener { CreateAccount() }
    }

    private fun CreateAccount() {
        val name = shopkeeperName!!.text.toString()
        val phone = shopkeeperPhone!!.text.toString()
        val shopNo = shopkeeperShopId!!.text.toString()
        val pass = shopkeeperPassword!!.text.toString()
        val shopName = shopkeeperShopName!!.text.toString()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(activity, "Please write a name", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(activity, "Please enter a phone number", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(shopName)) {
            Toast.makeText(activity, "Please give a shop name", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(shopNo)) {
            Toast.makeText(
                activity,
                "Please provide shop's registration number",
                Toast.LENGTH_SHORT
            ).show()
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(activity, "Please give a password", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Please hang in there for a while.", Toast.LENGTH_LONG).show()
            ValidatephoneNumber(name, shopNo, phone, pass, shopName)
        }
    }

    private fun ValidatephoneNumber(
        name: String,
        shopNo: String,
        phone: String,
        pass: String,
        shopName: String
    ) {
        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().getReference("Shopkeepers")
        RootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.child("Users").child(shopNo).exists()) {
                    val userdataMap = HashMap<String, Any>()
                    userdataMap["shopID"] = shopNo
                    userdataMap["Shopname"] = shopName
                    userdataMap["phone"] = phone
                    userdataMap["password"] = pass
                    userdataMap["Username"] = name
                    RootRef.child("Users").child(shopNo).updateChildren(userdataMap)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(activity, shopkeeperMain::class.java)
                                intent.putExtra("shopName", shopName)
                                val valAtLogin = Intent(activity, shopkeeperFragLogin::class.java)
                                valAtLogin.putExtra("shopName", shopName)
                                intent.putExtra("Id", shopNo)
                                Toast.makeText(
                                    activity,
                                    "Your account has been created successfully !",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                    Toast.makeText(
                        activity,
                        "Shop number $shopNo already exists.",
                        Toast.LENGTH_LONG
                    ).show()
                    Toast.makeText(activity, "Please enter valid shop number.", Toast.LENGTH_SHORT)
                        .show()
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
         * @return A new instance of fragment shopkeeperFragRegister.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): shopkeeperFragRegister {
            val fragment = shopkeeperFragRegister()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}