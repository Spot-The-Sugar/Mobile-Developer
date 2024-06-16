package com.example.spotthesugar.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spotthesugar.DI.Injection
import com.example.spotthesugar.data.source.repository.AuthRepository
import com.example.spotthesugar.data.source.repository.ProfileRepository
import com.example.spotthesugar.data.source.repository.TrackRepository
import com.example.spotthesugar.ui.history.TrackViewModel
import com.example.spotthesugar.ui.login.LoginViewModel
import com.example.spotthesugar.ui.profile.ProfileViewModel
import com.example.spotthesugar.ui.signup.RegisterViewModel

class ViewModelFactory(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val trackRepository: TrackRepository
): ViewModelProvider.NewInstanceFactory()  {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(profileRepository) as T
            }
            modelClass.isAssignableFrom(TrackViewModel::class.java) -> {
                TrackViewModel(trackRepository) as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(): ViewModelFactory {
            return INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideRegisterRepository(),
                    Injection.provideProfileRepository(),
                    Injection.provideTrackRepository()
                ).also { INSTANCE = it }
            }
        }
    }
}