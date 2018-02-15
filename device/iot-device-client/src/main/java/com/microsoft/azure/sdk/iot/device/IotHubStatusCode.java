// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.device;

import com.microsoft.azure.sdk.iot.device.exceptions.*;
import com.microsoft.azure.sdk.iot.device.transport.ConnectionStatusExceptions.ConnectionStatusException;
import com.microsoft.azure.sdk.iot.device.transport.ConnectionStatusExceptions.IotHubConnectionStatusException;

/**
 * An IoT Hub status code. Included in a message from an IoT Hub to a device.
 */
public enum IotHubStatusCode
{
    OK,
    OK_EMPTY,
    BAD_FORMAT,
    UNAUTHORIZED,
    TOO_MANY_DEVICES,
    HUB_OR_DEVICE_ID_NOT_FOUND,
    PRECONDITION_FAILED,
    REQUEST_ENTITY_TOO_LARGE,
    THROTTLED,
    INTERNAL_SERVER_ERROR,
    SERVER_BUSY,
    ERROR,
    MESSAGE_EXPIRED,
    MESSAGE_CANCELLED_ONCLOSE;

    public static ConnectionStatusException getConnectionStatusException(IotHubStatusCode statusCode)
    {
        ConnectionStatusException connectionStatusException;
        switch (statusCode)
        {
            case OK:
            case OK_EMPTY:
            case MESSAGE_CANCELLED_ONCLOSE:
            case MESSAGE_EXPIRED:
                connectionStatusException = null;
                break;
            case BAD_FORMAT:
                connectionStatusException = new ConnectionStatusBadFormatException();
                break;
            case UNAUTHORIZED:
                connectionStatusException = new ConnectionStatusUnauthorizedException();
                break;
            case TOO_MANY_DEVICES:
                connectionStatusException = new ConnectionStatusTooManyDevicesException();
                break;
            case HUB_OR_DEVICE_ID_NOT_FOUND:
                connectionStatusException = new ConnectionStatusHubOrDeviceIdNotFoundException();
                break;
            case PRECONDITION_FAILED:
                connectionStatusException = new ConnectionStatusPreconditionFailedException();
                break;
            case REQUEST_ENTITY_TOO_LARGE:
                connectionStatusException = new ConnectionStatusRequestEntityTooLargeException();
                break;
            case THROTTLED:
                connectionStatusException = new ConnectionStatusThrottledException();
                break;
            case INTERNAL_SERVER_ERROR:
                connectionStatusException = new ConnectionStatusInternalServerErrorException();
                break;
            case SERVER_BUSY:
                connectionStatusException = new ConnectionStatusServerBusyException();
                break;
            case ERROR:
                connectionStatusException = new ConnectionStatusUnknownException("Service gave unknown status code: " + statusCode);
                break;
            default:
                connectionStatusException = new IotHubConnectionStatusException("Service gave unknown status code: " + statusCode);
        }

        return connectionStatusException;
    }

    /**
     * Returns the IoT Hub status code referenced by the HTTPS status code.
     *
     * @param httpsStatus the HTTPS status code.
     *
     * @return the corresponding IoT Hub status code.
     *
     * @throws IllegalArgumentException if the HTTPS status code does not map to
     * an IoT Hub status code.
     */
    public static IotHubStatusCode getIotHubStatusCode(int httpsStatus)
    {
        // Codes_SRS_IOTHUBSTATUSCODE_11_001: [The function shall convert the given HTTPS status code to the corresponding IoT Hub status code.]
        IotHubStatusCode iotHubStatus;
        switch (httpsStatus)
        {
            case 200:
                iotHubStatus = OK;
                break;
            case 204:
                iotHubStatus = OK_EMPTY;
                break;
            case 400:
                iotHubStatus = BAD_FORMAT;
                break;
            case 401:
                iotHubStatus = UNAUTHORIZED;
                break;
            case 403:
                iotHubStatus = TOO_MANY_DEVICES;
                break;
            case 404:
                iotHubStatus = HUB_OR_DEVICE_ID_NOT_FOUND;
                break;
            case 412:
                iotHubStatus = PRECONDITION_FAILED;
                break;
            case 413:
                iotHubStatus = REQUEST_ENTITY_TOO_LARGE;
                break;
            case 429:
                iotHubStatus = THROTTLED;
                break;
            case 500:
                iotHubStatus = INTERNAL_SERVER_ERROR;
                break;
            case 503:
                iotHubStatus = SERVER_BUSY;
                break;
            default:
                // Codes_SRS_IOTHUBSTATUSCODE_11_002: [If the given HTTPS status code does not map to an IoT Hub status code, the function return status code ERROR.]
                iotHubStatus = ERROR;
        }

        return iotHubStatus;
    }
}
