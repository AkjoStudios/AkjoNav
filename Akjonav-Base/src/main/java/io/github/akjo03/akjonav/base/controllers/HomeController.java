package io.github.akjo03.akjonav.base.controllers;

import io.github.akjo03.akjonav.base.constants.AkjonavBaseConstants;
import io.github.akjo03.akjonav.base.services.SimpleHtmlService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class HomeController {
	private final SimpleHtmlService htmlService;

	@Hidden
	@RequestMapping("/")
	public String home(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html lang=\"en\">\n");
		sb.append("<head>\n");
		sb.append("<title>Akjonav-Base API - Home</title>\n");
		sb.append(htmlService.getDefaultStyle());
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("<h1>Akjonav-Base API - Home</h1>\n");
		sb.append("<p><strong>Module Name:</strong> ").append(AkjonavBaseConstants.MODULE_NAME).append("</p>\n");
		sb.append("<p><strong>App Name:</strong> ").append(AkjonavBaseConstants.APP_NAME).append("</p>\n");
		sb.append("<p><strong>App Version:</strong> ").append(AkjonavBaseConstants.APP_VERSION).append("</p>\n");
		sb.append("<br />");
		sb.append("<a href=\"").append(request.getContextPath()).append("/swagger-ui.html").append("\">Link to Swagger</a>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}
}