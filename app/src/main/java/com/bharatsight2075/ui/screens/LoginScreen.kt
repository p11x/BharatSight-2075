package com.bharatsight2075.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
 import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bharatsight2075.R
import com.bharatsight2075.ui.components.BharatSightLogo
import com.bharatsight2075.ui.theme.GridBackgroundSurface
import com.bharatsight2075.ui.theme.SciFiTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val googleLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
        navController.navigate(Routes.WELCOME) { popUpTo(Routes.LOGIN) { inclusive = true } }
    }
    var showPhoneSheet by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }
    
    val monoFamily = FontFamily.Monospace
    val onSurfaceColor = SciFiTheme.colors.onSurface
    val surfaceColor = SciFiTheme.colors.surface

    Box(Modifier.fillMaxSize().background(Color(0xFF050510))) {
        // Grid background
        GridBackgroundSurface {
            // Content
        }

        // Decorative: animated India silhouette in background
        Canvas(Modifier.fillMaxSize()) {
            val mapSize = Size(size.width * 0.5f, size.height * 0.5f)
            drawPath(
                path = IndiaOutlinePath(mapSize),
                color = Color(0xFF00F5FF).copy(0.04f),
                style = Stroke(width = 1.dp.toPx())
            )
        }

        LazyColumn(
            Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(32.dp)
        ) {
            // LOGO + TITLE
            item {
                Column(horizontalAlignment = CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    BharatSightLogo(size = 72.dp, animated = true)
                    Text(text = "BHARATSIGHT", fontSize = 24.sp, fontFamily = monoFamily, fontWeight = Bold, color = Color(0xFF00F5FF), letterSpacing = 0.1.em)
                    Text(text = "2075", fontSize = 14.sp, fontFamily = monoFamily, color = Color(0xFFFF6B35))
                    Text(
                        text = "Sign in to access India's most advanced economic intelligence platform",
                        fontSize = 12.sp,
                        color = onSurfaceColor.copy(0.5f),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
                Spacer(Modifier.height(40.dp))
            }

            // LOGIN CARD
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF0E0E1A))
                        .border(1.dp, Color(0xFF00F5FF).copy(0.2f), RoundedCornerShape(20.dp))
                        .padding(24.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(text = "CHOOSE SIGN-IN METHOD", fontSize = 9.sp, fontFamily = monoFamily, color = Color(0xFF00F5FF).copy(0.5f), letterSpacing = 0.15.em)
                        Spacer(Modifier.height(4.dp))

                        // GOOGLE BUTTON
                        LoginButton(
                            icon = { Image(painterResource(R.drawable.ic_google), "Google", Modifier.size(20.dp)) },
                            label = "Continue with Google",
                            primaryColor = Color(0xFF4285F4),
                            onClick = {
                                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
                                val client = GoogleSignIn.getClient(context, gso)
                                googleLauncher.launch(client.signInIntent)
                            }
                        )

                        // PHONE OTP BUTTON
                        LoginButton(
                            icon = { Icon(Icons.Outlined.Phone, "Phone", Modifier.size(20.dp), Color(0xFF00E676)) },
                            label = "Continue with Phone",
                            primaryColor = Color(0xFF00E676),
                            onClick = { showPhoneSheet = true }
                        )

                        // MICROSOFT / OUTLOOK
                        LoginButton(
                            icon = { Icon(Icons.Outlined.Email, "Microsoft", Modifier.size(20.dp), Color(0xFF00B4FF)) },
                            label = "Continue with Microsoft",
                            primaryColor = Color(0xFF00B4FF),
                            onClick = { /* Microsoft OAuth */ }
                        )

                        HorizontalDivider(Modifier.padding(vertical = 4.dp), color = Color(0xFF00F5FF).copy(0.1f))

                        // GUEST MODE
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFF00F5FF).copy(0.2f), RoundedCornerShape(12.dp))
                                .clickable {
                                    navController.navigate(Routes.WELCOME) { popUpTo(Routes.LOGIN) { inclusive = true } }
                                }
                                .padding(14.dp),
                            contentAlignment = Center
                        ) {
                            Row(verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Outlined.VisibilityOff, null, Modifier.size(18.dp), Color(0xFF00F5FF).copy(0.4f))
                                Text(text = "Continue as Guest", fontSize = 13.sp, fontFamily = monoFamily, color = Color(0xFF00F5FF).copy(0.6f))
                            }
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
            }

            // FOOTER
            item {
                Column(horizontalAlignment = CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "By continuing you agree to our Terms & Privacy Policy", fontSize = 9.sp, fontFamily = monoFamily, color = onSurfaceColor.copy(0.3f), textAlign = TextAlign.Center)
                    Text(text = "BharatSight 2075 · Economic Intelligence Platform", fontSize = 8.sp, fontFamily = monoFamily, color = Color(0xFF00F5FF).copy(0.2f))
                }
            }
        }

        // PHONE OTP BOTTOM SHEET
        if (showPhoneSheet) {
            ModalBottomSheet(
                onDismissRequest = { showPhoneSheet = false },
                containerColor = Color(0xFF0E0E1A),
                dragHandle = {
                    Box(Modifier.width(40.dp).height(4.dp).background(Color(0xFF00F5FF).copy(0.3f), RoundedCornerShape(2.dp)))
                }
            ) {
                Column(Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(text = "PHONE VERIFICATION", fontSize = 10.sp, fontFamily = monoFamily, color = Color(0xFF00F5FF).copy(0.5f), letterSpacing = 0.15.em)
                    if (!otpSent) {
                        // Phone input
                        Text(text = "Enter your mobile number", fontSize = 13.sp, fontFamily = monoFamily, color = onSurfaceColor)
                        Row(verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Country code box
                            Box(
                                Modifier
                                    .border(1.dp, Color(0xFF00F5FF).copy(0.3f), RoundedCornerShape(10.dp))
                                    .background(surfaceColor)
                                    .padding(12.dp)
                            ) {
                                Text(text = "+91 🇮🇳", fontSize = 13.sp, fontFamily = monoFamily, color = onSurfaceColor)
                            }
                            // Phone field
                            BasicTextField(
                                value = phoneNumber,
                                onValueChange = { if (it.length <= 10) phoneNumber = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .border(1.dp, Color(0xFF00F5FF).copy(0.3f), RoundedCornerShape(10.dp))
                                    .background(surfaceColor)
                                    .padding(12.dp),
                                textStyle = TextStyle(fontSize = 13.sp, fontFamily = monoFamily, color = onSurfaceColor),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                decorationBox = { inner ->
                                    if (phoneNumber.isEmpty()) Text(text = "10-digit number", fontSize = 13.sp, fontFamily = monoFamily, color = onSurfaceColor.copy(0.3f))
                                    inner()
                                }
                            )
                        }
                        // Send OTP button
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Brush.horizontalGradient(listOf(Color(0xFF00F5FF), Color(0xFF00B0FF))))
                                .clickable { if (phoneNumber.length == 10) otpSent = true }
                                .padding(14.dp),
                            contentAlignment = Center
                        ) {
                            Text(text = "SEND OTP", fontSize = 13.sp, fontFamily = monoFamily, fontWeight = Bold, color = Color(0xFF050510), letterSpacing = 0.1.em)
                        }
                    } else {
                        // OTP input
                        Text(text = "Enter the 6-digit OTP sent to +91 $phoneNumber", fontSize = 12.sp, fontFamily = monoFamily, color = onSurfaceColor.copy(0.6f))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            repeat(6) { i ->
                                val digit = otp.getOrNull(i)?.toString() ?: ""
                                Box(
                                    Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .border(1.5.dp, if (otp.length == i) Color(0xFF00F5FF) else Color(0xFF00F5FF).copy(0.2f), RoundedCornerShape(10.dp))
                                        .background(surfaceColor.copy(0.5f)),
                                    contentAlignment = Center
                                ) {
                                    Text(text = digit, fontSize = 18.sp, fontFamily = monoFamily, fontWeight = Bold, color = Color(0xFF00F5FF))
                                }
                            }
                        }
                        // Hidden BasicTextField capturing OTP input
                        BasicTextField(
                            value = otp,
                            onValueChange = {
                                if (it.length <= 6) {
                                    otp = it
                                    if (it.length == 6) {
                                        /* verify OTP */
                                        navController.navigate(Routes.WELCOME) { popUpTo(Routes.LOGIN) { inclusive = true } }
                                    }
                                }
                            },
                            modifier = Modifier.size(1.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Text(
                            text = "Resend OTP",
                            fontSize = 11.sp,
                            fontFamily = monoFamily,
                            color = Color(0xFF00F5FF).copy(0.5f),
                            modifier = Modifier.clickable { otp = ""; otpSent = false }
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun LoginButton(icon: @Composable () -> Unit, label: String, primaryColor: Color, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.97f else 1f, spring(stiffness = 600f), label = "ButtonScale")
    val onSurfaceColor = SciFiTheme.colors.onSurface
    val monoFamily = FontFamily.Monospace

    Box(
        Modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(14.dp))
            .background(primaryColor.copy(0.07f))
            .border(1.dp, primaryColor.copy(0.35f), RoundedCornerShape(14.dp))
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onClick() }
            .padding(14.dp)
    ) {
        Row(verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            icon()
            Text(text = label, fontSize = 13.sp, fontFamily = monoFamily, fontWeight = SemiBold, color = onSurfaceColor.copy(0.85f))
            Spacer(Modifier.weight(1f))
            Icon(Icons.Outlined.ChevronRight, null, Modifier.size(16.dp), primaryColor.copy(0.5f))
        }
    }
}

fun IndiaOutlinePath(size: Size): Path = Path().apply {
    val pts = listOf(0.42f to 0.04f, 0.48f to 0.02f, 0.56f to 0.04f, 0.62f to 0.08f, 0.68f to 0.06f, 0.72f to 0.12f, 0.78f to 0.14f, 0.82f to 0.20f, 0.80f to 0.28f, 0.76f to 0.32f, 0.82f to 0.36f, 0.80f to 0.44f, 0.74f to 0.48f, 0.76f to 0.54f, 0.72f to 0.60f, 0.68f to 0.62f, 0.64f to 0.70f, 0.60f to 0.76f, 0.56f to 0.82f, 0.52f to 0.88f, 0.48f to 0.92f, 0.50f to 0.98f, 0.48f to 0.96f, 0.44f to 0.90f, 0.40f to 0.84f, 0.36f to 0.78f, 0.32f to 0.72f, 0.28f to 0.68f, 0.24f to 0.64f, 0.22f to 0.58f, 0.24f to 0.52f, 0.20f to 0.48f, 0.18f to 0.42f, 0.16f to 0.36f, 0.18f to 0.30f, 0.22f to 0.26f, 0.26f to 0.22f, 0.28f to 0.16f, 0.34f to 0.10f, 0.38f to 0.06f)
    pts.forEachIndexed { i, (x, y) -> if (i == 0) moveTo(x * size.width, y * size.height) else lineTo(x * size.width, y * size.height) }
    close()
}
