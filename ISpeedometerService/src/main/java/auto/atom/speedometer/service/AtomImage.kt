package auto.atom.speedometer.service

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable


class AtomImage() : Parcelable {
    private var byteArr: ByteArray = ByteArray(0)

    constructor(parcel: Parcel) : this() {
        readFromParcel(parcel)
    }

    fun readFromParcel(parcel: Parcel) {
        // ALog.i(this@AtomImage.javaClass, "AImage(readFromParcel)", parcel.toString())
        try {

            val size = parcel.readInt()
            byteArr = ByteArray(size)
            if (size > 0) {
                parcel.readByteArray(byteArr)
                val end = parcel.readInt()
                if (end != 199) {
                    // ALog.i(this.javaClass, "readFromParcel(199)", "Неверно считан массив ByteArray!")
                }
            }
            /*
            parcel.createByteArray(size)?.apply {
                byteArr = this
            }
            */
        } catch (ex: Exception) {
            // ALog.e(ex, true)
            // ALog.d(
            //    this.javaClass, "readFromParcel()",
            //    "--- END Exception 3 ---"
            //)
        }
    }

    constructor(bitmap: Bitmap?, divider: Int = 1) : this() {
        // Сразу сжимаем изображение

        if (bitmap == null) {
            byteArr = ByteArray(0)
            return
        }

        val newWidth = bitmap.width / divider
        val newHeight = bitmap.height / divider

        val smallBitmap = Bitmap.createScaledBitmap(bitmap,
            newWidth,
            newHeight,
            false
        ) //or RGB_565 if you prefer.
        // todo: оиграться форматами

        byteArr = BitmapUtils.convertBitmapToByteArray(smallBitmap).clone()

        // ALog.i(this@AtomImage.javaClass, "AImage()", "$newWidth; $newHeight; size -> ${bitmap.byteCount}/${byteArr.size}")
    }


    fun getBitmap(): Bitmap? {
        if (byteArr.isNotEmpty()) {
            return BitmapUtils.convertByteArrayToBitmap(byteArr)
        }
        return null
    }

    fun getByteArray(): ByteArray {
        return byteArr.clone()
    }

    fun setByteArray(inByteArray: ByteArray) {
         byteArr = inByteArray.clone()
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //ALog.i(this@AtomImage.javaClass, "(${byteArr.size}): AImage(writeToParcel)", parcel.toString())
        try {
            // byteArr.size
            val size = byteArr.size

            parcel.writeInt(size)
            if (size > 0) {
                parcel.writeByteArray(getByteArray(), 0, size)
                // parcel.writeList(byteArr.toMutableList())
                parcel.writeInt(199)
            }
        } catch (ex: Exception) {
            //ALog.e(ex, true)
            //ALog.d(
            //    this.javaClass, "readFromParcel()",
            //    "--- END Exception 4 ---"
            //)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AtomImage?> {
        override fun createFromParcel(parcel: Parcel): AtomImage? {
            return AtomImage(parcel)
        }

        override fun newArray(size: Int): Array<AtomImage?> {
            return arrayOfNulls(size)
        }
    }

}