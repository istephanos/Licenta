package com.example.petoibittlecontrol

enum class BleCommands(val command: String) {
    KREST("krest"),
    KBK("kbk"),
    KWKF("kwkF"),
    KWKL("kwkL"),
    KWKR("kwkR"),
    KSIT("ksit"),
    M0_30("m0 30"),
    M0_NEG30("m0 -30"),
    KBALANCE("kbalance"),
    D("d");

    val byteArray: ByteArray
        get() = command.toByteArray()
}