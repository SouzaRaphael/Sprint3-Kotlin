package br.com.lactarehub.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppTextStyles

/**
 * Campo de formulário com rótulo acima, como em todos os formulários do
 * design.
 *
 * A validação segue o modelo do Flutter: a mensagem de [errorMessage] é
 * calculada pela tela e desce pronta para exibição.
 */
@Composable
fun AppTextField(
    label: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    obscureText: Boolean = false,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    errorMessage: String? = null,
    /** Ação alinhada à direita do rótulo, como "Esqueceu a senha?". */
    trailingLabel: String? = null,
    onTrailingLabelClick: (() -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = label, style = AppTextStyles.label, modifier = Modifier.weight(1f))
            if (trailingLabel != null) {
                Text(
                    text = trailingLabel,
                    style = AppTextStyles.badge.copy(color = AppColors.Primary),
                    modifier = Modifier.clickable(enabled = onTrailingLabelClick != null) {
                        onTrailingLabelClick?.invoke()
                    },
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            singleLine = maxLines == 1,
            maxLines = if (obscureText) 1 else maxLines,
            isError = errorMessage != null,
            placeholder = { Text(hint, style = AppTextStyles.body.copy(color = AppColors.Hint)) },
            textStyle = AppTextStyles.body.copy(color = AppColors.Ink),
            visualTransformation = if (obscureText) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                capitalization = capitalization,
            ),
            trailingIcon = suffix,
            shape = AppRadius.input,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = AppColors.Surface,
                unfocusedContainerColor = AppColors.Surface,
                errorContainerColor = AppColors.Surface,
                focusedBorderColor = AppColors.Accent,
                unfocusedBorderColor = AppColors.BorderInput,
                errorBorderColor = AppColors.Error,
                cursorColor = AppColors.Primary,
                focusedTextColor = AppColors.Ink,
                unfocusedTextColor = AppColors.Ink,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = AppTextStyles.caption.copy(color = AppColors.Error),
                modifier = Modifier.padding(start = 4.dp, top = 6.dp),
            )
        }
    }
}
