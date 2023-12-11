package br.edu.ifsp.scl.tasks.domain.utils

sealed class MBResult<out S, out E> {
    data class Success<out S>(val data: S) : MBResult<S, Nothing>()
    data class Error<out E>(val failure: E) : MBResult<Nothing, E>()
}

inline fun <S, E, R> MBResult<S, E>.flow(
    success: (S) -> R,
    error: (E) -> R
): R {
    return when (this) {
        is MBResult.Success -> success(data)
        is MBResult.Error -> error(failure)
    }
}