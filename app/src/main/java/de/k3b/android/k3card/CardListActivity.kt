package de.k3b.android.k3card

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent


import de.k3b.android.k3card.ui.theme.K3CardTheme

class CardListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            K3CardTheme {
                    Conversation(SampleMessageData.conversationSample)
                }
        }
    }
}

