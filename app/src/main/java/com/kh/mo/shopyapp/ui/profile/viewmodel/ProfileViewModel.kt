package com.kh.mo.shopyapp.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG ProfileViewModel"
    private val _userData: MutableStateFlow<UserData?> =
        MutableStateFlow(UserData(7588056039708, "Moaaz AbdEl-salam", "moaaz_1@gmail.com"))
    val userData: StateFlow<UserData?>
        get() = _userData

}