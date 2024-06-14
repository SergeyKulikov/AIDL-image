//package auto.atom.speedometer.service
package auto.atom.speedometer.service

import android.os.Parcel
import android.os.Parcelable

class AtomParcel() : Parcelable {
    var data: String = "default text"

    constructor(parcel: Parcel) : this() {
        readFromParcel(parcel)
    }

    fun readFromParcel(parcel: Parcel) {
        data = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(data)
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
