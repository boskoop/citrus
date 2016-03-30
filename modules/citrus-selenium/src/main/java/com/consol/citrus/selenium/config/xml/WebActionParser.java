/*
 * Copyright 2006-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.selenium.config.xml;

import com.consol.citrus.config.util.BeanDefinitionParserUtils;
import com.consol.citrus.config.xml.DescriptionElementParser;
import com.consol.citrus.selenium.action.WebAction;
import com.consol.citrus.selenium.client.ByEnum;
import org.openqa.selenium.By;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import java.lang.reflect.Method;

/**
 * WebBrowser client parser sets properties on bean definition for client
 * component.
 *
 * @author Tamer Erdogan
 */
public class WebActionParser implements BeanDefinitionParser {

    protected Class actionClass = WebAction.class;

    /**
     * @param element
     * @param builder
     * @return
     * @see
     * org.springframework.beans.factory.xml.BeanDefinitionParser#parse(org.w3c.dom.Element,
     * org.springframework.beans.factory.xml.ParserContext)
     */
    protected BeanDefinition doParse(Element element, BeanDefinitionBuilder builder) {
        DescriptionElementParser.doParse(element, builder);

        String url = element.getAttribute("url").trim();
        if (StringUtils.hasText(url)) {
            BeanDefinitionParserUtils.setPropertyValue(builder, url, "url");
        }

        String name = element.getAttribute("name").trim();
        if (StringUtils.hasText(name)) {
            BeanDefinitionParserUtils.setPropertyValue(builder, name, "name");
        }

        builder.addPropertyReference("webClient", element.getAttribute("client"));

        return builder.getBeanDefinition();
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(actionClass);
        this.doParse(element, builder);

        return builder.getBeanDefinition();
    }

    protected By getByFromElement(Element webElement) {
        for (ByEnum b : ByEnum.values()) {
            String byWhat = b.getSelectorId();
            String byAttrName = "by" + StringUtils.capitalize(byWhat);
            if (StringUtils.hasText(webElement.getAttribute(byAttrName))) {
                String selector = webElement.getAttribute(byAttrName).toLowerCase().trim();
                try {
                    Method byMethod = By.class.getMethod(byWhat, String.class);
                    return (By) byMethod.invoke(null, selector);
                } catch (Exception ex) {
                    throw new BeanCreationException("Failed to create web element selector", ex);
                }
            }
        }

        throw new BeanCreationException("Failed to create web element selector - unknown selector type");
    }
}
