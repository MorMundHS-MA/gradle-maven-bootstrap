package local.mormund.buildGenMaven;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.maven.model.Dependency;

import lombok.val;

public class GradleDependencyParser {
  private static final Pattern projectPattern = Pattern.compile("^Project ':([\\w\\-]+)'");
  private static final Pattern projectJarDependencyPattern = Pattern.compile("^\\+--- project :([\\w\\-]+)");
  private static final Pattern projectPomDependencyPattern = Pattern.compile("^\\\\--- project :([\\w\\-]+)");

  public static void parse(BufferedReader gradleOutput, Map<String, SubProject> subProjects) throws IOException {
    String currentLine = null;
    SubProject currentProject = null;
    while((currentLine = gradleOutput.readLine()) != null) {
      if(currentLine.startsWith("> ")) {
        System.out.println(currentLine);
        continue;
      }

      val projectMatcher = projectPattern.matcher(currentLine);
      if(projectMatcher.find()) {
        val projectName = projectMatcher.group(1);
        currentProject = subProjects.get(projectName);
        System.out.println(MessageFormat.format("Getting dependencies for project {0}", projectName));
      }

      val dependencyJarMatcher = projectJarDependencyPattern.matcher(currentLine);
      if(dependencyJarMatcher.find()) {
        val dependencyName = dependencyJarMatcher.group(1);
        val dependency = new Dependency();
        dependency.setArtifactId("gradle-"+dependencyName);
        dependency.setGroupId(currentProject.getPom().getParent().getGroupId());
        dependency.setVersion("${project.version}");
        currentProject.getPom().addDependency(dependency);
      }

      val dependencyPomMatcher = projectPomDependencyPattern.matcher(currentLine);
      if(dependencyPomMatcher.find()) {
        val dependencyName = dependencyPomMatcher.group(1);
        val dependency = new Dependency();
        dependency.setArtifactId("gradle-"+dependencyName);
        dependency.setGroupId(currentProject.getPom().getParent().getGroupId());
        dependency.setVersion("${project.version}");
        dependency.setType("pom");
        currentProject.getPom().addDependency(dependency);
      }
    }
  }
}
