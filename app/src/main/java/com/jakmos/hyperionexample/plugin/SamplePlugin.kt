package com.jakmos.hyperionexample.plugin

import com.google.auto.service.AutoService
import com.willowtreeapps.hyperion.plugin.v1.Plugin
import com.willowtreeapps.hyperion.plugin.v1.PluginModule

@AutoService(Plugin::class)
class SamplePlugin : Plugin() {

    override fun createPluginModule(): PluginModule = SamplePluginModule()
}
