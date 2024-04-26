package ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import po11nt.composeapp.generated.resources.*
import po11nt.composeapp.generated.resources.Res
import po11nt.composeapp.generated.resources.khandlight
import po11nt.composeapp.generated.resources.khandmedium
import po11nt.composeapp.generated.resources.khandregular

@OptIn(ExperimentalResourceApi::class)
@Composable
fun getTypography(): Typography {
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily(Font(Res.font.khandregular)),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodySmall = TextStyle(
            fontFamily = FontFamily(Font(Res.font.khandlight)),
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = FontFamily(Font(Res.font.khandlight)),
            fontWeight = FontWeight.Light,
            fontSize = 200.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = FontFamily(Font(Res.font.khandregular)),
            fontWeight = FontWeight.Normal,
            fontSize = 48.sp
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily(Font(Res.font.khandsemibold)),
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            lineHeight = 32.sp
        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily(Font(Res.font.khandsemibold)),
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),
        labelLarge = TextStyle(
            fontFamily = FontFamily(Font(Res.font.khandregular)),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        labelMedium = TextStyle(
            fontFamily = FontFamily(Font(Res.font.khandlight)),
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily(Font(Res.font.khandmedium)),
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 12.sp
        )
    )
}