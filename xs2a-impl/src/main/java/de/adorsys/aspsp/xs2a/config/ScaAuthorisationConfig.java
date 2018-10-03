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

package de.adorsys.aspsp.xs2a.config;

import de.adorsys.aspsp.xs2a.service.AisConsentDataService;
import de.adorsys.aspsp.xs2a.service.authorisation.ais.*;
import de.adorsys.aspsp.xs2a.service.authorisation.pis.*;
import de.adorsys.aspsp.xs2a.service.consent.AisConsentService;
import de.adorsys.aspsp.xs2a.service.mapper.PaymentMapper;
import de.adorsys.aspsp.xs2a.service.mapper.consent.Xs2aAisConsentMapper;
import de.adorsys.aspsp.xs2a.service.mapper.consent.Xs2aPisConsentMapper;
import de.adorsys.aspsp.xs2a.service.payment.*;
import de.adorsys.aspsp.xs2a.service.profile.AspspProfileServiceWrapper;
import de.adorsys.aspsp.xs2a.spi.service.AccountSpi;
import de.adorsys.aspsp.xs2a.spi.service.PaymentSpi;
import de.adorsys.psd2.aspsp.profile.domain.ScaApproach;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static de.adorsys.psd2.aspsp.profile.domain.ScaApproach.*;

// TODO refactor to AbstractFactory https://git.adorsys.de/adorsys/xs2a/aspsp-xs2a/issues/298
@Configuration
@RequiredArgsConstructor
public class ScaAuthorisationConfig {
    private final AspspProfileServiceWrapper aspspProfileService;
    private final AisConsentDataService aisConsentDataService;

    @Bean
    public ScaPaymentService scaPaymentService(PaymentMapper paymentMapper,
                                               PaymentSpi paymentSpi) {
        ScaApproach scaApproach = getScaApproach();
        if (OAUTH == scaApproach) {
            return new OauthScaPaymentService(paymentMapper, paymentSpi);
        }
        if (DECOUPLED == scaApproach) {
            return new DecoupedScaPaymentService();
        }
        if (EMBEDDED == scaApproach) {
            return new EmbeddedScaPaymentService(paymentSpi, paymentMapper);
        }
        return new RedirectScaPaymentService(paymentSpi, paymentMapper);
    }

    @Bean
    public AisAuthorisationService aisAuthorisationService(AccountSpi accountSpi, AisConsentService aisConsentService,
                                                           Xs2aAisConsentMapper aisConsentMapper
    ) {
        switch (getScaApproach()) {
            case OAUTH:
                return new OauthAisAuthorisationService();
            case DECOUPLED:
                return new DecoupledAisAuthorisationService();
            case EMBEDDED:
                return new EmbeddedAisAuthorisationService(accountSpi, aisConsentService, aisConsentMapper, aisConsentDataService);
            default:
                return new RedirectAisAuthorisationService();
        }
    }

    @Bean
    public PisScaAuthorisationService pisScaAuthorisationService(PisAuthorisationService pisAuthorisationService,
                                                              Xs2aPisConsentMapper pisConsentMapper) {
        ScaApproach scaApproach = getScaApproach();
        if (OAUTH == scaApproach) {
            return new OauthPisScaAuthorisationService();
        }
        if (DECOUPLED == scaApproach) {
            return new DecoupledPisScaAuthorisationService();
        }
        if (EMBEDDED == scaApproach) {
            return new EmbeddedPisScaAuthorisationService(pisAuthorisationService, pisConsentMapper);
        }
        return new RedirectPisScaAuthorisationService(pisAuthorisationService, pisConsentMapper);
    }

    private ScaApproach getScaApproach() {
        return aspspProfileService.getScaApproach();
    }
}
