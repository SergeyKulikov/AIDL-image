// ISpeedometerServiceCallbackParcel.aidl
package auto.atom.speedometer.service;

import auto.atom.speedometer.service.AtomParcel;
import auto.atom.speedometer.service.AtomImage;
parcelable AtomParcel;
parcelable AtomImage;

interface ISpeedometerServiceCallbackParcel {
    // через callback сервиса мы отправляем данные обратно
    void onParecelBallbackData(inout AtomParcel atomData);
    void onImageBack(inout AtomImage atomImage);
}