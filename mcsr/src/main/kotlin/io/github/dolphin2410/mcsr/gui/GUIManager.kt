package io.github.dolphin2410.mcsr.gui

import io.github.dolphin2410.mcsr.gui.controllers.BaseController
import io.github.dolphin2410.mcsr.gui.util.center
import io.github.dolphin2410.mcsr.loader.AbstractGUIManager
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle

class GUIManager: AbstractGUIManager {

    lateinit var stage: Stage

    fun start() {
        SceneManager.loadHome()
        initStage()
        center(stage)

        stage.show()
    }

    private fun initStage() {
        stage.initStyle(StageStyle.UNDECORATED)
        stage.width = 800.0
        stage.height = 500.0
        stage.isResizable = false
    }

    fun close() {
        stage.close()
    }

    fun loadScene(scene: Scene, loader: FXMLLoader) {
        stage.scene = scene
        loader.getController<BaseController>().load(loader)
    }
}