package de.k3b.android.k3card

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.k3b.android.k3card.ui.theme.K3CardTheme
import ezvcard.property.VCardProperty

@Composable
private fun PropertyCard(icon  : Any, text : String) {

}

@Composable
fun VCardPropertyCard(vproperty: VCardProperty) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        EmojiComponent(getVEmoji(vproperty))

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        VCardPropertyContent(vproperty)
    }
}

@Composable
private fun VCardPropertyContent(vproperty: VCardProperty) {
    // We keep track if the message is expanded or not in this
    // variable
    var isExpanded by remember { mutableStateOf(false) }

    // surfaceColor will be updated gradually from one color to the other
    val surfaceColor by animateColorAsState(
        if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        label = "messageAnimateColorAsState",
    )
    // We toggle the isExpanded variable when we click on this Column
    Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
        Text(
            text = getVValue(vproperty),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(4.dp))

        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 1.dp,
            // surfaceColor color will be changing gradually from primary to surface
            color = surfaceColor,
            // animateContentSize will change the Surface size gradually
            modifier = Modifier
                .animateContentSize()
                .padding(1.dp)

        ) {
            Text(
                text = getVValue(vproperty),
                modifier = Modifier.padding(all = 4.dp),
                // If the message is expanded, we display all its content
                // otherwise we only display the first line
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun EmojiComponent(emoji: String, imageId : Int = 0) {
    if (imageId != 0) {
        Image(
            painter = painterResource(imageId),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
    } else {
        // val icon = "[" + text.subSequence(0, 1) + "] "
        Text(text = emoji)
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)@Composable
fun PreviewVProperyCard() {
    K3CardTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            VCardPropertyCard(
                vproperty = SampleVCardData.cardWithDetails.formattedName
            )
        }
    }
}

/*
@Composable
fun Conversation(vcard: VCard) {
    LazyColumn {
        items(vcard.properties.filter { it != null && value(it).isNotBlank() }) {
            VCardPropertyCard(it)
        }
    }

}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)@Composable
fun PreviewConversation() {
    K3CardTheme {
        Conversation(SampleVCardData.cardListSample)
    }
}

 */