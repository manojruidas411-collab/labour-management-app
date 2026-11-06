package com.labourlink.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    FirebaseApp.initializeApp(this)
    setContent { LabourLinkScreen() }
  }
}

@Composable
fun LabourLinkScreen() {
  val auth = remember { FirebaseAuth.getInstance() }
  val db = remember { FirebaseFirestore.getInstance() }
  var page by remember { mutableStateOf("Home") }
  var name by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var address by remember { mutableStateOf("") }
  var factory by remember { mutableStateOf("") }
  var factoryAddress by remember { mutableStateOf("") }
  var worker by remember { mutableStateOf("") }
  var wage by remember { mutableStateOf("500") }
  var petrol by remember { mutableStateOf("0") }
  var message by remember { mutableStateOf("Welcome to LabourLink") }
  val factories = remember { mutableStateListOf<String>() }
  val workers = remember { mutableStateListOf<String>() }
  val duties = remember { mutableStateListOf<String>() }
  val uid = auth.currentUser?.uid ?: "Not signed in"
  MaterialTheme {
    Scaffold(topBar = { TopAppBar(title = { Text("LabourLink") }) }, bottomBar = {
      NavigationBar { listOf("Home", "Workers", "Factories", "Duty", "Ledger").forEach { item ->
        NavigationBarItem(selected = page == item, onClick = { page = item }, icon = {}, label = { Text(item) })
      } }
    }) { padding ->
      Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
        Text(message, style = MaterialTheme.typography.bodyMedium)
        when (page) {
          "Home" -> {
            Text("Labour management made simple", style = MaterialTheme.typography.headlineSmall)
            Text("Account UID: " + uid)
            Text("Workers: " + workers.size + "   Factories: " + factories.size + "   Duty entries: " + duties.size)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(name, { name = it }, label = { Text("Full name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(phone, { phone = it }, label = { Text("Mobile number") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(address, { address = it }, label = { Text("Address") }, modifier = Modifier.fillMaxWidth())
            Button(onClick = {
              val current = auth.currentUser
              if (current == null) message = "Google sign-in needs Firebase OAuth Web Client ID first."
              else db.collection("users").document(current.uid).set(mapOf("fullName" to name, "phone" to phone, "address" to address))
            }, modifier = Modifier.fillMaxWidth()) { Text("Save profile") }
          }
          "Workers" -> {
            Text("Workers", style = MaterialTheme.typography.headlineSmall)
            workers.forEach { Text("• " + it) }
            OutlinedTextField(name, { name = it }, label = { Text("Worker name") }, modifier = Modifier.fillMaxWidth())
            Button(onClick = { workers.add(name); message = "Worker added locally. Sign-in and database rules are still required." }) { Text("Add worker") }
          }
          "Factories" -> {
            Text("Factories", style = MaterialTheme.typography.headlineSmall)
            factories.forEach { Text("• " + it) }
            OutlinedTextField(factory, { factory = it }, label = { Text("Factory name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(factoryAddress, { factoryAddress = it }, label = { Text("Factory address") }, modifier = Modifier.fillMaxWidth())
            Button(onClick = { factories.add(factory + " - " + factoryAddress); message = "Factory added to this session." }) { Text("Add factory") }
          }
          "Duty" -> {
            Text("Factory appointments", style = MaterialTheme.typography.headlineSmall)
            workers.forEach { TextButton(onClick = { worker = it }) { Text(if (worker == it) "✓ " + it else it) } }
            OutlinedTextField(wage, { wage = it }, label = { Text("Daily wage ₹") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(petrol, { petrol = it }, label = { Text("Petrol allowance ₹") }, modifier = Modifier.fillMaxWidth())
            Button(onClick = { duties.add(worker + " | " + factory + " | ₹" + wage + " + ₹" + petrol); message = "Duty entry added to this session." }) { Text("Assign duty") }
            duties.forEach { Text(it) }
          }
          else -> {
            Text("Wage ledger", style = MaterialTheme.typography.headlineSmall)
            Text("Duty entries: " + duties.size)
            duties.forEach { Text(it) }
          }
        }
      }
    }
  }
}