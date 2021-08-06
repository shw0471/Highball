package com.semicolon.highball.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max
import com.semicolon.highball.remote.TagData
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun HashTag(tags: List<TagData>) {
    SimpleFlowRow(
        verticalGap = 8.dp,
        horizontalGap = 8.dp,
        alignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 0.dp, horizontal = 20.dp)
    ) {
        for (tag in tags) {
            Text(
                text = "#${tag.title}",
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(randomTagColor(), RoundedCornerShape(10.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun SimpleFlowRow(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.Start,
    verticalGap: Dp = 0.dp,
    horizontalGap: Dp = 0.dp,
    content: @Composable () -> Unit
) = Layout(content, modifier) { measurables, constraints ->
    val hGapPx = horizontalGap.roundToPx()
    val vGapPx = verticalGap.roundToPx()

    val rows = mutableListOf<MeasuredRow>()
    val itemConstraints = constraints.copy(minWidth = 0)

    for (measurable in measurables) {
        val lastRow = rows.lastOrNull()
        val placeable = measurable.measure(itemConstraints)

        if (lastRow != null && lastRow.width + hGapPx + placeable.width <= constraints.maxWidth) {
            lastRow.items.add(placeable)
            lastRow.width += hGapPx + placeable.width
            lastRow.height = max(lastRow.height, placeable.height)
        } else {
            val nextRow = MeasuredRow(
                items = mutableListOf(placeable),
                width = placeable.width,
                height = placeable.height
            )

            rows.add(nextRow)
        }
    }

    val width = rows.maxOfOrNull { row -> row.width } ?: 0
    val height = rows.sumBy { row -> row.height } + max(vGapPx.times(rows.size - 1), 0)

    val coercedWidth = width.coerceIn(constraints.minWidth, constraints.maxWidth)
    val coercedHeight = height.coerceIn(constraints.minHeight, constraints.maxHeight)

    layout(coercedWidth, coercedHeight) {
        var y = 0

        for (row in rows) {
            var x = when (alignment) {
                Alignment.Start -> 0
                Alignment.CenterHorizontally -> (coercedWidth - row.width) / 2
                Alignment.End -> coercedWidth - row.width

                else -> throw Exception("unsupported alignment")
            }

            for (item in row.items) {
                item.place(x, y)
                x += item.width + hGapPx
            }

            y += row.height + vGapPx
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HashTagPreview() {
    val hashTag: MutableList<TagData> = ArrayList()
    hashTag.add(TagData("brown"))
    hashTag.add(TagData("spicy"))
    hashTag.add(TagData("salty"))
    hashTag.add(TagData("bitter"))
    hashTag.add(TagData("sugar"))
    hashTag.add(TagData("cocoa"))
    hashTag.add(TagData("mild"))
    hashTag.add(TagData("complex"))
    hashTag.add(TagData("orange"))
    hashTag.add(TagData("fruity"))
    hashTag.add(TagData("citrus"))
    HashTag(tags = hashTag)
}

private fun randomTagColor(): Color {
    val num = Random().nextInt(4)

    when(num) {
        0 -> return Color(0xFFF8EBE2)
        1-> return Color(0xFFF3DAD8)
        2-> return Color(0xFFE2E5F0)
        3-> return Color(0xFFF0F5E5)
        4-> return Color(0xFFE1F1E9)
    }
    return Color(0xFFF8EBE2)
}

private data class MeasuredRow(
    val items: MutableList<Placeable>,
    var width: Int,
    var height: Int
)