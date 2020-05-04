package com.chberndt.liferay.soy.portlet.internal.portlet.action;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Christian Berndt
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=hello_soy_portlet", "mvc.command.name=/",
		"mvc.command.name=View"
	},
	service = MVCRenderCommand.class
)
public class HelloSoyViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		Template template = (Template)renderRequest.getAttribute(
			WebKeys.TEMPLATE);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<Layout> layouts = themeDisplay.getLayouts();

		Stream<Layout> layoutStream = layouts.stream();

		template.put(
			"layouts",
			layoutStream.map(
				layout -> new HashMap<String, String>() {
					{
						put("friendlyURL", layout.getFriendlyURL());
						put("nameCurrentValue", layout.getNameCurrentValue());
					}
				}
			).collect(
				Collectors.toList()
			));

		PortletURL navigationURL = renderResponse.createRenderURL();

		navigationURL.setParameter("mvcRenderCommandName", "Navigation");

		template.put("navigationURL", navigationURL.toString());

		template.put("releaseInfo", ReleaseInfo.getReleaseInfo());

		return "View";
	}

}