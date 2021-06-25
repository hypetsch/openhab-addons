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

/**
 * The {@link LgTvTvConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Peter Schraffl - Initial contribution
 */
public class LgTvTvConfiguration {

    public String pairingKey = "";
    public String hostname = "";
    public Integer port = DEFAULT_PORT;
    public Integer localPort = 0;
}
