package com.example.petoibittlecontrol.scan

enum class BleCommands(val command: String) {
    KSIT("ksit"),
    KREST("krest"),
    M0_30("m0 30"),
    M0_NEG30("m0 -30"),
    KBALANCE("kbalance"),
    KBK("kbk"),
    KWKF("kwkF"),
    KWKL("kwkL"),
    KWKR("kwkR"),
    D("d");

    val byteArray: ByteArray
        get() = command.toByteArray()
}