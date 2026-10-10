package com.samuel.session3_mdi2103ch12

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuel.session3_mdi2103ch12.ui.theme.Session3_MDI2103Ch12Theme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Session3_MDI2103Ch12Theme {
                RestaurantScreen()
            }
        }
    }
}

@Composable
fun RestaurantScreen(){
    //Challenge 2 SESSION 3
    //ARRAY
    val vipCustomers = arrayOf("Emma", "Noah", "Olivia", "Alex", "Karen")
    //QUEUE
    val waitingList =remember { mutableStateListOf<String>() }
    //STACK
    val seatedHistory = remember { mutableStateListOf<String>() }
    //STATE VARIABLES
    var customerName by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("Welcome!")}
    val customersTime = remember { mutableStateMapOf<String, String>() }

    //Challenge 3
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        //CHALLENGE 4
        Text(
            text = "Restaurant Waiting List",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        //CHALLENGE 5
        OutlinedTextField(
            value = customerName,
            onValueChange = { customerName = it },
            label = { Text("Customer Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (customerName.isNotBlank()){
                    val newCustomer = customerName
                    val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))

                    waitingList.add(customerName)
                    customersTime[newCustomer] = currentTime
                    message = "$customerName added to waiting list"
                    customerName = ""
                } else {
                    message = "Please enter a name"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Add customer")
        }

        Spacer(modifier = Modifier.height(8.dp))


        //Challenge 6
        Button(
            onClick = {
                //waitingList.addAll(vipCustomers)
                //message = "VIP customer added"
                    val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))

                    vipCustomers.forEach { vipCustomers ->
                        waitingList.add(vipCustomers)
                        customersTime[vipCustomers] = currentTime
                    }

                    message = "VIP customers added at $currentTime"
                },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Load VIP customers")
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Challenge 7
        Button(
            onClick = {
                if (waitingList.isNotEmpty()){
                    val nextCustomer = waitingList.removeAt(0) //QUEUE : FIFO
                    seatedHistory.add(nextCustomer) // Stack: push
                    message = "Now seating: $nextCustomer"
                } else {
                    message = "No customers in line"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seat Next Customer")
        }

        //CHALLENGE 1 SESSION 4
        Spacer(modifier = Modifier.height(16.dp))

        Text("Message: $message")

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            //CHALLENGE 2
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Waiting List (Queue)")

                Spacer(modifier = Modifier.height(8.dp))

                //CHALLENGE 3
                if (waitingList.isEmpty()){
                    Text("No customers waiting")
                } else {
                    //waitingList.forEachIndexed { index, customer ->
                    //Text("${index +1 }, $customer")

                    waitingList.forEachIndexed { index, customer ->
                        val arrivalTime = customersTime[customer] ?: "Unknown"

                        Text("${index + 1}. $customer - Arrived at: $arrivalTime")
                    }
                }
            }
        }

        //CHALLENGE 4
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Seated History (Stack)")

                Spacer(modifier = Modifier.height(8.dp))

                //CHALLENGE 5
                if (seatedHistory.isEmpty()){
                    Text("No customers seated yet")
                } else {
                    seatedHistory.reversed().forEach { customer ->
                        Text("- $customer")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantScreenPreview(){
    MaterialTheme{
        RestaurantScreen()
    }
}