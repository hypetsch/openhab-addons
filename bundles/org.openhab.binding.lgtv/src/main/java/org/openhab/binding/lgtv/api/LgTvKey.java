/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
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

/**
 * This enum lists every possible command/variable receiver.
 *
 * @author Peter Schraffl - Initial contribution
 */
public enum LgTvKey {

    POWER("POWER", 1, "POWER"),
    N0("N0", 2, "Number 0"),
    N1("N1", 3, "Number 1"),
    N2("N2", 4, "Number 2"),
    N3("N3", 5, "Number 3"),
    N4("N4", 6, "Number 4"),
    N5("N5", 7, "Number 5"),
    N6("N6", 8, "Number 6"),
    N7("N7", 9, "Number 7"),
    N8("N8", 10, "Number 8"),
    N9("N9", 11, "Number 9"),

    KEY_UP("UP", 12, "UP key among remote Controller’s 4 direction keys"),
    KEY_DOWN("DOWN", 13, "DOWN key among remote Controller’s 4 direction keys"),
    KEY_LEFT("LEFT", 14, "LEFT key among remote Controller’s 4 direction keys"),
    KEY_RIGHT("RIGHT", 15, "RIGHT key among remote Controller’s 4 direction keys"),

    KEY_OK("OK", 20, "OK"),
    KEY_HOME("HOME", 21, "Home menu"),
    KEY_MENU("MENU", 22, "Menu key (same with Home menu key)"),
    KEY_BACK("BACK", 23, "Previous key (Back)"),

    VOLUME_UP("VOLUME_UP", 24, "Volume up"),
    VOLUME_DOWN("VOLUME_DOWN", 25, "Volume down"),
    KEY_MUTE("MUTE", 26, "Mute (toggle)"),

    CHANNEL_UP("CHANNEL_UP", 27, "Channel UP (+)"),
    CHANNEL_DOWN("CHANNEL_DOWN", 28, "Channel DOWN (-)"),
    KEY_BLUE("BLUE", 29, "Blue key of data broadcast"),
    KEY_GREEN("GREEN", 30, "Green key of data broadcast"),
    KEY_RED("RED", 31, "Red key of data broadcast"),
    KEY_YELLOW("YELLOW", 32, "Yellow key of data broadcast"),
    KEY_PLAY("PLAY", 33, "Play"),
    KEY_PAUSE("PAUSE", 34, "Pause"),
    KEY_STOP("STOP", 35, "Stop"),
    KEY_FF("FF", 36, "Fast forward (FF)"),
    KEY_REW("REW", 37, "Rewind (REW)"),
    KEY_SF("SF", 38, "Skip Forward"),
    KEY_SB("SB", 39, "Skip Backward"),

    KEY_RECORD("RECORD", 40, "Record"),
    KEY_RECORDLIST("RECORDLIST", 41, "Recording list"),

    KEY_REPEAT("REPEAT", 42, "Repeat"),
    KEY_LIVETV("LIVETV", 43, "Live TV"),
    KEY_EPG("EPG", 44, "EPG"),
    KEY_CURRENTPROG("CURRENTPROG", 45, "Current program information"),
    KEY_ASPECT("ASPECT", 46, "Aspect ratio"),
    KEY_EXTERNALINPUT("EXTERNALINPUT", 47, "External input"),
    KEY_PIP("PIP", 48, "PIP secondary video"),
    KEY_SUBTITLE("SUBTITLE", 49, "Show / Change subtitle"),
    KEY_PROGRAMLIST("PROGRAMLIST", 50, "Program list"),

