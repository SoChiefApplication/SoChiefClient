package fr.vlegall.sochief.client.components.ui

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.EyeOff
import com.composables.icons.lucide.Lucide

@Composable
fun PasswordOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,

    modifier: Modifier = Modifier,

    label: (@Composable (() -> Unit))? = null,
    placeholder: (@Composable (() -> Unit))? = null,
    leadingIcon: (@Composable (() -> Unit))? = null,

    trailingIconExtra: (@Composable (() -> Unit))? = null,

    supportingText: (@Composable (() -> Unit))? = null,

    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,

    textStyle: TextStyle = TextStyle.Default,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),

    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions.Default,

    passwordVisible: Boolean? = null,
    onPasswordVisibleChange: ((Boolean) -> Unit)? = null,
) {
    var internalVisible by rememberSaveable { mutableStateOf(false) }
    val visible = passwordVisible ?: internalVisible
    var isFocused by remember { mutableStateOf(false) }

    fun setVisible(v: Boolean) {
        if (passwordVisible != null) {
            onPasswordVisibleChange?.invoke(v)
        } else {
            internalVisible = v
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.onFocusChanged {
            isFocused = it.isFocused
        },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (isFocused) {
                IconButton(onClick = { setVisible(!visible) }) {
                    Icon(
                        imageVector = if (visible) Lucide.EyeOff else Lucide.Eye,
                        contentDescription = if (visible) "Masquer" else "Afficher"
                    )
                }
            }
            trailingIconExtra?.invoke()
        },
        supportingText = supportingText,
        isError = isError,
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else maxLines,
        minLines = minLines,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = shape,
        colors = colors
    )
}