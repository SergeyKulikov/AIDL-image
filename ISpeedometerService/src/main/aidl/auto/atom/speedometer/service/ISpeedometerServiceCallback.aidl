// ISpeedometerServiceCallback.aidl
package auto.atom.speedometer.service;

interface ISpeedometerServiceCallback {
    oneway void onSpeedChanged(float speed);
}