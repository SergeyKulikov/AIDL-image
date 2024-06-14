// ISpeedometerServiceCallbackParcel.aidl
package auto.atom.speedometer.service;

import auto.atom.speedometer.service.AtomParcel;
parcelable AtomParcel;

interface ISpeedometerServiceCallbackParcel {
    void onParecelBallbackData(out AtomParcel atomData);
}