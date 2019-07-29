package org.squat_team.vis.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.allocation.AllocationContext;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.squat_team.vis.connector.ProjectConnector;
import org.squat_team.vis.connector.data.CArchitecture;
import org.squat_team.vis.connector.data.CCandidate;
import org.squat_team.vis.connector.data.CLevel;
import org.squat_team.vis.connector.server.ConnectorService;
import org.squat_team.vis.data.daos.ArchitectureAnalysisDataDao;
import org.squat_team.vis.data.daos.ArchitectureContainerResourceDao;
import org.squat_team.vis.data.daos.CandidateDao;
import org.squat_team.vis.data.data.ArchitectureAnalysisData;
import org.squat_team.vis.data.data.ArchitectureComponent;
import org.squat_team.vis.data.data.ArchitectureComponentAllocation;
import org.squat_team.vis.data.data.ArchitectureComponentLink;
import org.squat_team.vis.data.data.ArchitectureContainerResource;
import org.squat_team.vis.data.data.ArchitectureResource;
import org.squat_team.vis.data.data.Candidate;

import de.fakeller.palladio.environment.PalladioEclipseEnvironment;
import de.uka.ipd.sdq.pcmsolver.models.PCMInstance;
import io.github.squat_team.callgraph.CallGraphGenerator;
import io.github.squat_team.callgraph.config.CallGraphConfiguration;
import io.github.squat_team.callgraph.data.CallGraphManager;
import lombok.extern.java.Log;

/**
 * Analyzes the architecture of a {@link CCandidate}. Searches for used
 * components and their dependencies as well as their allocation. The results
 * are then stored in the database.
 */
@Log
public class PCMArchitectureAnalyzer {
	private ConnectorService connectorService;
	private ProjectConnector projectConnector;
	private CLevel level;

	/**
	 * Initializes a new analyzer. Each analyzer should only be used for one level.
	 * 
	 * @param level            the level with the candidates to analyze
	 * @param projectConnector identifies the project
	 */
	public PCMArchitectureAnalyzer(CLevel level, ProjectConnector projectConnector, ConnectorService connectorService) {
		this.projectConnector = projectConnector;
		this.level = level;
		this.connectorService = connectorService;
	}

	/**
	 * Runs the analysis.
	 */
	public void analyze() {
		exportArchitectureFiles();
		analyzeCandidates();
	}

	private void analyzeCandidates() {
		for (CCandidate candidate : level.getCandidates()) {
			try {
				analyzeCandidateStatic(candidate);
			} catch (IOException e) {
				log.log(java.util.logging.Level.SEVERE,
						"Error during static analysis of candidate " + candidate.getCandidateId(), e);
			}
			try {
				analyzeCandidateDynamic(candidate);
			} catch (Exception e) {
				log.log(java.util.logging.Level.SEVERE,
						"Error during dynamic analysis of candidate " + candidate.getCandidateId(), e);
			}
		}
	}

	private void analyzeCandidateDynamic(CCandidate candidate) {
		runCallgraphAnalysis(candidate.getArchitecture());
	}

	/**
	 * Exports the byte-formatted architecture files to real files on disk.
	 */
	private void exportArchitectureFiles() {
		for (CCandidate candidate : this.level.getCandidates()) {
			try {
				exportArchitectureFiles(candidate);
			} catch (IOException e) {
				log.log(java.util.logging.Level.SEVERE, "Error while analyzing candidate " + candidate.getCandidateId(),
						e);
			}
		}
	}

	/**
	 * Exports the architecture files of a particular candidate.
	 * 
	 * @param candidate the candidates to consider
	 * @throws IOException
	 */
	private void exportArchitectureFiles(CCandidate candidate) throws IOException {
		// generate temp directory
		String tempDirectoryPrefix = "p" + projectConnector.getProjectId() + "l" + level.getLevelNumber() + "c"
				+ candidate.getCandidateId();
		File tempDirectory = Files.createTempDirectory(tempDirectoryPrefix).toFile();

		// export palladio files to disk
		CArchitecture architecture = candidate.getArchitecture();
		if (candidate.getArchitecture() != null) {
			architecture.setAllocation(exportArchitectureFile(architecture.getAllocationBytes(), tempDirectory,
					architecture.getAllocation().getName()));
			architecture.setRepository(exportArchitectureFile(architecture.getRepositoryBytes(), tempDirectory,
					architecture.getRepository().getName()));
			architecture.setResourceenvironment(exportArchitectureFile(architecture.getResourceenvironmentBytes(),
					tempDirectory, architecture.getResourceenvironment().getName()));
			architecture.setSystem(exportArchitectureFile(architecture.getSystemBytes(), tempDirectory,
					architecture.getSystem().getName()));
			architecture.setUsage(exportArchitectureFile(architecture.getUsageBytes(), tempDirectory,
					architecture.getUsage().getName()));
		}
	}

