package com.example.petoibittlecontrol.commands

enum class BleCommands(val command: String) {

    //POSTURA
    KBALANCE("kbalance"),
    BUTTUP("kbuttUp"),
    CALIBRATION("kcalib"),
    DROPPED("kdropped"),
    LIFTED("klifted"),
    LANDING("klnd"),
    REST("krest"),
    SIT("ksit"),
    STRETCH("kstr"),
    UP("kup"), //==balance
    ZERO("kzero"),

    //MERS
    BOUND_FORWARD("kbdF"),
    BACKWARD("kbk"),
    BACKWARD_LEFT("kbkL"),
    CRAWL_FORWARD("kcrF"),
    CRAWL_LEFT("kcrL"),
    GAP_FORWARD("kgpF"),
    GAP_LEFT("kgpL"),
    HALLOWEEN("khlw"),
    JUMP_FORWARD("kjpF"),
    PUSH_FORWARD("kphF"),
    PUSH_LEFT("kphL"),
    TROT_FORWARD("ktrF"),
    TROT_LEFT("ktrL"),
    STEP_ORIGIN("kvtF"),
    SPING_LEFT("kvtL"),
    WALK_FORWARD("kwkF"),
    WALK_LEFT("kwkL"),
    WALK_RIGHT("kwkR"),

    //COMPORTAMENT
    ANGRY("kang"),
    BACKFLIP("kbf"),
    BOXING("kbx"),
    CHEERS("kchr"),
    CHECK("kck"),
    COME_HERE("kcmh"),
    DIG("kdg"),
    FRONT_FLIP("kff"),
    HIGH_FIVE("kfiv"),
    GOOD_BOY("kgdb"),
    HANDSTAND("khds"),
    HUG("khg"),
    HI("khi"),
    HAND_SHAKE("ksk"),
    HANDS_UP("khu"),
    JUMP("kjmp"),
    KICK("kkc"),
    LEAP_OVER("klpov"),
    MOON_WALK("kmw"),
    NOD("knd"),
    PLAY_DEAD("kpd"),
    PEE("kpee"),
    PUSH_UPS("kpu"),
    PUSH_UPS_ONE_HAND("kpu1"),
    RECOVER("krc"),
    ROLL("krl"),
    SCRATCH("kscrh"),
    SNIFF("ksnf"),
    BE_TABLE("ktbl"),
    TEST("kts"),
    WAVE_HEAD("hwh"),
    ALL_JOINT_AT_0("kzz");

    val byteArray: ByteArray
        get() = command.toByteArray()
}