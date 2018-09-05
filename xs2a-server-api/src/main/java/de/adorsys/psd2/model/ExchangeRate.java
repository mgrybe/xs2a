package de.adorsys.psd2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Exchange Rate
 */
@ApiModel(description = "Exchange Rate")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2018-08-09T18:41:17.591+02:00[Europe/Berlin]")
public class ExchangeRate {

    @JsonProperty("Currency from")
    private String currencyFrom = null;

    @JsonProperty("Rate from")
    private String rateFrom = null;

    @JsonProperty("Currency to")
    private String currencyTo = null;

    @JsonProperty("Rate to")
    private String rateTo = null;

    @JsonProperty("Rate date")
    private LocalDate rateDate = null;

    @JsonProperty("Rate contract")
    private String rateContract = null;

    public ExchangeRate currencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
        return this;
    }

    /**
     * Get currencyFrom
     *
     * @return currencyFrom
     **/
    @ApiModelProperty(required = true)
    @NotNull

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public ExchangeRate rateFrom(String rateFrom) {
        this.rateFrom = rateFrom;
        return this;
    }

    /**
     * Get rateFrom
     *
     * @return rateFrom
     **/
    @ApiModelProperty(required = true)
    @NotNull

    public String getRateFrom() {
        return rateFrom;
    }

    public void setRateFrom(String rateFrom) {
        this.rateFrom = rateFrom;
    }

    public ExchangeRate currencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
        return this;
    }

    /**
     * Get currencyTo
     *
     * @return currencyTo
     **/
    @ApiModelProperty(required = true)
    @NotNull

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public ExchangeRate rateTo(String rateTo) {
        this.rateTo = rateTo;
        return this;
    }

    /**
     * Get rateTo
     *
     * @return rateTo
     **/
    @ApiModelProperty(required = true)
    @NotNull

    public String getRateTo() {
        return rateTo;
    }

    public void setRateTo(String rateTo) {
        this.rateTo = rateTo;
    }

    public ExchangeRate rateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
        return this;
    }

    /**
     * Get rateDate
     *
     * @return rateDate
     **/
    @ApiModelProperty(required = true)
    @NotNull
    @Valid
    public LocalDate getRateDate() {
        return rateDate;
    }

    public void setRateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
    }

    public ExchangeRate rateContract(String rateContract) {
        this.rateContract = rateContract;
        return this;
    }

    /**
     * Get rateContract
     *
     * @return rateContract
     **/
    @ApiModelProperty
    public String getRateContract() {
        return rateContract;
    }

    public void setRateContract(String rateContract) {
        this.rateContract = rateContract;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExchangeRate exchangeRate = (ExchangeRate) o;
        return Objects.equals(this.currencyFrom, exchangeRate.currencyFrom) &&
            Objects.equals(this.rateFrom, exchangeRate.rateFrom) &&
            Objects.equals(this.currencyTo, exchangeRate.currencyTo) &&
            Objects.equals(this.rateTo, exchangeRate.rateTo) &&
            Objects.equals(this.rateDate, exchangeRate.rateDate) &&
            Objects.equals(this.rateContract, exchangeRate.rateContract);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyFrom, rateFrom, currencyTo, rateTo, rateDate, rateContract);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ExchangeRate {\n");

        sb.append("    currencyFrom: ").append(toIndentedString(currencyFrom)).append("\n");
        sb.append("    rateFrom: ").append(toIndentedString(rateFrom)).append("\n");
        sb.append("    currencyTo: ").append(toIndentedString(currencyTo)).append("\n");
        sb.append("    rateTo: ").append(toIndentedString(rateTo)).append("\n");
        sb.append("    rateDate: ").append(toIndentedString(rateDate)).append("\n");
        sb.append("    rateContract: ").append(toIndentedString(rateContract)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
