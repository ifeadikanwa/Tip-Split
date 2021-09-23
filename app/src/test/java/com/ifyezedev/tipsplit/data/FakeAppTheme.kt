package com.ifyezedev.tipsplit.data

enum class FakeAppTheme (override val mode: Int) : IAppTheme {
    SYSTEM_DEFAULT(0),
    LIGHT(1),
    DARK(2)
}