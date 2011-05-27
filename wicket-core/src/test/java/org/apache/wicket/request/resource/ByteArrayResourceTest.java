/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.request.resource;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.junit.Test;

/**
 * Unit tests for {@link ByteArrayResource}
 */
public class ByteArrayResourceTest
{

	/**
	 * Unit test for {@link ByteArrayResource} with static byte array.
	 */
	@Test
	public void staticResource()
	{
		String contentType = "application/x-octet";
		byte[] array = new byte[] { 1, 2, 3 };
		ByteArrayResource resource = new ByteArrayResource(contentType, array)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void configureCache(ResourceResponse data, Attributes attributes)
			{
				// no caching is needed
			}
		};

		WebRequest request = mock(WebRequest.class);
		WebResponse response = mock(WebResponse.class);

		Attributes attributes = new Attributes(request, response);
		resource.respond(attributes);

		verify(response).write(same(array));
		verify(response).setContentLength(eq(3L));
		verify(response).setContentType(eq(contentType));
	}

	/**
	 * Unit test for {@link ByteArrayResource} with dynamically generated byte array.
	 */
	@Test
	public void dynamicResource()
	{
		String contentType = "application/x-octet";
		final byte[] array = new byte[] { 1, 2, 3 };
		ByteArrayResource resource = new ByteArrayResource(contentType)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected byte[] getData(Attributes attributes)
			{
				return array;
			}

			@Override
			protected void configureCache(ResourceResponse data, Attributes attributes)
			{
				// no caching is needed
			}
		};

		WebRequest request = mock(WebRequest.class);
		WebResponse response = mock(WebResponse.class);

		Attributes attributes = new Attributes(request, response);
		resource.respond(attributes);

		verify(response).write(same(array));
		verify(response).setContentLength(eq(3L));
		verify(response).setContentType(eq(contentType));
	}
}