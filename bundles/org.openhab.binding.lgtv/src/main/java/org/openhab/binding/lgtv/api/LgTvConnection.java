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
package org.openhab.binding.lgtv.api;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.lgtv.internal.LgTvTvConfiguration;

/**
 * The {@link LgTvBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Peter Schraffl - Initial contribution
 */
@NonNullByDefault
public class LgTvConnection {

    public enum Status {
        UNKNOWN,
        NOT_PAIRED,
        PAIRED,
        WAITING_FOR_PAIRING_KEY
    };

    private final @NonNull LgTvApiCaller apiCaller;
    private final LgTvTvConfiguration config;
    private Status status = Status.UNKNOWN;

    public LgTvConnection(LgTvTvConfiguration config) {
        this.config = config;
        this.apiCaller = new LgTvApiCaller(config);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void connect() {
        // try {
        // if (reader != null) {
        // reader.startserver();
        // }
        // } catch (IOException e) {
        // logger.error("Error at openconnection", e);
        // }

        if (config.pairingKey == null || config.pairingKey.isBlank()) {
            boolean requested = apiCaller.requestPairingKey();
            setStatus(requested ? Status.WAITING_FOR_PAIRING_KEY : Status.NOT_PAIRED);
            return;
        }

        // if we have a pairing key, send it
        boolean paired = apiCaller.sendPairingKey();
        setStatus(paired ? Status.PAIRED : Status.NOT_PAIRED);
    }

    public boolean sendKey(LgTvKey key) {
        return apiCaller.sendKey(key);
    }
}
