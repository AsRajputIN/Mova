package com.muratozturk.metflix.domain.use_case.authentication

import com.google.firebase.auth.AuthCredential
import com.muratozturk.metflix.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithCredentialUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(credential: AuthCredential) = repository.signInWithCredential(credential)
}