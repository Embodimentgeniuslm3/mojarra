<%--

    Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.

    This program and the accompanying materials are made available under the
    terms of the Eclipse Public License v. 2.0, which is available at
    http://www.eclipse.org/legal/epl-2.0.

    This Source Code may also be made available under the following Secondary
    Licenses when the conditions for such availability set forth in the
    Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
    version 2 with the GNU Classpath Exception, which is available at
    https://www.gnu.org/software/classpath/license.html.

    SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

--%>

<%@ page contentType="text/html"
%><%@ page import="jakarta.faces.application.Application"
%><%@ page import="jakarta.faces.context.FacesContext"
%><%@ page import="jakarta.faces.el.ValueBinding"
%><%@ page import="com.sun.faces.systest.model.TestBean"
%><%

  // Instantiate our test bean in request scope
  TestBean bean = new TestBean();
  FacesContext context = FacesContext.getCurrentInstance();
  context.getExternalContext().getRequestMap().put
   ("testVB", bean);

  // Retrieve a simple String property with a value binding expression
  ValueBinding vb = context.getApplication().createValueBinding
   ("#{testVB.stringProperty}");
  Object result;
  try {
    result = vb.getValue(context);
  } catch (Exception e) {
    out.println("/valueBinding02.jsp FAILED - getValue() exception: " + e);
    e.printStackTrace(System.out);
    return;
  }

  // Validate the result
  if (result == null) {
    out.println("/valueBinding02.jsp FAILED - getValue() returned null");
  } else if (!(result instanceof String)) {
    out.println("/valueBinding02.jsp FAILED - getValue() returned " + result);
  } else if (!"This is a String property".equals((String) result)) {
    out.println("/valueBinding02.jsp FAILED - getValue() returned " + result);
  } else {
    out.println("/valueBinding02.jsp PASSED");
  }

%>
