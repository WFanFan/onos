/*
 * Copyright 2016-present Open tworking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.drivers.fujitsu.cli;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.net.DeviceId;
import org.onosproject.drivers.fujitsu.behaviour.VoltPonLinkConfig;
import org.onosproject.net.driver.DriverHandler;
import org.onosproject.net.driver.DriverService;

/**
 * Gets PON links in vOLT.
 */
@Command(scope = "onos", name = "volt-ponlinks",
        description = "Gets PON links in vOLT")
public class VoltGetPonLinksCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "uri", description = "Device ID",
            required = true, multiValued = false)
    String uri = null;

    @Argument(index = 1, name = "target", description = "PON link ID",
            required = false, multiValued = false)
    String target = null;

    private DeviceId deviceId;

    @Override
    protected void execute() {
        DriverService service = get(DriverService.class);
        deviceId = DeviceId.deviceId(uri);
        DriverHandler h = service.createHandler(deviceId);
        VoltPonLinkConfig volt = h.behaviour(VoltPonLinkConfig.class);
        String reply = volt.getPonLinks(target);
        if (reply != null) {
            print("%s", reply);
        } else {
            print("No reply from %s", deviceId.toString());
        }
    }

}
