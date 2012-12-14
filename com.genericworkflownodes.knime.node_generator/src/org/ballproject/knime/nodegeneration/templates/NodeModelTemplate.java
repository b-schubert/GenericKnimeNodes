package org.ballproject.knime.nodegeneration.templates;

import java.io.IOException;

import org.ballproject.knime.nodegeneration.NodeGenerator;
import org.ballproject.knime.nodegeneration.exceptions.UnknownMimeTypeException;

import com.genericworkflownodes.knime.config.INodeConfiguration;
import com.genericworkflownodes.knime.port.Port;

public class NodeModelTemplate extends Template {

	public NodeModelTemplate(String packageName, String nodeName,
			INodeConfiguration nodeConfiguration) throws IOException,
			UnknownMimeTypeException {
		super(NodeGenerator.class
				.getResourceAsStream("templates/NodeModel.template"));

		this.replace("__BASE__", packageName);
		this.replace("__NODENAME__", nodeName);

		fillMimeTypes(nodeConfiguration);
	}

	private void fillMimeTypes(INodeConfiguration config)
			throws UnknownMimeTypeException {
		String clazzez = "";
		for (Port port : config.getInputPorts()) {
			String tmp = "{";
			for (String type : port.getMimeTypes()) {
				String ext = type.toLowerCase();
				if (ext == null) {
					throw new UnknownMimeTypeException(type);
				}
				/*
				 * if(port.isMultiFile()) tmp +=
				 * "DataType.getType(ListCell.class, DataType.getType(" + ext +
				 * "FileCell.class)),"; else tmp += "DataType.getType(" + ext +
				 * "FileCell.class),";
				 */
				tmp += "\"" + ext + "\",";
			}
			tmp = tmp.substring(0, tmp.length() - 1);
			tmp += "},";
			clazzez += tmp;
		}

		if (!clazzez.equals("")) {
			clazzez = clazzez.substring(0, clazzez.length() - 1);
		}

		clazzez += "}";
		createInClazzezModel(clazzez);

		clazzez = "";
		for (Port port : config.getOutputPorts()) {
			String tmp = "{";
			for (String type : port.getMimeTypes()) {
				String ext = type.toLowerCase();
				if (ext == null) {
					throw new UnknownMimeTypeException(type);
				}
				/*
				 * if(port.isMultiFile()) tmp +=
				 * "DataType.getType(ListCell.class, DataType.getType(" + ext +
				 * "FileCell.class)),"; else tmp += "DataType.getType(" + ext +
				 * "FileCell.class),";
				 */
				tmp += "\"" + ext + "\",";
			}
			tmp = tmp.substring(0, tmp.length() - 1);
			tmp += "},";
			clazzez += tmp;
		}

		if (!clazzez.equals("")) {
			clazzez = clazzez.substring(0, clazzez.length() - 1);
		}

		clazzez += "}";

		createOutClazzezModel(clazzez);
	}

	private void createInClazzezModel(String clazzez) {
		if (clazzez.equals("")) {
			clazzez = "null";
		} else {
			clazzez = clazzez.substring(0, clazzez.length() - 1);
		}
		this.replace("__INCLAZZEZ__", clazzez);
	}

	private void createOutClazzezModel(String clazzez) {
		if (clazzez.equals("")) {
			clazzez = "null";
		} else {
			clazzez = clazzez.substring(0, clazzez.length() - 1);
		}
		this.replace("__OUTCLAZZEZ__", clazzez);
	}

}
