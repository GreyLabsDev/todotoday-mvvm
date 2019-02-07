package com.greylabs.todotoday.base

sealed class ProgressState {
    class Initial: ProgressState()
    class Done: ProgressState()
    class Loading: ProgressState()
    class Error: ProgressState()
    class Finish: ProgressState()
}