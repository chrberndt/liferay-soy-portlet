package com.chberndt.liferay.soy.portlet.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
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
		"javax.portlet.name=hello_soy_portlet", "mvc.command.name=Navigation"
	},
	service = MVCRenderCommand.class
)
public class HelloSoyNavigationMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		Template template = (Template)renderRequest.getAttribute(
			WebKeys.TEMPLATE);

		PortletURL backURL = renderResponse.createRenderURL();

		backURL.setParameter("mvcRenderCommandName", "View");

		template.put("backURL", backURL.toString());

		PortletURL formURL = renderResponse.createActionURL();

		formURL.setParameter(ActionRequest.ACTION_NAME, "Form");

		template.put("formURL", formURL.toString());

		template.put("releaseInfo", ReleaseInfo.getReleaseInfo());

		return "Navigation";
	}

}