package com.example.petoibittlecontrol.scan

enum class BleCommands(val command: String) {
    KSIT("ksit"),
    M0_30("m0 30"),
    M0_NEG30("m0 -30"),
    KBALANCE("kbalance"),
    KBK("kbk"),
    KWKF("kwkF"),
    KTRL("ktrL"),
    D("d");

    val byteArray: ByteArray
        get() = command.toByteArray()
}