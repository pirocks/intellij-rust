/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.cargo.runconfig.filters

import org.rust.cargo.RsWithToolchainTestBase
import org.rust.cargo.project.model.cargoProjects
import org.rust.cargo.runconfig.filters.HighlightFilterTestBase.Companion.checkHighlights
import org.rust.fileTree
import org.rust.singleWorkspace

/**
 * Cargo tests for [RsBacktraceFilter]
 */
class RsBacktraceFilterCargoTest : RsWithToolchainTestBase() {
    private val filter: RsBacktraceFilter
        get() = RsBacktraceFilter(project, cargoProjectDirectory, project.cargoProjects.singleWorkspace())

    fun `test resolve cargo crate`() {
        fileTree {
            toml("Cargo.toml", """
                [package]
                name = "hello"
                version = "0.1.0"
                authors = []

                [dependencies]
                left-pad = "=1.0.1"
            """)

            dir("src") {
                rust("lib.rs", "")
            }
        }.create()

        checkHighlights(
            filter,
            " at /cargo/registry/src/github.com-1ecc6299db9ec823/left-pad-1.0.1/src/lib.rs:21",
            " at [/cargo/registry/src/github.com-1ecc6299db9ec823/left-pad-1.0.1/src/lib.rs:21 -> lib.rs]"
        )
    }
}