    KEY_TELETEXT("TELETEXT", 51, "Tele Text"),
    KEY_MARK("MARK", 52, "Mark"),
    KEY_3DVIDEO("3DVIDEO", 400, "3D Video"),
    KEY_3DLR("3DLR", 401, "3D L/R"),
    KEY_DASH("DASH", 402, "Dash (-)"),
    KEY_PREVCHANNEL("PREVCHANNEL", 403, "Previous channel (Flash back)"),
    KEY_FAVORITE("FAVORITE", 404, "Favorite channel"),
    KEY_QUICKMENU("QUICKMENU", 405, "Quick menu"),
    KEY_TEXTOPTION("TEXTOPTION", 406, "Text Option"),
    KEY_AUDIODESCR("AUDIODESCR", 407, "Audio Description"),
    KEY_NETCAST("NETCAST", 408, "NetCast key (same with Home menu)"),

    KEY_ENGERGYSAVE("ENGERGYSAVE", 409, "Energy saving"),
    KEY_AVMODE("AVMODE", 410, "A/V mode"),
    KEY_SIMPLINK("SIMPLINK", 411, "SIMPLINK"),
    KEY_EXIT("EXIT", 412, "Exit"),
    KEY_RESERVAT("RESERVAT", 413, "Reservation programs list"),
    PIP_CHANNEL_UP("PIP_CHANNEL_UP", 414, "PIP channel UP"),
    PIP_CHANNEL_DOWN("PIP_CHANNEL_DOWN", 415, "PIP channel DOWN"),
    KEY_SWITCHPSEC("SWITCHPSEC", 416, "Switching between primary/secondary video"),
    KEY_MYAPPS("MYAPPS", 417, "My Apps");

    // special commands with Commandinterpreter !=0
    // REQUESTPAIRKEY("REQUESTPAIRKEY", 66, 1, "", "MF: Request Pair Key"),
    // SENDPAIRKEY("SENDPAIRKEY", 67, 2, "", "MF: Send Pair Key"),
    // CHANNEL_SET("CHANNEL_SET", 68, 3, "", "MF: Set Channel"),
    // VOLUME_CURRENT("VOLUME_CURRENT", 69, 4, "LEVEL", "MF: Get Current Volume"),
    // VOLUME_ISMUTED("VOLUME_ISMUTED", 70, 4, "ISMUTED", "MF: Get Is Muted"),
    // CHANNEL_CURRENTNUMBER("CHANNEL_CURRENTNUMBER", 71, 5, "NUMBER", "MF: Get Current Channel Number"),
    // CHANNEL_CURRENTNAME("CHANNEL_CURRENTNAME", 72, 5, "NAME", "MF: Get Current Channel Name"),
    // CHANNEL_CURRENTPROG("CHANNEL_CURRENTPROG", 73, 5, "PROG", "MF: Get Current Program Name"),
    // GET_CHANNELS("GET_CHANNELS", 74, 6, "", "MF: Get all Channels"),
    // GET_APPS("GET_APPS", 75, 7, "", "MF: Get all Apps"),
    // APP_EXECUTE("APP_EXECUTE", 76, 8, "", "MF: Execute Application"),
    // APP_TERMINATE("APP_TERMINATE", 77, 9, "", "MF: Terminate Application"),
    // CONNECTION_STATUS("CONNECTION_STATUS", 78, 10, "", "MF: Get Binding Connection Status"),
    // VOLUME_SET("VOLUME_SET", 79, 11, "", "MF: Set Volume"),
    // BROWSER_URL("BROWSER_URL", 80, 12, "", "MF: Set Browser Url");

    private String thingCommand;
    private int lgVirtualKeyCode;
    private String description;

    private LgTvKey(String thingCommand, int lgVirtualKeyCode, String description) {
        this.thingCommand = thingCommand;
        this.lgVirtualKeyCode = lgVirtualKeyCode;
        this.description = description;
    }

    public String getThingCommand() {
        return thingCommand;
    }

    public int getLgVirtualKeyCode() {
        return lgVirtualKeyCode;
    }

    /**
     * @param command the command to find a matching command name for.
     * @return the commandName that is associated with the passed command.
     */
    public static LgTvKey getKeyByCommandStr(String command) {
        for (LgTvKey candidate : values()) {
            if (candidate.getThingCommand().equals(command)) {
                return candidate;
            }
        }
        return null;
    }
}
