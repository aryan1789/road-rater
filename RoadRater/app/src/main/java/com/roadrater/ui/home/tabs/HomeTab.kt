package com.roadrater.ui.home.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCarFilled
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.roadrater.R
import com.roadrater.presentation.util.Tab

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab

    override val options: TabOptions
        @Composable
        get() {
            val image = rememberVectorPainter(Icons.Outlined.DirectionsCarFilled)
            return TabOptions(
                index = 0u,
                title = stringResource(R.string.home_tab),
                icon = image,
            )
        }

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { HomeTabScreenModel() }

        Scaffold(
            topBar = {},
            floatingActionButton = {},
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Text("Home Tab")
            }
        }
    }
}
