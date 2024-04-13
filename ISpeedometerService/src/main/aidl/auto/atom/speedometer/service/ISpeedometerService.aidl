// ISpeedometerService.aidl
package auto.atom.speedometer.service;

import auto.atom.speedometer.service.ISpeedometerServiceCallback;

interface ISpeedometerService {
    void setCallback(in ISpeedometerServiceCallback callback);
}