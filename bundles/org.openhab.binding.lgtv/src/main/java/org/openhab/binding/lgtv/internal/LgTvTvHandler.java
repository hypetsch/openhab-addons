/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.lgtv.internal;

import static org.openhab.binding.lgtv.internal.LgTvBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.lgtv.api.LgTvConnection;
import org.openhab.binding.lgtv.api.LgTvKey;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LgTvHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Peter Schraffl - Initial contribution
 */
@NonNullByDefault
public class LgTvTvHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(LgTvTvHandler.class);

    private @Nullable LgTvConnection connection;

    public LgTvTvHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // if (CHANNEL_1.equals(channelUID.getId())) {
        // if (command instanceof RefreshType) {
        // // TODO: handle data refresh
        // }

        if (connection == null) {
            logger.debug("No connection to LG TV available when setting channel '{}'", channelUID);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
            return;
        }

        switch (channelUID.getId()) {
            case CHANNEL_SEND_KEY:
                handleSendKeyCommand(command);
                break;
        }

        // // Note: if communication with thing fails for some reason,
        // // indicate that by setting the status with detail information:
        // // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
        // // "Could not control device at IP address x.x.x.x");
        // }
    }

    private void handleSendKeyCommand(Command command) {
        if (command instanceof RefreshType) {
            // not supported
            return;
        }

        // if (!(command instanceof StringType)) {
        // logger.debug("Channel '{}' can only handle StingType commands", CHANNEL_SEND_COMMAND);
        // return;
        // }

        var lgKey = LgTvKey.getKeyByCommandStr(command.toString());
        if (lgKey == null) {
            logger.debug("No matching LG key found for '{}' when setting channel '{}'", command.toString(),
                    CHANNEL_SEND_KEY);
            return;
        }

        var success = connection.sendKey(lgKey);
        if (!success) {
            logger.debug("Sending key '{}' failed for channel '{}'", lgKey.getThingCommand(), CHANNEL_SEND_KEY);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    String.format("Could not send key {} to TV", lgKey.getThingCommand()));
            return;
        }
    }

    @Override
    public void initialize() {
        LgTvTvConfiguration config = getConfigAs(LgTvTvConfiguration.class);
        if (config == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "No configuration available");
            return;
        }

        // TODO: Initialize the handler.
        // The framework requires you to return from this method quickly. Also, before leaving this method a thing
        // status from one of ONLINE, OFFLINE or UNKNOWN must be set. This might already be the real thing status in
        // case you can decide it directly.
        // In case you can not decide the thing status directly (e.g. for long running connection handshake using WAN
        // access or similar) you should set status UNKNOWN here and then decide the real status asynchronously in the
        // background.

        // set the thing status to UNKNOWN temporarily and let the background task decide for the real status.
        // the framework is then able to reuse the resources from the thing handler initialization.
        // we set this upfront to reliably check status updates in unit tests.
        // updateStatus(ThingStatus.OFFLINE);

        // validate 'remoteHost' configuration
        if (config.hostname == null || config.hostname.isBlank()) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "Parameter 'host' is mandatory and must be configured");
            return;
        }

        if (config.port <= 0) {
            config.port = DEFAULT_PORT;
        }

        updateThingStatus();
        // all checks succeeded, start-up
        scheduler.execute(() -> {
            connection = new LgTvConnection(config);
            connection.connect();
            updateThingStatus();
        });

        // These logging types should be primarily used by bindings
        // logger.trace("Example trace message");
        // logger.debug("Example debug message");
        // logger.warn("Example warn message");

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    private void updateThingStatus() {
        if (connection == null) {
            updateStatus(ThingStatus.UNKNOWN);
            return;
        }

        switch (connection.getStatus()) {
            case WAITING_FOR_PAIRING_KEY:
                updateStatus(ThingStatus.ONLINE, ThingStatusDetail.CONFIGURATION_PENDING,
                        "Device not paired yet. Configure a pairing key.");
                break;

            case PAIRED:
                updateStatus(ThingStatus.ONLINE);
                break;

            default:
                updateStatus(ThingStatus.OFFLINE);
                break;
        }
    }
}
