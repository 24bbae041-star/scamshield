package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ScamDatabase
import com.example.model.VolunteerProfile
import com.example.ui.ScanShieldViewModel
import com.example.ui.components.CuteStickerBadge
import com.example.ui.components.SeniorCard
import com.example.ui.components.SeniorPrimaryButton
import com.example.ui.components.StickerType
import com.example.ui.theme.ButterYellow
import com.example.ui.theme.ButterYellowContainer
import com.example.ui.theme.DarkInk
import com.example.ui.theme.DeepPurple
import com.example.ui.theme.Lavender
import com.example.ui.theme.LavenderContainer
import com.example.ui.theme.Mint
import com.example.ui.theme.MintContainer
import com.example.ui.theme.MintDark
import com.example.ui.theme.PeachContainer
import com.example.ui.theme.WarningRose
import kotlinx.coroutines.launch

@Composable
fun CommunityScreen(
    viewModel: ScanShieldViewModel,
    modifier: Modifier = Modifier
) {
    var selectedLanguageFilter by remember { mutableStateOf("All") }
    var selectedVolunteerForSession by remember { mutableStateOf<VolunteerProfile?>(null) }
    var sessionConfirmedVolunteer by remember { mutableStateOf<VolunteerProfile?>(null) }
    var userRole by remember { mutableStateOf("Senior Learner") } // or "Youth Volunteer"

    var showReportDialog by remember { mutableStateOf(false) }

    // Schedule Session Dialog
    if (selectedVolunteerForSession != null) {
        val volunteer = selectedVolunteerForSession!!
        var preferredTopic by remember { mutableStateOf("How to safely submit Jeevan Pramaan DLC") }
        var preferredDay by remember { mutableStateOf("This Saturday, 5:00 PM IST") }

        AlertDialog(
            onDismissRequest = { selectedVolunteerForSession = null },
            title = {
                Text(
                    text = "Request 1-on-1 Safety Mentorship",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column {
                    Text(
                        text = "With ${volunteer.name} (${volunteer.location})",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = DeepPurple
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Available time: ${volunteer.availabilityNote}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Select guidance topic:", style = MaterialTheme.typography.labelSmall)
                    OutlinedTextField(
                        value = preferredTopic,
                        onValueChange = { preferredTopic = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Preferred time slot:", style = MaterialTheme.typography.labelSmall)
                    OutlinedTextField(
                        value = preferredDay,
                        onValueChange = { preferredDay = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Safety Note: Mentors will NEVER ask for money, OTPs, or passwords. All sessions are 100% free.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MintDark
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        sessionConfirmedVolunteer = volunteer
                        selectedVolunteerForSession = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepPurple)
                ) {
                    Text("Submit Free Request")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedVolunteerForSession = null }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }

    // Session Confirmed Toast/Dialog
    if (sessionConfirmedVolunteer != null) {
        AlertDialog(
            onDismissRequest = { sessionConfirmedVolunteer = null },
            title = {
                Text(
                    text = "Mentorship Session Scheduled! 🎉",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MintDark
                )
            },
            text = {
                Text(
                    text = "Your request has been routed to ${sessionConfirmedVolunteer!!.name}. You will receive a friendly confirmation call or notification before the session. Reminder: Never share passwords or PINs with anyone.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = { sessionConfirmedVolunteer = null },
                    colors = ButtonDefaults.buttonColors(containerColor = MintDark)
                ) {
                    Text("Got It, Thank You")
                }
            }
        )
    }

    // Report Dialog
    if (showReportDialog) {
        AlertDialog(
            onDismissRequest = { showReportDialog = false },
            title = { Text("Report Community Concern") },
            text = {
                Text("Scan Shield has zero tolerance for misconduct. If any mentor or user violates community guidelines or asks for sensitive info, report immediately or email safety@scanshield.in.")
            },
            confirmButton = {
                Button(
                    onClick = { showReportDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepPurple)
                ) {
                    Text("Close")
                }
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CuteStickerBadge(type = StickerType.PHONE, size = 48.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Volunteer Community",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Generational digital safety mentorship & guidance",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF57526D)
                        )
                    }
                }

                IconButton(onClick = { showReportDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = "Report",
                        tint = Color.Gray
                    )
                }
            }
        }

        // Role Switcher: Senior Learner vs Youth Volunteer
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(LavenderContainer)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (userRole == "Senior Learner") DeepPurple else Color.Transparent)
                        .clickable { userRole = "Senior Learner" }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👴 Senior Learner",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = if (userRole == "Senior Learner") Color.White else DarkInk
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (userRole == "Youth Volunteer") DeepPurple else Color.Transparent)
                        .clickable { userRole = "Youth Volunteer" }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🎓 Youth Mentor",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = if (userRole == "Youth Volunteer") Color.White else DarkInk
                    )
                }
            }
        }

        // Strict Safety Guidelines Banner
        item {
            SeniorCard(
                backgroundColor = ButterYellowContainer,
                borderColor = Color(0xFFFFD56B)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = Color(0xFF7A5400), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Community Safety Guardrails",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF7A5400)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "1. Mentors NEVER ask for money, OTPs, UPI PINs, or passwords.\n" +
                                "2. Mentors NEVER ask to install remote-screen apps.\n" +
                                "3. Elderly personal contact info is kept strictly private.",
                        style = MaterialTheme.typography.bodySmall,
                        color = DarkInk
                    )
                }
            }
        }

        // Demo Notice
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF1ECFC))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Demo Mode: The 5 mentor profiles below are realistic fictional volunteers ready for simulated session scheduling.",
                    style = MaterialTheme.typography.labelSmall,
                    color = DeepPurple
                )
            }
        }

        // Language Filters
        item {
            val languages = listOf("All", "Hindi", "Gujarati", "Tamil", "Bengali", "Marathi", "Kannada", "Telugu")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(languages) { lang ->
                    val isSelected = selectedLanguageFilter == lang
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) DeepPurple else Color.White)
                            .border(1.dp, if (isSelected) DeepPurple else Color(0xFFE2DCF0), RoundedCornerShape(12.dp))
                            .clickable { selectedLanguageFilter = lang }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = lang,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (isSelected) Color.White else DarkInk
                        )
                    }
                }
            }
        }

        // Volunteer Profiles List
        val filteredVolunteers = ScamDatabase.VOLUNTEERS.filter {
            if (selectedLanguageFilter == "All") true
            else it.languages.contains(selectedLanguageFilter)
        }

        items(filteredVolunteers) { volunteer ->
            VolunteerCard(
                volunteer = volunteer,
                onRequestSession = { selectedVolunteerForSession = volunteer }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun VolunteerCard(
    volunteer: VolunteerProfile,
    onRequestSession: () -> Unit
) {
    SeniorCard(
        backgroundColor = Color.White,
        borderColor = Lavender
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(MintContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = MintDark, modifier = Modifier.size(26.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = volunteer.name,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = DarkInk
                            )
                            if (volunteer.verifiedBadge) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified Volunteer",
                                    tint = DeepPurple,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Text(
                            text = "${volunteer.location} • Age ${volunteer.age}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF57526D)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${volunteer.rating} (${volunteer.totalSessions})",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = DarkInk
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = volunteer.bio,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkInk
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Languages & Expertise Tags
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                volunteer.languages.forEach { lang ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(LavenderContainer)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(text = lang, style = MaterialTheme.typography.labelSmall, color = DeepPurple)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Available: ${volunteer.availabilityNote}",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = Color(0xFF57526D)
            )

            Spacer(modifier = Modifier.height(12.dp))

            SeniorPrimaryButton(
                text = "Request Free 1-on-1 Guidance",
                icon = Icons.Default.CalendarToday,
                containerColor = Mint,
                contentColor = MintDark,
                testTag = "request_volunteer_${volunteer.id}",
                onClick = onRequestSession
            )
        }
    }
}
