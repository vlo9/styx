/*
  Copyright (C) 2013-2018 Expedia Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.hotels.styx.server.routing.routes;

import com.hotels.styx.api.Eventual;
import com.hotels.styx.api.HttpHandler;
import com.hotels.styx.api.HttpInterceptor;
import com.hotels.styx.api.LiveHttpRequest;
import com.hotels.styx.api.LiveHttpResponse;
import com.hotels.styx.client.BackendServiceClient;

import static java.util.Objects.requireNonNull;

/**
 * A HTTP router route which proxies to Styx backend application.
 */
public final class ProxyToBackendRoute implements HttpHandler {
    private final BackendServiceClient client;

    private ProxyToBackendRoute(BackendServiceClient client) {
        this.client = requireNonNull(client);
    }

    public static ProxyToBackendRoute proxyToBackend(BackendServiceClient client) {
        return new ProxyToBackendRoute(client);
    }

    @Override
    public Eventual<LiveHttpResponse> handle(LiveHttpRequest request, HttpInterceptor.Context context) {
        return new Eventual<>(client.sendRequest(request));
    }
}
