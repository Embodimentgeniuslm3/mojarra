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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>test</title>
    <%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
    <%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
</head>

<body>
<h1>test</h1>

<f:view>
    <%-- Ensure converters are applied for List as well as Array
         target values --%>
    <h:form>
        <h:selectManyListbox id="list" value="#{testListBox.listResults}"
                             converter="#{testBeanConverter}">
            <f:selectItems value="#{testListBox.selectItems}"/>
        </h:selectManyListbox>
        <h:message for="list"/>
        <br/>
        <h:selectManyListbox id="array" value="#{testListBox.arrayResults}"
                             converter="#{testBeanConverter}">
            <f:selectItems value="#{testListBox.selectItems}"/>
        </h:selectManyListbox>
        <h:message for="array"/>
        <br/>
        <h:commandButton id="submit" value="Submit"/>
    </h:form>
</f:view>

<hr>
</body>
</html>
