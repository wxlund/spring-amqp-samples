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

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

import com.rabbitmq.client.Envelope;

/**
 *
 * @author Gary Russell, Scott Deeg, Wayne Lund
 *
 */
public class Tut7Receiver {
	
	@RabbitListener(queues="queue1")
	public void receive1(String in) throws InterruptedException {
		System.out.println(" [x] Received '"  + "':'" + in + "1" + "'");
//		receive(in, 1);
	}

	@RabbitListener(queues="queue2")
	public void receive2(String in) throws InterruptedException {
		System.out.println(" [x] Received '" +  "':'" + in + "2" + "'");
//		receive(in, 2);
	}

	public void receive(String in, int receiver) throws InterruptedException {
		StopWatch watch = new StopWatch();
		watch.start();
//		System.out.println("instance " + receiver + " [x] Received '" + in + "'");
		dowork(in);
		watch.stop();
		System.out.println("instance " + receiver + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
	}

	private void dowork(String in) throws InterruptedException {
		System.out.println("Message: " + in);
	}
}
