package de.hsudbrock.portagemavenworkspacereader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.WorkspaceReader;
import org.eclipse.aether.repository.WorkspaceRepository;

@Named("ide")
@Singleton
public class PortageWorkspaceReader implements WorkspaceReader {
	
	private static final Path ARTIFACT_REGISTRY_DIRECTORY = Paths.get("/usr/share/portage-maven-artifact-registry");
	
	private final WorkspaceRepository workspaceRepository;
	
	public PortageWorkspaceReader() {
	    this.workspaceRepository = new WorkspaceRepository("portage", "portage");
	  }

	@Override
	public WorkspaceRepository getRepository() {
		return workspaceRepository;
	}

	@Override
	public File findArtifact(Artifact artifact) {
		System.out.println("Trying to find artifact: " + artifact);
		Path artifactLocatorFile = getArtifactLocatorFile(artifact);
		if (artifactLocatorFile != null) {
			String artifactLocation;
			try {
				artifactLocation = Files.readAllLines(artifactLocatorFile).get(0);
			} catch (IOException e) {
				System.out.println("Could not read artifact locator file at: " + artifactLocatorFile);	
				return null;
			}
			System.out.println("Found artifact location for " + artifact + " at " + artifactLocation);
			return new File(artifactLocation);
		} else {
			System.out.println("Could not find artifact location for: " + artifact);
			return null;
		}
	}

	@Override
	public List<String> findVersions(Artifact artifact) {
		System.out.println("Trying to find versions: " + artifact);
		System.out.println("Not yet implemented");
		return new ArrayList<>();
	}
	
	// TOOD: Support classifier?
	private Path getArtifactLocatorFile(Artifact artifact) {
		Path fromSourcePath = ARTIFACT_REGISTRY_DIRECTORY
				.resolve("source")
				.resolve(artifact.getGroupId())
				.resolve(artifact.getArtifactId())
				.resolve(artifact.getVersion() + "-" + artifact.getExtension() + ".location");
		if (Files.exists(fromSourcePath)) {
			return fromSourcePath;
		}
		
		Path binaryPath = ARTIFACT_REGISTRY_DIRECTORY
				.resolve("binary")
				.resolve(artifact.getGroupId())
				.resolve(artifact.getArtifactId())
				.resolve(artifact.getVersion() + "-" + artifact.getExtension() + ".location");
		if (Files.exists(binaryPath)) {
			return binaryPath;
		}
		
		return null;
	}

}
