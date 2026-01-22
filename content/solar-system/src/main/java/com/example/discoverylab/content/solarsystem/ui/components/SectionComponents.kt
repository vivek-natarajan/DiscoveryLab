package com.example.discoverylab.content.solarsystem.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discoverylab.content.solarsystem.ui.theme.SolarColors

/**
 * Section header with underline divider.
 */
@Composable
fun SectionHeader(title: String) {
    Column {
        Text(
            text = title,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = SolarColors.textPrimary,
            letterSpacing = 1.5.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(color = SolarColors.divider, thickness = 1.dp)
    }
}

/**
 * Property row showing label and value.
 */
@Composable
fun PropertyRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = SolarColors.textSecondary,
            letterSpacing = 0.5.sp,
            modifier = Modifier.weight(0.45f)
        )
        Text(
            text = value,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = SolarColors.textPrimary,
            textAlign = TextAlign.End,
            maxLines = 1,
            modifier = Modifier.weight(0.55f)
        )
    }
}

/**
 * Bullet point item.
 */
@Composable
fun BulletItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
    ) {
        Text(
            text = "•",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = SolarColors.textSecondary,
            modifier = Modifier.width(16.dp)
        )
        Text(
            text = text,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = SolarColors.textPrimary,
            lineHeight = 16.sp
        )
    }
}

/**
 * Fun fact / trivia item.
 */
@Composable
fun TriviaItem(text: String) {
    Text(
        text = text,
        fontFamily = FontFamily.Monospace,
        fontSize = 11.sp,
        color = SolarColors.textSecondary,
        lineHeight = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
    )
}

/**
 * Format number with proper rounding to 2 decimal places.
 */
fun formatNumber(value: Double): String {
    return if (value == value.toLong().toDouble()) {
        value.toLong().toString()
    } else {
        String.format("%.2f", value).trimEnd('0').trimEnd('.')
    }
}
