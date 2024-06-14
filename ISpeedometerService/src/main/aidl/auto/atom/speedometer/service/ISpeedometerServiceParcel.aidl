// ISpeedometerService.aidl
package auto.atom.speedometer.service;

import auto.atom.speedometer.service.ISpeedometerServiceCallbackParcel;

interface ISpeedometerServiceParcel {
    void setCallback(in ISpeedometerServiceCallbackParcel callback);
}