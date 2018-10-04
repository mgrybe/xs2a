/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.keycloak.extension.clientregistration;

public class SSAService {

    // this could throw invalid_software_statement or
    // unapproved_software_statement exception
    // https://tools.ietf.org/html/rfc7591#section-3.2.1
    public static void validate(OIDCClientRepresentationExtended clientOIDC) {

    }

}
