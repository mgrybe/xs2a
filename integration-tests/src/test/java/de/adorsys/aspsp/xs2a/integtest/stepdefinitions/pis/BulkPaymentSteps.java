
package de.adorsys.aspsp.xs2a.integtest.stepdefinitions.pis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.adorsys.aspsp.xs2a.domain.pis.PaymentInitialisationResponse;
import de.adorsys.aspsp.xs2a.domain.pis.SinglePayment;
import de.adorsys.aspsp.xs2a.integtest.entities.ITMessageError;
import de.adorsys.aspsp.xs2a.integtest.model.TestData;
import de.adorsys.aspsp.xs2a.integtest.util.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@FeatureFileSteps
public class BulkPaymentSteps {
    @Autowired
    @Qualifier("xs2a")
    private RestTemplate restTemplate;

    @Autowired
    private Context<List<SinglePayment>, List<HashMap>, List<PaymentInitialisationResponse>> context;

    @Autowired
    private ObjectMapper mapper;

    @Given("^PSU wants to initiate multiple payments (.*) using the payment product (.*)$")
    public void loadTestDataBulkPayment(String dataFileName, String paymentProduct) throws IOException {
        context.setPaymentProduct(paymentProduct);

        TestData<List<SinglePayment>, List<HashMap>> data = mapper.readValue(resourceToString("/data-input/pis/bulk/" + dataFileName, UTF_8), new TypeReference<TestData<List<SinglePayment>, List<HashMap>>>() {
        });

        context.setTestData(data);
    }

    @When("^PSU sends the bulk payment initiating request$")
    public void sendBulkPaymentInitiatingRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(context.getTestData().getRequest().getHeader());
        headers.add("Authorization", "Bearer " + context.getAccessToken());
        headers.add("Content-Type", "application/json");

        List<SinglePayment> paymentsList = context.getTestData().getRequest().getBody();

        ResponseEntity<List<PaymentInitialisationResponse>> response = restTemplate.exchange(
            context.getBaseUrl() + "/bulk-payments/" + context.getPaymentProduct(),
            HttpMethod.POST, new HttpEntity<>(paymentsList, headers), new ParameterizedTypeReference<List<PaymentInitialisationResponse>>() {
            });

        context.setActualResponse(response);
    }

    @Then("^a successful response code and the appropriate bulk payment response data$")
    public void checkResponseCodeBulkPayment() {
        ResponseEntity<List<PaymentInitialisationResponse>> actualResponse = context.getActualResponse();
        List<HashMap> givenResponseBody = context.getTestData().getResponse().getBody();

        assertThat(actualResponse.getStatusCode(), equalTo(context.getTestData().getResponse().getHttpStatus()));

        assertThat(actualResponse.getBody().get(0).getTransactionStatus().name(), equalTo(givenResponseBody.get(0).get("transactionStatus")));
        assertThat(actualResponse.getStatusCode(), notNullValue());

        assertThat(actualResponse.getBody().get(1).getTransactionStatus().name(), equalTo(givenResponseBody.get(1).get("transactionStatus")));
    }

    @And("^a redirect URL for every payment of the Bulk payment is delivered to the PSU$")
    public void checkRedirectUrlBulkPayment() {
        ResponseEntity<List<PaymentInitialisationResponse>> actualResponse = context.getActualResponse();

        assertThat(actualResponse.getBody().get(0).getLinks().getScaRedirect(), notNullValue());
        assertThat(actualResponse.getBody().get(1).getLinks().getScaRedirect(), notNullValue());
    }

    @When("^PSU sends the bulk payment initiating request with error$")
    public void sendBulkInitiatingPaymentWithError() throws HttpClientErrorException, IOException{
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(context.getTestData().getRequest().getHeader());
        headers.add("Authorization", "Bearer " + context.getAccessToken());
        headers.add("Content-Type", "application/json");

        List<SinglePayment> bulkPayments = context.getTestData().getRequest().getBody();
        try{
            restTemplate.exchange(
                context.getBaseUrl() + "/bulk-payments/" + context.getPaymentProduct(),
                HttpMethod.POST, new HttpEntity<>(bulkPayments, headers), new ParameterizedTypeReference<List<PaymentInitialisationResponse>>() {
                });
        }catch (HttpClientErrorException rex){
            ResponseEntity<List<PaymentInitialisationResponse>> response =new ResponseEntity<>(
                rex.getStatusCode());
            context.setActualResponse(response);

            ITMessageError messageError = mapper.readValue(rex.getResponseBodyAsString(), ITMessageError.class);
            context.setMessageError(messageError);
        }
    }

    @Then("^error responses are displayed the appropriate error response")
    public void displayedAppropriateErrorResponses(){
        ITMessageError givenErrorObject = context.getMessageError();
         List<HashMap> responses = context.getTestData().getResponse().getBody();

        HttpStatus httpStatus = context.getTestData().getResponse().getHttpStatus();
        assertThat(context.getActualResponse().getStatusCode(), equalTo(httpStatus));

        LinkedHashMap tppMessageContent = (LinkedHashMap) responses.get(1);
        LinkedHashMap transactionState = (LinkedHashMap) responses.get(0);

        if (givenErrorObject.getTppMessage() != null) {
            assertThat(givenErrorObject.getTransactionStatus().name(), equalTo(transactionState.get("transactionStatus")));

            assertThat(givenErrorObject.getTppMessage().getCategory().name(), equalTo(tppMessageContent.get("category")));
            assertThat(givenErrorObject.getTppMessage().getCode().name(), equalTo(tppMessageContent.get("code")));
        }
    }
}

