package org.squat_team.vis.test.exporter;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.squat_team.vis.test.analysis.ArchitectureAnalysisData;

public class XmlExporter {
	public void jaxbObjectToXML(ArchitectureAnalysisData candidate) 
	  {
	      try
	      {
	        //Create JAXB Context
	          JAXBContext jaxbContext = JAXBContext.newInstance(ArchitectureAnalysisData.class);
	           
	          //Create Marshaller
	          Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
	          //Required formatting??
	          jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	 
	         //Store XML to File
	          File file = new File("SquatVisExports/" + candidate.getId() + ".xml");
	          System.out.println(file.getAbsolutePath());
	           
	          //Writes XML file to file-system
	          jaxbMarshaller.marshal(candidate, file); 
	      } 
	      catch (JAXBException e) 
	      {
	          e.printStackTrace();
	      }
	  }
}
