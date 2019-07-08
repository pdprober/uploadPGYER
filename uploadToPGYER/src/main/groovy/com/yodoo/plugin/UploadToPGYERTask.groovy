package com.yodoo.plugin

import com.android.build.gradle.api.BaseVariant
import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject

public class UploadToPGYERTask extends DefaultTask {
    @Input
    BaseVariant variant
    @Input
    Project project

    void setup() {
        description "Upload release apk to PGYER"
        group "UploadToPGYER"
        dependsOn variant.assemble
    }

    @Inject
    UploadToPGYERTask(BaseVariant variant, Project project) {
        this.variant = variant
        this.project = project
        setup()
    }

    @TaskAction
    void uploadToPGYERTask() {
        variant.outputs.each { out ->
            def outputFile = out.outputFile
            if (outputFile != null && outputFile.exists()) {
                logger.log(LogLevel.ERROR, "start up load apk file : ${outputFile.absolutePath}")
                def stdout = new ByteArrayOutputStream()
                UploadToPGYERExtension uploadToPGYERExtensionConfig = project.extensions.findByType(UploadToPGYERExtension)
                project.exec {
                    executable = 'curl'
                    args = ['-F', "file=@${outputFile.absolutePath}",
                            '-F', "_api_key=${uploadToPGYERExtensionConfig.pgyerApiKey}",
                            '-F', "buildInstallType=${uploadToPGYERExtensionConfig.buildInstallType}",
                            '-F', "buildPassword=${uploadToPGYERExtensionConfig.buildPassword}",
                            '-F', "buildUpdateDescription=${uploadToPGYERExtensionConfig.buildUpdateDescription}",
                            '-F', "buildName=${uploadToPGYERExtensionConfig.buildName}",
                            '-F', "buildInstallQuestoin=${uploadToPGYERExtensionConfig.buildInstallQuestoin}",
                            '-F', "buildInstallAnswer=${uploadToPGYERExtensionConfig.buildInstallAnswer}",
                            '-F', "buildInstallDate=${uploadToPGYERExtensionConfig.buildInstallDate}",
                            '-F', "buildInstallStartDate=${uploadToPGYERExtensionConfig.buildInstallStartDate}",
                            '-F', "buildInstallEndDate=${uploadToPGYERExtensionConfig.buildInstallEndDate}",
                            "https://www.pgyer.com/apiv2/app/upload"]
                    standardOutput = stdout
                }
                String output = stdout.toString()
                logger.log(LogLevel.ERROR, "upload result :$output")
                def parsedJson = new JsonSlurper().parseText(output)
                if (parsedJson.code == 0 && uploadToPGYERExtensionConfig.isOpenWeb) {
                    project.exec {
                        executable = 'cmd'
                        args = ['/c', "start", "https://www.pgyer.com/${parsedJson.data.buildShortcutUrl}"]
                    }
                }
            }
        }
    }
}