	/**
	 * Exports a specific file to disk.
	 * 
	 * @param fileData  the byte-formatted architecture file
	 * @param directory the directory to export to
	 * @param fileName  the name of the file
	 * @return the exported file
	 * @throws IOException
	 */
	private File exportArchitectureFile(byte[] fileData, File directory, String fileName) throws IOException {
		File outputFile = new File(directory, fileName);
		FileOutputStream fileOut = new FileOutputStream(outputFile);
		fileOut.write(fileData);
		fileOut.close();
		return outputFile;
	}

	private void runCallgraphAnalysis(CArchitecture architecture) {
		File repositoryFile = architecture.getRepository();
		String baseModelDirectory = repositoryFile.getParent();
		String baseModelName = repositoryFile.getName().replace(".repository", "");

		CallGraphConfiguration configuration;
		try {
			configuration = new CallGraphConfiguration(baseModelDirectory, baseModelName, "");
			configuration.setExportIds(false);
			configuration.setDebugMode(false);
			CallGraphGenerator callGenerator = new CallGraphGenerator(configuration);
			CallGraphManager callGraphManager = callGenerator.generateWithoutExport();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void analyzeCandidateStatic(CCandidate candidate) throws IOException {
		setupEclipseEnvironment();
		PCMInstance instance = loadPCMInstance(candidate);

		ArchitectureAnalysisData architectureAnalysis = new ArchitectureAnalysisData();

		Repository repository = instance.getRepositories().get(0);
		Map<String, ArchitectureComponent> components = createArchitectureComponents(repository);
		Map<String, ArchitectureComponent> requiredInterfaceMappings = findRequiredInterfaceMappings(repository,
				components);
		Map<String, ArchitectureComponent> providedInterfaceMappings = findProvidedInterfaceMappings(repository,
				components);

		org.palladiosimulator.pcm.system.System system = instance.getSystem();
		List<ArchitectureComponentLink> componentLinks = createArchitectureLinks(system, requiredInterfaceMappings,
				providedInterfaceMappings);

		ResourceEnvironment resourceEnvironment = instance.getResourceEnvironment();
		Map<String, ArchitectureContainerResource> resources = createContainerResources(resourceEnvironment);

		Allocation allocation = instance.getAllocation();
		List<ArchitectureComponentAllocation> allocations = createComponentAllocations(allocation, resources,
				components);

		architectureAnalysis.getComponents().addAll(components.values());
		architectureAnalysis.setComponentLinks(componentLinks);
		architectureAnalysis.getResourcesContainers().addAll(resources.values());
		architectureAnalysis.setAllocations(allocations);

		store(resources.values());
		store(architectureAnalysis);
		updateProject(architectureAnalysis, candidate);
	}

	private void setupEclipseEnvironment() {
		PalladioEclipseEnvironment.INSTANCE.setup();
	}

	private void store(Collection<ArchitectureContainerResource> resources) {
		ArchitectureContainerResourceDao dao = connectorService.getArchitectureContainerResourceDao();
		for (ArchitectureContainerResource resource : resources) {
			dao.save(resource);
		}
	}

	private void store(ArchitectureAnalysisData architectureAnalysis) {
		ArchitectureAnalysisDataDao architectureDao = connectorService.getArchitectureAnalysisDataDao();
		architectureDao.save(architectureAnalysis);
	}

	private void updateProject(ArchitectureAnalysisData architectureAnalysis, CCandidate ccandidate) {
		CandidateDao candidateDao = connectorService.getCandidateDao();
		Candidate candidate = candidateDao.find(projectConnector.getProjectId(), ccandidate.getCandidateId());
		candidate.setStaticArchitectureAnalysisData(architectureAnalysis);
		candidateDao.update(candidate);
	}

	private Map<String, ArchitectureComponent> createArchitectureComponents(Repository repository) {
		Map<String, ArchitectureComponent> components = new HashMap<>();
		for (RepositoryComponent repositoryComponent : repository.getComponents__Repository()) {
			ArchitectureComponent component = new ArchitectureComponent();
			component.setComponentId(repositoryComponent.getId());
			component.setName(repositoryComponent.getEntityName());
			components.put(component.getComponentId(), component);
		}
		return components;
	}

	private Map<String, ArchitectureComponent> findRequiredInterfaceMappings(Repository repository,
			Map<String, ArchitectureComponent> components) {
		Map<String, ArchitectureComponent> requiredInterfaceMappings = new HashMap<String, ArchitectureComponent>();
		for (RepositoryComponent repositoryComponent : repository.getComponents__Repository()) {
			ArchitectureComponent component = components.get(repositoryComponent.getId());
			for (RequiredRole requiredRole : repositoryComponent.getRequiredRoles_InterfaceRequiringEntity()) {
				requiredInterfaceMappings.put(requiredRole.getId(), component);
			}
		}
		return requiredInterfaceMappings;
	}

	private Map<String, ArchitectureComponent> findProvidedInterfaceMappings(Repository repository,
			Map<String, ArchitectureComponent> components) {
		Map<String, ArchitectureComponent> providedInterfaceMappings = new HashMap<String, ArchitectureComponent>();
		for (RepositoryComponent repositoryComponent : repository.getComponents__Repository()) {
			ArchitectureComponent component = components.get(repositoryComponent.getId());
			for (ProvidedRole providedRole : repositoryComponent.getProvidedRoles_InterfaceProvidingEntity()) {
				providedInterfaceMappings.put(providedRole.getId(), component);
			}
		}
		return providedInterfaceMappings;
	}

	private List<ArchitectureComponentLink> createArchitectureLinks(org.palladiosimulator.pcm.system.System system,
			Map<String, ArchitectureComponent> requiredInterfaceMappings,
			Map<String, ArchitectureComponent> providedInterfaceMappings) {
		List<ArchitectureComponentLink> architectureLinks = new ArrayList<>();
		for (Connector connector : system.getConnectors__ComposedStructure()) {
			if (connector instanceof AssemblyConnector) {
				AssemblyConnector assemblyConnector = (AssemblyConnector) connector;
				ArchitectureComponentLink architectureLink = new ArchitectureComponentLink();
				ArchitectureComponent sourceComponent = requiredInterfaceMappings
						.get(assemblyConnector.getRequiredRole_AssemblyConnector().getId());
				architectureLink.setSource(sourceComponent);
				ArchitectureComponent providedComponent = providedInterfaceMappings
						.get(assemblyConnector.getProvidedRole_AssemblyConnector().getId());
				architectureLink.setTarget(providedComponent);
				architectureLinks.add(architectureLink);
			}
		}
		return architectureLinks;
	}

	private Map<String, ArchitectureContainerResource> createContainerResources(
			ResourceEnvironment resourceEnvironment) {
		Map<String, ArchitectureContainerResource> containerResources = new HashMap<>();
		for (LinkingResource link : resourceEnvironment.getLinkingResources__ResourceEnvironment()) {
			ArchitectureContainerResource container = handleLinkingResource(link);
			containerResources.put(container.getResourceId(), container);
		}
		for (ResourceContainer resourceContainer : resourceEnvironment.getResourceContainer_ResourceEnvironment()) {
			ArchitectureContainerResource container = handleNormalResource(resourceContainer);
			containerResources.put(container.getResourceId(), container);
		}
		return containerResources;
	}

	private ArchitectureContainerResource handleLinkingResource(LinkingResource link) {
		ArchitectureContainerResource containerResource = handleLinkingResourceContainer(link);
		containerResource.getResources().add(handleLinkingResourceLatency(link));
		containerResource.getResources().add(handleLinkingResourceThroughput(link));
		return containerResource;
	}

	private ArchitectureContainerResource handleLinkingResourceContainer(LinkingResource link) {
		ArchitectureContainerResource containerResource = new ArchitectureContainerResource();
		containerResource.setResourceId(link.getId());
		containerResource.setName("Link " + containerResource.getName());
		containerResource.setLink(true);
		return containerResource;
	}

	private ArchitectureResource handleLinkingResourceLatency(LinkingResource link) {
		ArchitectureResource latencyResource = new ArchitectureResource();
		String latency = link.getCommunicationLinkResourceSpecifications_LinkingResource()
				.getLatency_CommunicationLinkResourceSpecification().getSpecification();
		latencyResource.setResourceId(link.getCommunicationLinkResourceSpecifications_LinkingResource().getId());
		latencyResource.setName("Latency");
		latencyResource.setValue(new Double(latency));
		return latencyResource;
	}

	private ArchitectureResource handleLinkingResourceThroughput(LinkingResource link) {
		ArchitectureResource throughputResource = new ArchitectureResource();
		String throughput = link.getCommunicationLinkResourceSpecifications_LinkingResource()
				.getThroughput_CommunicationLinkResourceSpecification().getSpecification();
		throughputResource.setResourceId(link.getCommunicationLinkResourceSpecifications_LinkingResource().getId());
		throughputResource.setName("Throughput");
		throughputResource.setValue(new Double(throughput));
		return throughputResource;
	}

	private ArchitectureContainerResource handleNormalResource(ResourceContainer palladioResourceContainer) {
		ArchitectureContainerResource containerResource = handleNormalResourceContainer(palladioResourceContainer);
		int i = 0;
		for (ProcessingResourceSpecification resourceSpecification : palladioResourceContainer
				.getActiveResourceSpecifications_ResourceContainer()) {
			containerResource.getResources().add(handleProcessingResourceSpecification(resourceSpecification, i));
			i++;
		}
		return containerResource;
	}

	private ArchitectureContainerResource handleNormalResourceContainer(ResourceContainer palladioResourceContainer) {
		ArchitectureContainerResource containerResource = new ArchitectureContainerResource();
		containerResource.setName(palladioResourceContainer.getEntityName());
		containerResource.setResourceId(palladioResourceContainer.getId());
		containerResource.setLink(false);
		return containerResource;
	}

	private ArchitectureResource handleProcessingResourceSpecification(
			ProcessingResourceSpecification resourceSpecification, int i) {
		ArchitectureResource resource = new ArchitectureResource();
		resource.setResourceId(resourceSpecification.getId());
		resource.setName("" + i);
		String value = resourceSpecification.getProcessingRate_ProcessingResourceSpecification().getSpecification();
		resource.setValue(new Double(value));
		return resource;
	}

	private List<ArchitectureComponentAllocation> createComponentAllocations(Allocation allocation,
			Map<String, ArchitectureContainerResource> resources, Map<String, ArchitectureComponent> components) {
		List<ArchitectureComponentAllocation> componentAllocations = new ArrayList<>();
		for (AllocationContext allocationContext : allocation.getAllocationContexts_Allocation()) {
			ArchitectureComponentAllocation componentAllocation = new ArchitectureComponentAllocation();
			String resourceContainerId = allocationContext.getResourceContainer_AllocationContext().getId();
			ArchitectureContainerResource allocationLocation = resources.get(resourceContainerId);
			String allocatedComponentId = allocationContext.getAssemblyContext_AllocationContext()
					.getEncapsulatedComponent__AssemblyContext().getId();
			ArchitectureComponent allocatedComponent = components.get(allocatedComponentId);
			componentAllocation.setComponent(allocatedComponent);
			componentAllocation.setContainer(allocationLocation);
			componentAllocations.add(componentAllocation);
		}
		return componentAllocations;
	}

	private PCMInstance loadPCMInstance(CCandidate candidate) throws IOException {
		File usageModel = candidate.getArchitecture().getUsage().getAbsoluteFile();
		String baseDirectory = usageModel.getParent();
		String baseFileName = usageModel.getName().replaceAll(".usagemodel", "");
		CallGraphConfiguration config = new CallGraphConfiguration(baseDirectory, baseFileName, "");
		Properties properties = new Properties();
		properties.put("Filename_UsageModel", config.getDependencySolverConfiguration().getUsageModelPath());
		properties.put("Filename_AllocationModel", config.getDependencySolverConfiguration().getAllocationModelPath());
		return new PCMInstance(properties);
	}

}
