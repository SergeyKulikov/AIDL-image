// ISpeedometerService.aidl
package auto.atom.speedometer.service;

import auto.atom.speedometer.service.ISpeedometerServiceCallbackParcel;
import auto.atom.speedometer.service.AtomParcel;
// parcelable AtomParcel;

interface ISpeedometerServiceParcel {
    // регистрируем callback из Application. Если он не зарегистрирован,
    // то обратно данные отправить будет нельзя.
    void setCallback(ISpeedometerServiceCallbackParcel callback);

     // после регистрации можно отсылать данные из Application
    void setValue(in AtomParcel incomeData);
}