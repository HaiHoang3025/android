package com.coming.app.fitmax.data

enum class BodyPart(val position: Int) {
    NOSE(0),
    LEFT_EYE(1),
    RIGHT_EYE(2),
    LEFT_EAR(3),
    RIGHT_EAR(4),
    LEFT_SHOULDER(5), //vai
    RIGHT_SHOULDER(6),
    LEFT_ELBOW(7),
    RIGHT_ELBOW(8),//khuyu tay
    LEFT_WRIST(9), //co tay
    RIGHT_WRIST(10),
    LEFT_HIP(11),//hong
    RIGHT_HIP(12),
    LEFT_KNEE(13),//dau goi
    RIGHT_KNEE(14),
    LEFT_ANKLE(15),//Chân
    RIGHT_ANKLE(16);
    companion object{
        private val map = values().associateBy(BodyPart::position)
        fun fromInt(position: Int): BodyPart = map.getValue(position)
    }
}
