package de.hsudbrock.portagemavenworkspacereader;

import java.io.File;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.WorkspaceReader;
import org.eclipse.aether.repository.WorkspaceRepository;

@Named("ide")
@Singleton
public class PortageWorkspaceReader implements WorkspaceReader {
	
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
		return null;
	}

	@Override
	public List<String> findVersions(Artifact artifact) {
		System.out.println("Trying to find versions: " + artifact);
		return null;
	}

}
