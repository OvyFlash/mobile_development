package ua.kpi.comsys.io8130.FourthFragment.Models

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

class Flower(
    var largeImageURL: String = "",
    var photoUri: Uri? = null
) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readParcelable(Uri::class.java.classLoader)
    ) {
    }

    public fun ByURI(): Boolean {
        if (photoUri != null) {
            return false
        }
        return true
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(largeImageURL)
        parcel.writeParcelable(photoUri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Flower> {
        override fun createFromParcel(parcel: Parcel): Flower {
            return Flower(parcel)
        }

        override fun newArray(size: Int): Array<Flower?> {
            return arrayOfNulls(size)
        }
    }
}