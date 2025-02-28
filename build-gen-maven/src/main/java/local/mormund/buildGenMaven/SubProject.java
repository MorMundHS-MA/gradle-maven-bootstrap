package local.mormund.buildGenMaven;

import java.nio.file.Path;

import org.apache.maven.model.Model;

import lombok.Value;

@Value
public class SubProject {
  private String name;
  private String path;
  private Model pom;

  public Path getDirectoryPath() {
    return Path.of(path);
  }

  public Path getPomPath() {
    return getDirectoryPath().resolve("pom.xml");
  }
}
