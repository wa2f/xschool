package com.example.school.basaa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.school.R
import com.example.school.basaa.upload.FeeModel
import com.example.school.parents.model.QrModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FeesActivity : AppCompatActivity() {

    private lateinit var name:EditText
    private lateinit var dbRef: DatabaseReference
    private lateinit var adm:EditText
    private lateinit var paymentbtn:Button
    private lateinit var amount:EditText
    private lateinit var date:EditText
    private lateinit var paymentmethod:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fees)
        supportActionBar?.hide()

        dbRef = FirebaseDatabase.getInstance().getReference("feespayed")
        paymentbtn = findViewById(R.id.payfees)

        name = findViewById(R.id.stdname)
        adm = findViewById(R.id.stdadm)
        paymentmethod = findViewById(R.id.modeofpayment)
        date = findViewById(R.id.paymentdate)
        amount = findViewById(R.id.amount)

        paymentbtn.setOnClickListener {
            uploadfees()
        }
    }

    private fun uploadfees() {
        val namee = name.text.toString().trim()
        val qr = adm.text.toString().trim()
        val paymentmethode = paymentmethod.text.toString().trim()
        val datee = date.text.toString().trim()
        val amoutnt= amount.text.toString().trim()

        if (name.text.isEmpty()){
            name.error ="please enter the student name"
        }else if (adm.text.isEmpty()){
            adm.error = "please enter the qr"
        }else if (paymentmethod.text.isEmpty()){
            paymentmethod.error = "please enter the qr"
        }else{
            val users = FeeModel(namee,qr,datee,amoutnt,paymentmethode)
            dbRef.child(namee).setValue(users)
                .addOnCompleteListener {
                    Toast.makeText(this,"sent", Toast.LENGTH_LONG).show()
                    name.text.clear()
                    adm.text.clear()
                    paymentmethod.text.clear()
                    date.text.clear()
                    amount.text.clear()
                }.addOnFailureListener {
                    Toast.makeText(this, "not sent", Toast.LENGTH_LONG).show()
                }
        }
    }
}