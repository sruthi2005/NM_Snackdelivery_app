package com.example.snackordering

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.snackordering.ui.theme.SnackOrderingTheme

class TargetActivity : ComponentActivity() {
    private lateinit var orderDatabaseHelper: OrderDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderDatabaseHelper = OrderDatabaseHelper(this)

        setContent {
            SnackOrderingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    OrderScreen(this, orderDatabaseHelper)
                }
            }
        }
    }
}

@Composable
fun OrderScreen(context: Context, orderDatabaseHelper: OrderDatabaseHelper) {
    // Background Image
    Image(
        painter = painterResource(id = R.drawable.order),
        contentDescription = "Order background",
        alpha = 0.4f,
        contentScale = ContentScale.FillHeight,
        modifier = Modifier.fillMaxHeight()
    )

    // Main Column for form layout
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Order Form State
        var quantity by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var error by remember { mutableStateOf("") }

        // Quantity Input Field
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color.White),
            shape = MaterialTheme.shapes.medium
        )

        // Address Input Field
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color.White),
            shape = MaterialTheme.shapes.medium
        )

        // Error Message
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Order Button
        Button(
            onClick = {
                if (quantity.isNotEmpty() && address.isNotEmpty()) {
                    val order = Order(
                        id = null,
                        quantity = quantity,
                        address = address
                    )
                    orderDatabaseHelper.insertOrder(order)
                    Toast.makeText(context, "Order Placed Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    error = "Please fill in all fields"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00A0FF)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Place Order",
                color = Color.White,
                style = MaterialTheme.typography.button
            )
        }
    }
}

private fun startMainPage(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    ContextCompat.startActivity(context, intent, null)
}
