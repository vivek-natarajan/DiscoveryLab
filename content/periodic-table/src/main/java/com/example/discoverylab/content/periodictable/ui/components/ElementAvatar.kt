package com.example.discoverylab.content.periodictable.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discoverylab.content.periodictable.data.Element

/**
 * Avatar placeholder for an element showing a fun emoji representation.
 * These will be replaced with actual illustrations later.
 */
@Composable
fun ElementAvatar(
    element: Element,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp
) {
    val primaryColor = element.category.toPrimaryColor()
    val lightColor = element.category.toLightColor()
    val emoji = getElementEmoji(element.atomicNumber)

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(lightColor, primaryColor.copy(alpha = 0.3f))
                )
            )
            .border(
                width = 2.dp,
                color = primaryColor.copy(alpha = 0.4f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = (size.value * 0.45f).sp
        )
    }
}

/**
 * Returns a fun emoji representation for each element.
 * These serve as placeholders until real avatars are added.
 */
fun getElementEmoji(atomicNumber: Int): String {
    return when (atomicNumber) {
        // Period 1
        1 -> "\uD83D\uDCA7"  // H - Water droplet
        2 -> "\uD83C\uDF88"  // He - Balloon

        // Period 2
        3 -> "\uD83D\uDD0B"  // Li - Battery
        4 -> "\uD83D\uDC8E"  // Be - Gem
        5 -> "\uD83E\uDDFC"  // B - Soap
        6 -> "\uD83D\uDC8E"  // C - Diamond
        7 -> "\uD83C\uDF43"  // N - Leaf
        8 -> "\uD83C\uDF2C\uFE0F"  // O - Wind
        9 -> "\uD83E\uDDB7"  // F - Tooth
        10 -> "\u2728"  // Ne - Sparkles

        // Period 3
        11 -> "\uD83E\uDDC2"  // Na - Salt
        12 -> "\uD83C\uDF3F"  // Mg - Plant
        13 -> "\uD83E\uDD64"  // Al - Can
        14 -> "\uD83D\uDCF1"  // Si - Phone
        15 -> "\uD83D\uDCA1"  // P - Light bulb
        16 -> "\uD83C\uDF4B"  // S - Lemon
        17 -> "\uD83C\uDFCA"  // Cl - Swimmer
        18 -> "\uD83D\uDCA8"  // Ar - Air

        // Period 4
        19 -> "\uD83C\uDF4C"  // K - Banana
        20 -> "\uD83E\uDD5B"  // Ca - Milk
        21 -> "\uD83C\uDFC6"  // Sc - Trophy
        22 -> "\u2708\uFE0F"  // Ti - Airplane
        23 -> "\uD83D\uDD27"  // V - Wrench
        24 -> "\uD83D\uDEBF"  // Cr - Chrome
        25 -> "\uD83E\uDDF2"  // Mn - Magnet
        26 -> "\uD83D\uDEE0\uFE0F"  // Fe - Tools
        27 -> "\uD83D\uDC99"  // Co - Blue heart
        28 -> "\uD83E\uDE99"  // Ni - Coin
        29 -> "\uD83E\uDD49"  // Cu - Medal
        30 -> "\uD83D\uDD0B"  // Zn - Battery
        31 -> "\uD83D\uDCBB"  // Ga - Computer
        32 -> "\uD83D\uDD2E"  // Ge - Crystal
        33 -> "\u2620\uFE0F"  // As - Skull
        34 -> "\uD83E\uDDA0"  // Se - Microbe
        35 -> "\uD83D\uDFE4"  // Br - Brown
        36 -> "\uD83D\uDCA1"  // Kr - Light

        // Period 5
        37 -> "\u23F0"  // Rb - Clock
        38 -> "\uD83C\uDF86"  // Sr - Fireworks
        39 -> "\uD83D\uDCFA"  // Y - TV
        40 -> "\uD83D\uDC8D"  // Zr - Ring
        41 -> "\uD83D\uDE80"  // Nb - Rocket
        42 -> "\u2699\uFE0F"  // Mo - Gear
        43 -> "\u2622\uFE0F"  // Tc - Radioactive
        44 -> "\uD83D\uDD8A\uFE0F"  // Ru - Pen
        45 -> "\uD83D\uDC8E"  // Rh - Gem
        46 -> "\uD83D\uDE97"  // Pd - Car
        47 -> "\uD83E\uDD48"  // Ag - Silver
        48 -> "\uD83D\uDD0B"  // Cd - Battery
        49 -> "\uD83D\uDCF1"  // In - Phone
        50 -> "\uD83E\uDD6B"  // Sn - Can
        51 -> "\uD83D\uDD25"  // Sb - Fire
        52 -> "\uD83D\uDCBF"  // Te - CD
        53 -> "\uD83E\uDE79"  // I - Bandage
        54 -> "\uD83D\uDCA1"  // Xe - Light

        // Period 6
        55 -> "\u23F1\uFE0F"  // Cs - Timer
        56 -> "\u2615"  // Ba - Coffee
        57 -> "\uD83D\uDD2D"  // La - Telescope
        58 -> "\uD83D\uDD25"  // Ce - Fire
        59 -> "\uD83D\uDFE2"  // Pr - Green
        60 -> "\uD83E\uDDF2"  // Nd - Magnet
        61 -> "\u2622\uFE0F"  // Pm - Radioactive
        62 -> "\uD83E\uDDF2"  // Sm - Magnet
        63 -> "\uD83D\uDCFA"  // Eu - TV
        64 -> "\uD83C\uDFE5"  // Gd - Hospital
        65 -> "\uD83D\uDFE2"  // Tb - Green
        66 -> "\uD83E\uDDF2"  // Dy - Magnet
        67 -> "\uD83D\uDFE1"  // Ho - Yellow
        68 -> "\uD83D\uDFE3"  // Er - Purple
        69 -> "\uD83D\uDD2C"  // Tm - Microscope
        70 -> "\u2699\uFE0F"  // Yb - Gear
        71 -> "\uD83D\uDE91"  // Lu - Ambulance
        72 -> "\uD83D\uDCA1"  // Hf - Light
        73 -> "\uD83D\uDCF1"  // Ta - Phone
        74 -> "\uD83D\uDCA1"  // W - Tungsten light
        75 -> "\u2708\uFE0F"  // Re - Airplane
        76 -> "\uD83D\uDD8A\uFE0F"  // Os - Pen
        77 -> "\uD83D\uDD25"  // Ir - Spark
        78 -> "\uD83D\uDC8D"  // Pt - Ring
        79 -> "\uD83E\uDD47"  // Au - Gold
        80 -> "\uD83C\uDF21\uFE0F"  // Hg - Thermometer
        81 -> "\u2620\uFE0F"  // Tl - Skull
        82 -> "\uD83D\uDD0B"  // Pb - Battery
        83 -> "\uD83D\uDC8A"  // Bi - Pill
        84 -> "\u2622\uFE0F"  // Po - Radioactive
        85 -> "\u2622\uFE0F"  // At - Radioactive
        86 -> "\u2622\uFE0F"  // Rn - Radioactive

        // Period 7
        87 -> "\u2622\uFE0F"  // Fr - Radioactive
        88 -> "\u2622\uFE0F"  // Ra - Radioactive
        89 -> "\u2622\uFE0F"  // Ac - Radioactive
        90 -> "\u2622\uFE0F"  // Th - Radioactive
        91 -> "\u2622\uFE0F"  // Pa - Radioactive
        92 -> "\u2622\uFE0F"  // U - Radioactive
        93 -> "\u2622\uFE0F"  // Np - Radioactive
        94 -> "\u2622\uFE0F"  // Pu - Radioactive
        95 -> "\u2622\uFE0F"  // Am - Radioactive
        96 -> "\u2622\uFE0F"  // Cm - Radioactive
        97 -> "\u2622\uFE0F"  // Bk - Radioactive
        98 -> "\u2622\uFE0F"  // Cf - Radioactive
        99 -> "\u2622\uFE0F"  // Es - Radioactive
        100 -> "\u2622\uFE0F" // Fm - Radioactive
        101 -> "\u2622\uFE0F" // Md - Radioactive
        102 -> "\u2622\uFE0F" // No - Radioactive
        103 -> "\u2622\uFE0F" // Lr - Radioactive
        104 -> "\uD83D\uDD2C" // Rf - Microscope
        105 -> "\uD83D\uDD2C" // Db - Microscope
        106 -> "\uD83D\uDD2C" // Sg - Microscope
        107 -> "\uD83D\uDD2C" // Bh - Microscope
        108 -> "\uD83D\uDD2C" // Hs - Microscope
        109 -> "\uD83D\uDD2C" // Mt - Microscope
        110 -> "\uD83D\uDD2C" // Ds - Microscope
        111 -> "\uD83D\uDD2C" // Rg - Microscope
        112 -> "\uD83D\uDD2C" // Cn - Microscope
        113 -> "\uD83D\uDD2C" // Nh - Microscope
        114 -> "\uD83D\uDD2C" // Fl - Microscope
        115 -> "\uD83D\uDD2C" // Mc - Microscope
        116 -> "\uD83D\uDD2C" // Lv - Microscope
        117 -> "\uD83D\uDD2C" // Ts - Microscope
        118 -> "\uD83D\uDD2C" // Og - Microscope

        else -> "\u2753"  // Question mark
    }
}

/**
 * Get a kid-friendly description for the element's real-world appearance.
 */
fun getElementAvatarDescription(atomicNumber: Int): String {
    return when (atomicNumber) {
        1 -> "Found in water!"
        2 -> "Makes balloons float!"
        3 -> "Powers your phone!"
        4 -> "Found in pretty gems!"
        5 -> "In cleaning products!"
        6 -> "Diamonds are made of this!"
        7 -> "Most of the air you breathe!"
        8 -> "Helps you breathe!"
        9 -> "In your toothpaste!"
        10 -> "Makes colorful signs glow!"
        11 -> "In your table salt!"
        12 -> "Helps plants grow!"
        13 -> "Soda cans are made of this!"
        14 -> "In computer chips!"
        15 -> "Makes things glow in the dark!"
        16 -> "Has a stinky smell!"
        17 -> "Keeps swimming pools clean!"
        18 -> "In the air around you!"
        19 -> "In bananas!"
        20 -> "Makes your bones strong!"
        26 -> "In nails and tools!"
        29 -> "Pennies and wires!"
        47 -> "Shiny like mirrors!"
        79 -> "Precious gold!"
        else -> "A special element!"
    }
}
