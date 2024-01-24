package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Main {

	public static void main(String[] args) {
		String xmlFilePath = "src/main/resources/file.xml";

		try {
			File xmlFile = new File(xmlFilePath);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			NodeList changeSets = document.getElementsByTagName("changeSet");

			for (int i = 0; i < changeSets.getLength(); i++) {
				Node changeSet = changeSets.item(i);
				if (changeSet.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) changeSet;

					String id = element.getAttribute("id");
					String author = element.getAttribute("author");

					NodeList sqlNodes = element.getElementsByTagName("sql");

					for (int j = 0; j < sqlNodes.getLength(); j++) {
						Node sqlNode = sqlNodes.item(j);
						if (sqlNode.getNodeType() == Node.ELEMENT_NODE) {
							Element sqlElement = (Element) sqlNode;

							String sqlContent = sqlElement.getTextContent().trim();

							// Extract insert and log errors statements
							if (sqlContent.startsWith("insert") || sqlContent.startsWith("log errors")) {
//								System.out.println("ChangeSet ID: " + id);
//								System.out.println("Author: " + author);
								System.out.println(sqlContent + ";");
//								System.out.println("------------------------");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}