// ISpeedometerService.aidl
package auto.atom.speedometer.service;

import auto.atom.speedometer.service.ISpeedometerServiceCallbackParcel;
import auto.atom.speedometer.service.AtomParcel;
// parcelable AtomParcel;

interface ISpeedometerServiceParcel {
    // void setValue(in AtomParcel incomeData, in ISpeedometerServiceCallbackParcel callback);
    void setValue(in AtomParcel incomeData, in AtomParcel outgoingData);
}