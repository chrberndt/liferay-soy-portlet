package com.chberndt.liferay.soy.portlet.internal.portlet.action;

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Christian Berndt
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=hello_soy_portlet", "mvc.command.name=Form"
	},
	service = MVCActionCommand.class
)
public class HelloSoyFormMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		String submittedData = ParamUtil.getString(
			actionRequest, "submittedData");

		SessionMessages.add(actionRequest, "Your name is: " + submittedData);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "View");

		sendRedirect(actionRequest, actionResponse, portletURL.toString());
	}

	@Reference
	private Portal _portal;

}