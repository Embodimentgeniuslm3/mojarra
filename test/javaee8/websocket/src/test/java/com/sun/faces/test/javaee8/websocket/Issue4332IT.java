/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.test.javaee8.websocket;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.sun.faces.test.htmlunit.IgnoringIncorrectnessListener;
import static com.sun.faces.test.junit.JsfServerExclude.WEBLOGIC_12_2_1;
import static com.sun.faces.test.junit.JsfServerExclude.WEBLOGIC_12_3_1;
import com.sun.faces.test.junit.JsfTest;
import com.sun.faces.test.junit.JsfTestRunner;
import static com.sun.faces.test.junit.JsfVersion.JSF_2_3_0_M08;

@RunWith(JsfTestRunner.class)
public class Issue4332IT {

    private String webUrl;
    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.url");
        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setJavaScriptTimeout(120000);
    }

    @Test
    @JsfTest(value=JSF_2_3_0_M08, excludes={WEBLOGIC_12_2_1, WEBLOGIC_12_3_1})
    public void test() throws Exception {
        webClient.setIncorrectnessListener(new IgnoringIncorrectnessListener());

        testWebsocketAfterPostback();
    }

    public void testWebsocketAfterPostback() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "issue4332.xhtml");

        HtmlSubmitInput postback = (HtmlSubmitInput) page.getHtmlElementById("form:postback");
        page = postback.click();
        webClient.waitForBackgroundJavaScript(60000);
        
        String pageSource = page.getWebResponse().getContentAsString();
        assertTrue(pageSource.contains("/jakarta.faces.push/push?"));

        HtmlSubmitInput button = (HtmlSubmitInput) page.getHtmlElementById("form:button");
        assertTrue(button.asText().equals("push"));

        page = button.click();
        webClient.waitForBackgroundJavaScript(60000);
       
        for (int i=0; i<6; i++) {
            try {
                System.out.println("Wait until WS push - iteration #" + i);
                Thread.sleep(1000); // waitForBackgroundJavaScript doesn't wait until the WS push is arrived.

                assertTrue(page.getHtmlElementById("form:button").asText().equals("pushed!"));
                
                break;
            } catch (Error e) {
                e.printStackTrace();
            }
        }
        
    }

    @After
    public void tearDown() {
        webClient.close();
    }

}