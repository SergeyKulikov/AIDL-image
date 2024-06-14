//package auto.atom.speedometer.service
package auto.atom.speedometer.service

import android.os.Parcel
import android.os.Parcelable

class AtomParcel() : Parcelable {
    var data: Float = 0f

    constructor(parcel: Parcel) : this() {
        readFromParcel(parcel)
    }

    fun readFromParcel(parcel: Parcel) {
        data = parcel.readFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AtomParcel> {
        override fun createFromParcel(parcel: Parcel): AtomParcel {
            return AtomParcel(parcel)
        }

        override fun newArray(size: Int): Array<AtomParcel?> {
            return arrayOfNulls(size)
        }
    }
}
