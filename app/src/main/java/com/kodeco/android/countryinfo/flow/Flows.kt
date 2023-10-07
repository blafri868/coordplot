package com.kodeco.android.countryinfo.flow

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object Flows {
    private val _tapFlow = MutableStateFlow(0)
    val tapFlow = _tapFlow.asStateFlow()
    fun tap() {
        _tapFlow.update { tap -> tap + 1 }
    }

    private val _backFlow = MutableStateFlow(0)
    val backFlow = _backFlow.asStateFlow()
    fun tapBack() {
        _backFlow.update { back -> back + 1 }
    }

    private val _counterFlow = MutableStateFlow(0)
    val counterFlow = _counterFlow.asStateFlow()
    init {
        GlobalScope.launch {
            while (true) {
                delay(1_000L)
                _counterFlow.update { counter -> counter + 1 }
            }
        }
    }
}
