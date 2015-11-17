/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.amqp.tutorials.tut7;

import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Gary Russell, Scott Deeg
 *
 */
public class Tut7Sender {

	String routingKey = "ourTestRoutingKey";
	
	@Autowired
	private RabbitTemplate template;

	@Autowired
	private HeadersExchange headersExchange;

	
		
	@Scheduled(fixedDelay = 1000, initialDelay = 500)
	public void send() {

		while (true) {
			

			MessageProperties props = MessagePropertiesBuilder.newInstance()
				    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
				    .setMessageId("123")
				    .setHeader("queue1", "x-match")
				    .setHeader("Temperature", "hot")
				    .setHeader("Color", "white")
				    .build();
			Message message = MessageBuilder.withBody("Headers Test Message".getBytes())
				    .andProperties(props)
				    .build();

			template.convertAndSend(headersExchange.getName(), routingKey, message);
			System.out.println(" [x] Sent '" + message + "'");
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}
}
