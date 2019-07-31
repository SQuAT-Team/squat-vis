package org.squat_team.vis.util;

import java.io.File;

import org.squat_team.vis.connector.data.CArchitecture;

public class ArchitectureHelper {
	public static CArchitecture loadModel(CArchitecture architecture, String pathAndName) {
		architecture.setAllocation(new File(pathAndName + ".allocation"));
		architecture.setRepository(new File(pathAndName + ".repository"));
		architecture.setResourceenvironment(new File(pathAndName + ".resourceenvironment"));
		architecture.setSystem(new File(pathAndName + ".system"));
		architecture.setUsage(new File(pathAndName + ".usagemodel"));
		return architecture;
	}
}
