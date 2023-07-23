package com.bitflyer.testapp.ui.compose.userdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.bitflyer.testapp.R
import com.bitflyer.testapp.ui.userdetails.model.UserDetailsModel
import com.bitflyer.testapp.ui.userdetails.state.UserDetailsScreenState
import com.bitflyer.testapp.util.getTwitterLink
import com.bitflyer.testapp.util.getWebLink
import com.valentinilk.shimmer.shimmer

@Composable
fun UserDetailsScreen(
    onReloadClick: () -> Unit,
    state: UserDetailsScreenState
) {
    Box(Modifier.padding(16.dp)) {
        when (state) {
            UserDetailsScreenState.Loading -> {
                LoadingState()
            }

            UserDetailsScreenState.Error -> {
                ErrorState(onReloadClick)
            }

            is UserDetailsScreenState.Loaded -> {
                LoadedState(state.model)
            }
        }
    }
}

@Composable
fun LoadingState() {
    Column(modifier = Modifier.shimmer()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.tertiary,
            ) {}
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {}
        }
        Spacer(modifier = Modifier.height(height = 12.dp))
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {}
        Spacer(modifier = Modifier.height(height = 8.dp))
        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier
                .width(150.dp)
                .height(24.dp)
        ) {}
    }
}

@Composable
fun BoxScope.ErrorState(
    onReloadClick: () -> Unit,
) {
    val errorMsg = stringResource(id = R.string.user_brief_loading_error)
    val tryAgainMsg = stringResource(id = R.string.try_again_button_label)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = errorMsg, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier.clickable {
                onReloadClick()
            },
            text = tryAgainMsg,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(widthDp = 400, heightDp = 700, showBackground = true)
@Composable
fun ErrorStatePreview() {
    Box {
        ErrorState {}
    }
}

@Composable
fun BoxScope.LoadedState(model: UserDetailsModel) {
    Column {
        AvatarSection(model = model)
        Spacer(modifier = Modifier.height(height = 12.dp))
        DetailsSection(model = model)
        Spacer(modifier = Modifier.height(height = 8.dp))
        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        RepositoriesSection(model)
    }
}

@Composable
fun AvatarSection(model: UserDetailsModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .semantics {
                    this.testTag = model.avatarUrl
                },
            model = model.avatarUrl,
            contentDescription = "avatar",
        )
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = model.name ?: "",
                    style = MaterialTheme.typography.headlineMedium
                )
                val followingLabel =
                    stringResource(id = R.string.following_label)
                Text(
                    text = "$followingLabel ${model.following}",
                    style = MaterialTheme.typography.bodySmall
                )
                val followersLabel =
                    stringResource(id = R.string.followers_label)
                Text(
                    text = "$followersLabel ${model.followers}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun DetailsSection(model: UserDetailsModel) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        ConstraintLayout(modifier = Modifier.padding(8.dp)) {
            val (websiteLabel,
                websiteVal,
                companyLabel,
                companyVal,
                locationLabel,
                locationVal,
                twitterLabel,
                twitterVal) = createRefs()
            val barrier = createEndBarrier(
                websiteLabel,
                companyLabel,
                locationLabel,
                twitterLabel
            )
            Text(
                modifier = Modifier.constrainAs(websiteLabel) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                text = stringResource(id = R.string.blog_label),
                style = MaterialTheme.typography.bodyMedium
            )
            HyperlinkText(
                modifier = Modifier
                    .constrainAs(websiteVal) {
                        top.linkTo(websiteLabel.top)
                        start.linkTo(barrier)
                    }
                    .padding(start = 8.dp),
                fullText = model.blogUrl ?: "",
                links = if (model.blogUrl.isNullOrEmpty()) emptyList() else
                    listOf(model.blogUrl to getWebLink(model.blogUrl)),
                linksStyle = MaterialTheme.typography.bodyMedium,
                linksColor = Color.Blue
            )
            Text(
                modifier = Modifier.constrainAs(companyLabel) {
                    top.linkTo(websiteVal.bottom)
                    start.linkTo(websiteLabel.start)
                },
                text = stringResource(id = R.string.company_label),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier
                    .constrainAs(companyVal) {
                        top.linkTo(companyLabel.top)
                        start.linkTo(barrier)
                    }
                    .padding(start = 8.dp),
                text = model.company ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier.constrainAs(locationLabel) {
                    top.linkTo(companyVal.bottom)
                    start.linkTo(companyLabel.start)
                },
                text = stringResource(id = R.string.location_label),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier
                    .constrainAs(locationVal) {
                        top.linkTo(locationLabel.top)
                        start.linkTo(barrier)
                    }
                    .padding(start = 8.dp),
                text = model.location ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier.constrainAs(twitterLabel) {
                    top.linkTo(locationVal.bottom)
                    start.linkTo(locationLabel.start)
                },
                text = stringResource(id = R.string.twitter_label),
                style = MaterialTheme.typography.bodyMedium
            )
            HyperlinkText(
                modifier = Modifier
                    .constrainAs(twitterVal) {
                        top.linkTo(twitterLabel.top)
                        start.linkTo(barrier)
                    }
                    .padding(start = 8.dp),
                fullText = model.twitter ?: "",
                links = if (model.twitter.isNullOrEmpty()) emptyList() else
                    listOf(model.twitter to getTwitterLink(model.twitter)),
                linksStyle = MaterialTheme.typography.bodyMedium,
                linksColor = Color.Blue
            )
        }
    }
}

@Composable
fun HyperlinkText(
    modifier: Modifier,
    fullText: String,
    links: List<Pair<String, String>>,
    linksStyle: TextStyle,
    linksColor: Color,
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        val spanStyle = linksStyle.toSpanStyle()
        addStyle(
            style = spanStyle,
            start = 0,
            end = fullText.length
        )
        links.forEach {
            val string = it.first
            val link = it.second
            val startIndex = fullText.indexOf(string)
            val endIndex = startIndex + string.length
            addStyle(
                style = spanStyle.copy(color = linksColor),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "LINK",
                annotation = link,
                start = startIndex,
                end = endIndex
            )
        }
    }

    val uriHandler = LocalUriHandler.current
    ClickableText(
        modifier = modifier,
        text = annotatedString
    ) {
        annotatedString.getStringAnnotations("LINK", it, it)
            .firstOrNull()?.let { annotation ->
                uriHandler.openUri(annotation.item)
            }
    }
}

@Composable
fun RepositoriesSection(model: UserDetailsModel) {
    Row {
        val repositoriesLabel = stringResource(id = R.string.total_repos_label)
        Text(
            text = repositoriesLabel,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = model.repos, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(widthDp = 400, heightDp = 700, showBackground = true)
@Composable
fun LoadedStatePreview() {
    Box(Modifier.padding(16.dp)) {
        LoadedState(
            model = UserDetailsModel(
                "login",
                "avatarUrl",
                "name",
                "company",
                "blogUrl",
                "location",
                "email",
                "twitter",
                "1",
                "2",
                "3"
            )
        )
    }
}