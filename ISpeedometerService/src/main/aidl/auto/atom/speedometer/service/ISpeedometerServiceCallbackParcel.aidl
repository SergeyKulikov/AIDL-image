// ISpeedometerServiceCallbackParcel.aidl
package auto.atom.speedometer.service;

import auto.atom.speedometer.service.AtomParcel;
parcelable AtomParcel;

interface ISpeedometerServiceCallbackParcel {
    // через callback сервиса мы отправляем данные обратно
    void onParecelBallbackData(out AtomParcel atomData);
}