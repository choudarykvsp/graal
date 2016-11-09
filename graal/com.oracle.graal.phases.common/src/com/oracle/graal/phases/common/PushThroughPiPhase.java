/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.graal.phases.common;

import com.oracle.graal.debug.Debug;
import com.oracle.graal.debug.DebugCounter;
import com.oracle.graal.graph.Node;
import com.oracle.graal.nodes.PiNode;
import com.oracle.graal.nodes.StructuredGraph;
import com.oracle.graal.nodes.spi.PiPushable;
import com.oracle.graal.phases.Phase;

public class PushThroughPiPhase extends Phase {

    public static final DebugCounter PUSHED_NODES = Debug.counter("NodesPushedThroughPi");

    @Override
    protected void run(StructuredGraph graph) {
        for (PiNode pi : graph.getNodes(PiNode.TYPE)) {
            for (Node n : pi.usages().snapshot()) {
                if (n instanceof PiPushable) {
                    PiPushable pip = (PiPushable) n;
                    if (pip.push(pi)) {
                        PUSHED_NODES.add(1);
                    }
                }
            }
        }
    }
}