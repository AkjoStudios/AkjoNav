package io.github.akjo03.akjonav.base.services;

import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("StringBufferReplaceableByString")
public class SimpleHtmlService {
	public String getDefaultStyle() {
		StringBuilder sb = new StringBuilder();
		sb.append("<style>\n");
		sb.append("body {\n");
		sb.append("	 background-color: #f5f5f5;\n");
		sb.append("  padding: 20px;\n");
		sb.append("	 font-family: 'Open Sans', sans-serif;\n");
		sb.append("	 font-size: 16px;\n");
		sb.append("	 color: #333;\n");
		sb.append("}\n");
		sb.append("</style>\n");
		return sb.toString();
	}
}