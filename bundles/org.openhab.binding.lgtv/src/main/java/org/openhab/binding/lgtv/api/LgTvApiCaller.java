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

import static org.openhab.binding.lgtv.internal.LgTvBindingConstants.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.lgtv.internal.LgTvTvConfiguration;
import org.openhab.core.io.net.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LgTvBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Peter Schraffl - Initial contribution
 */
@NonNullByDefault
public class LgTvApiCaller {

    private final Logger logger = LoggerFactory.getLogger(LgTvApiCaller.class);
    private final @NonNull LgTvTvConfiguration config;

    public LgTvApiCaller(LgTvTvConfiguration config) {
        this.config = config;
    }

    public boolean requestPairingKey() {
        String message = "<?xml version=\"1.0\" encoding=\"utf-8\"?><envelope><api type=\"pairing\">"
                + "<name>showKey</name></api></envelope>";

        return executeApiCall("POST", "/udap/api/pairing", message) != null;
    }

    public boolean sendPairingKey() {
        String message = "<?xml version=\"1.0\" encoding=\"utf-8\"?><envelope><api type=\"pairing\">"
                + "<name>hello</name><value>" + config.pairingKey + "</value><port>" + config.localPort + "</port>" + // todo
                "</api></envelope>";

        return executeApiCall("POST", "/udap/api/pairing", message) != null;
    }

    public boolean sendKey(LgTvKey command) {
        String message = "<?xml version=\"1.0\" encoding=\"utf-8\"?><envelope><api type=\"command\"><name>HandleKeyInput</name>"
                + "<value>" + command.getLgVirtualKeyCode() + "</value></api></envelope>";

        return executeApiCall("POST", "/udap/api/command", message) != null;
    }

    /**
     * @param httpMethod to execute
     * @param apiPath to request
     * @param message to add to request
     * @return the response
     */
    private @Nullable String executeApiCall(String httpMethod, String apiPath, String message) {
        try {
            String url = createApiBaseUrl() + apiPath;
            logger.trace("api request url = '{}'", url);

            InputStream contentStream = null;
            String contentType = null;
            if (message != null && !message.isBlank()) {
                logger.trace("api request content: '{}'", message);
                if (!message.isBlank()) {
                    contentStream = new ByteArrayInputStream(message.getBytes(Charset.forName("UTF-8")));
                    contentType = "text/xml; charset=utf-8";
                }
            }

            // a Controller must include UDAP/2.0 in the UserAgent header of POST
            var httpHeaders = new java.util.Properties();
            httpHeaders.setProperty("User-Agent", "openHAB LG-TV Binding - UDAP/2.0");

            String response = HttpUtil.executeUrl(httpMethod, url, httpHeaders, contentStream, contentType,
                    API_TIMEOUT);
            logger.trace("api response content: '{}'", response == null ? "null" : response);
            return response;
        } catch (IOException | IllegalStateException e) {
            logger.debug("Error executing api request: {}", e.getMessage());
            return null;
        }
    }

    private String createApiBaseUrl() {
        final String remoteHost = config.hostname == null ? "" : config.hostname.trim();

        StringBuilder url = new StringBuilder();
        url.append("http://");
        url.append(remoteHost);
        if (config.port != 80) {
            url.append(":").append(config.port);
        }
        return url.toString();
    }
}
