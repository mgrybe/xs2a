/**
 * XS2A REST API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0
 * Contact: fpo@adorsys.de
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


/**
 * Authentication object
 */
export interface AuthenticationObject {
    /**
     * Provided by the ASPSP for the later identification of the authentication method selection.
     */
    authenticationMethodId: string;
    /**
     * Type of the authentication method
     */
    authenticationType: AuthenticationObject.AuthenticationTypeEnum;
    /**
     * Version can be used by differentiating authentication tools used within performing OTP generation in the same authentication type
     */
    authenticationVersion?: string;
    /**
     * Detailed information about the sca method for the PSU
     */
    explanation?: string;
    /**
     * Name of the authentication method
     */
    name?: string;
}
export namespace AuthenticationObject {
    export type AuthenticationTypeEnum = 'SMS_OTP' | 'CHIP_OTP' | 'PHOTO_OTP' | 'PUSH_OTP';
    export const AuthenticationTypeEnum = {
        SMSOTP: 'SMS_OTP' as AuthenticationTypeEnum,
        CHIPOTP: 'CHIP_OTP' as AuthenticationTypeEnum,
        PHOTOOTP: 'PHOTO_OTP' as AuthenticationTypeEnum,
        PUSHOTP: 'PUSH_OTP' as AuthenticationTypeEnum
    };
}
