/*
 * Copyright (c) 2018, 2020, Oracle and/or its affiliates. All rights reserved.
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

/*
 * @test
 * @bug 8062950
 * @requires vm.flavor == "server"
 * @library /test/lib
 * @run driver compiler.c2.Test8062950
 */

import jdk.test.lib.process.OutputAnalyzer;
import static jdk.test.lib.process.ProcessTools.createTestJavaProcessBuilder;
import static jdk.test.lib.process.ProcessTools.executeProcess;

import jdk.test.lib.process.ProcessTools;

public class Test8062950 {
    private static final String CLASSNAME = "DoesNotExist";
    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            ProcessBuilder pb = createTestJavaProcessBuilder("ShutdownHook");
            OutputAnalyzer output = executeProcess(pb);
            output.shouldContain("Shutdown Hook");
            output.shouldHaveExitValue(0);
            return;
        }

        Thread thread = Thread.ofVirtual().unstarted(()-> {
            System.out.println("Shutdown Hook");
            System.out.flush();
        });
        Runtime.getRuntime().addShutdownHook(thread);
        System.exit(0);
    }
}
