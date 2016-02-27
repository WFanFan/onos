/*
 * Copyright 2016 Open Networking Laboratory
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
package org.onosproject.cli.net;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.cluster.NodeId;
import org.onosproject.net.region.Region;
import org.onosproject.net.region.RegionAdminService;
import org.onosproject.net.region.RegionId;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Add a new region.
 */
@Command(scope = "onos", name = "region-add",
        description = "Adds a new region.")
public class RegionAddCommand extends AbstractShellCommand {

    private static final BiMap<String, Region.Type> REGION_TYPE_MAP = HashBiMap.create();

    static {
        for (Region.Type t : Region.Type.values()) {
            REGION_TYPE_MAP.put(t.name(), t);
        }
    }

    @Argument(index = 0, name = "id", description = "Region ID",
            required = true, multiValued = false)
    String id = null;

    @Argument(index = 1, name = "name", description = "Region Name",
            required = true, multiValued = false)
    String name = null;

    @Argument(index = 2, name = "type", description = "Region Type",
            required = true, multiValued = false)
    String type = null;

    @Argument(index = 3, name = "masters", description = "Region Master",
            required = true, multiValued = true)
    List<String> masters = null;

    @Override
    protected void execute() {
        RegionAdminService service = get(RegionAdminService.class);
        RegionId regionId = RegionId.regionId(id);
        Set<NodeId> nodeIds =
                masters.stream().map(s -> NodeId.nodeId(s)).collect(Collectors.toSet());
        List<Set<NodeId>> masters = Lists.newArrayList();
        masters.add(nodeIds);
        service.createRegion(regionId, name, REGION_TYPE_MAP.get(type), masters);
        print("Region successfully added.");
    }
}