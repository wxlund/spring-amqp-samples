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

import java.util.Collections;
import java.util.HashMap;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Gary Russell, Scott Deeg, Wayne Lund
 *
 */
@Profile({"tut7","header-exchange"})
@Configuration
public class Tut7Config {


	@Bean
	public HeadersExchange headers() {
		return new HeadersExchange("tut7.headers-exchange");
	}

	@Profile("receiver")
	private static class ReceiverConfig {
		
		@Bean
		public Queue autoDeleteQueue1() {
			return new AnonymousQueue();
		}

		@Bean
		public Queue autoDeleteQueue2() {
			return new AnonymousQueue();
		}

		@Bean
		public Binding binding1(HeadersExchange headers, Queue autoDeleteQueue1) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("queue1", "x-match");
			map.put("Temperature", "hot");
			map.put("Color", "White");
			Binding binding = BindingBuilder.//
					bind(autoDeleteQueue1()).//
					to(headers).//
					whereAll(map).match();
			return binding;		
		}
	
		@Bean
		public Binding binding2(HeadersExchange headers, Queue autoDeleteQueue2) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("queue1", "x-match");
			map.put("Temperature", "hot");
			map.put("Color", "White");
			Binding binding = BindingBuilder.//
					bind(autoDeleteQueue2()).//
					to(headers).//
					whereAll(map).match();
			return binding;			
		}		

		@Bean
		public Tut7Receiver receiver() {
	 	 	return new Tut7Receiver();
		}
	}

	@Profile("sender")
	private static class SenderConfig
	{	
		@Bean
		public Tut7Sender sender() {
			return new Tut7Sender();
		}
	}
}
