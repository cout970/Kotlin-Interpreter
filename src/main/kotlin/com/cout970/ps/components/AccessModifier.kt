package com.cout970.ps.components

import com.cout970.ps.tokenizer.TokenType

/**
 * Created by cout970 on 2016/09/22.
 */
enum class AccessModifier(val type: TokenType) {
    PUBLIC(TokenType.ATTRIBUTE_PUBLIC),
    PRIVATE(TokenType.ATTRIBUTE_PRIVATE),
    PROTECTED(TokenType.ATTRIBUTE_PROTECTED),
    INTERNAL(TokenType.ATTRIBUTE_INTERNAL);

    companion object {

        fun getModifier(type: TokenType): AccessModifier {
            return when (type) {
                TokenType.ATTRIBUTE_PUBLIC -> PUBLIC
                TokenType.ATTRIBUTE_PRIVATE -> PRIVATE
                TokenType.ATTRIBUTE_PROTECTED -> PROTECTED
                TokenType.ATTRIBUTE_INTERNAL -> INTERNAL
                else -> throw IllegalStateException("Invalid token type(is not a valid access modifier): $type")
            }
        }

        fun isModifier(type: TokenType): Boolean {
            return type == TokenType.ATTRIBUTE_PUBLIC || type == TokenType.ATTRIBUTE_PRIVATE ||
                    type == TokenType.ATTRIBUTE_PROTECTED || type == TokenType.ATTRIBUTE_INTERNAL
        }
    }
}