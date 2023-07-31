package org.squat_team.vis.test.analysis;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement
@Data
public class ArchitectureContainerResource {
	private Long id;

	private String name;
	private String resourceId;
	private boolean isLink;
	private List<ArchitectureResource> resources = new ArrayList<>();;
}
