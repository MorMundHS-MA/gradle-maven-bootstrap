package local.mormund.buildGenMaven;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.maven.model.Model;
import org.apache.maven.model.Build;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import lombok.val;

public class Cli {
  private static final Path workingDir = Path.of(".");

  public static void main(String[] args) throws IOException, InterruptedException {
    val modulePoms = new HashMap<Path, SubProject>();
    val projects = SettingsParser.parseSourceLines()
        .entrySet()
        .stream()
        .map(entry -> {
          val pomModel = new Model();
          val subProject = new SubProject(entry.getKey(), entry.getValue(), pomModel);
          val parentDir = subProject.getDirectoryPath().getParent();
          val parentName = parentDir.getFileName().toString();
          pomModel.setArtifactId("gradle-"+entry.getKey());
          pomModel.setModelVersion("4.0.0");

          val parent = new Parent();
          parent.setGroupId("org.gradle");
          parent.setArtifactId("gradle-"+parentName);
          parent.setVersion("8.11");

          pomModel.setParent(parent);
          if (modulePoms.containsKey(parentDir)) {
            modulePoms.get(parentDir).getPom().addModule(subProject.getName());
          } else {
            val parentPomModel = new Model();
            parentPomModel.setModelVersion("4.0.0");
            parentPomModel.setPackaging("pom");
            parentPomModel.setArtifactId("gradle-"+parentName);
            val parent2 = new Parent();
            parent2.setGroupId("org.gradle");
            parent2.setArtifactId("gradle-platforms");
            parent2.setVersion("8.11");
  
            parentPomModel.setParent(parent2);
            parentPomModel.addModule(subProject.getName());
            val parentProject = new SubProject(parentName,
                parentDir.toString(), parentPomModel);
            modulePoms.put(parentDir, parentProject);
          }

          if(Cli.isKotlinProject(subProject)) {
            Cli.addKotlinSettings(pomModel);
          }

          return new AbstractMap.SimpleEntry<String, SubProject>(entry.getKey(), subProject);
        })
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue));

    val commands = new ArrayList<String>();
    commands.add("./gradlew");
    for (String project : projects.keySet()) {
      commands.add(project + ":dependencies");
      commands.add("--configuration");
      commands.add("compileClasspath");
    }

    val gradleProcessBuilder = new ProcessBuilder().command(commands);
    val gradleProcess = gradleProcessBuilder.start();

    GradleDependencyParser.parse(new BufferedReader(new InputStreamReader(gradleProcess.getInputStream())), projects);
    gradleProcess.getErrorStream().transferTo(System.err);
    gradleProcess.waitFor();
    if (gradleProcess.exitValue() != 0) {
      throw new RuntimeException("Gradle build failed.");
    }

    val writer = new MavenXpp3Writer();
    for (SubProject subProject : projects.values()) {
      val file = subProject.getPomPath().toFile();
      file.createNewFile();
      val outStream = new FileOutputStream(file, false);
      writer.write(outStream, subProject.getPom());
    }

    for (SubProject subProject : modulePoms.values()) {
      System.out.println("Module pom for " + subProject.getPath());
      val file = subProject.getPomPath().toFile();
      file.createNewFile();
      val outStream = new FileOutputStream(file, false);
      writer.write(outStream, subProject.getPom());
    }
  }

  private static boolean isKotlinProject(SubProject project) {
    val kotlinDir = project.getDirectoryPath().resolve("src/main/kotlin").toFile();
    return kotlinDir.exists() && kotlinDir.isDirectory();
  }

  private static void addKotlinSettings(Model pom) {
    pom.addProperty("kotlin.version", "2.0.20");
    val build = new Build();    
    build.setSourceDirectory("${project.basedir}/src/main/kotlin");
    build.setTestSourceDirectory("${project.basedir}/src/test/kotlin");
    val plugin = new Plugin();
    plugin.setGroupId("org.jetbrains.kotlin");
    plugin.setArtifactId("kotlin-maven-plugin");
    plugin.setVersion("${kotlin.version}");
    val execution = new PluginExecution();
    execution.setId("compile");
    execution.addGoal("compile");
    plugin.addExecution(execution);
    build.addPlugin(plugin);
    pom.setBuild(build);
  }
}
