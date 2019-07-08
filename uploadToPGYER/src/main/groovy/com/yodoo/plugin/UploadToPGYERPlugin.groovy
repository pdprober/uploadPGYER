package com.yodoo.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.annotations.NotNull

/**
 * @author zhang
 */
public class UploadToPGYERPlugin implements Plugin<Project> {
    public static final String sPluginExtensionName = "UploadToPGYER"

    @Override
    void apply(@NotNull Project project) {
        project.extensions.create(sPluginExtensionName, UploadToPGYERExtension)
        project.afterEvaluate {
            AppExtension appExtension = project.extensions.findByType(AppExtension)
            UploadToPGYERExtension uploadToPGYERExtension = project.extensions.findByType(UploadToPGYERExtension)
            appExtension.applicationVariants.all { BaseVariant variant ->
                def buildType = uploadToPGYERExtension.uploadBuildType
                if (buildType == null || buildType.length() == 0) {
                    throw new GradleException('uploadBuildType is empty,please deploy uploadBuildType')
                }
                def pgyerApiKey = uploadToPGYERExtension.pgyerApiKey
                if (pgyerApiKey == null || pgyerApiKey.length() == 0) {
                    throw new GradleException('pgyerApiKey is empty,please deploy pgyerApiKey')
                }
                if (buildType == variant.buildType.name) {
                    String taskName = "upload${variant.buildType.name}ToPGYER";
                    if (project.tasks.findByName(taskName) == null) {
                        UploadToPGYERTask uploadToPGYERTask = project.tasks.create(taskName, UploadToPGYERTask, variant, project)
                    }
                }
            }
        }
    }
}