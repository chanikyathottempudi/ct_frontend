package com.simats.finalapp.network;

import com.simats.finalapp.model.LoginRequest;
import com.simats.finalapp.model.LoginResponse;
import com.simats.finalapp.model.SignupRequest;
import com.simats.finalapp.model.SignupResponse;
import com.simats.finalapp.model.ResetPasswordRequest;
import com.simats.finalapp.model.VerificationRequest;
import com.simats.finalapp.model.VerificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("doctor/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("admincenter/signup/")
    Call<SignupResponse> signup(@Body SignupRequest signupRequest);

    @GET("patients/patients/")
    Call<java.util.List<com.simats.finalapp.model.PatientResponse>> getPatients();

    @POST("patients/patients/")
    Call<com.simats.finalapp.model.PatientResponse> registerPatient(@Body com.simats.finalapp.model.PatientRequest patientRequest);

    @POST("dosestats/scan-parameters/")
    Call<Void> saveScanParameters(@Body com.simats.finalapp.model.ScanParameterRequest scanParameterRequest);

    @POST("dosestats/scans/predict_dose/")
    Call<com.simats.finalapp.model.PredictDoseResponse> predictDose(@Body com.simats.finalapp.model.PredictDoseRequest predictDoseRequest);

    @POST("admincenter/send-verification/")
    Call<VerificationResponse> sendVerificationCode(@Body java.util.Map<String, String> body);

    @POST("admincenter/verify-code/")
    Call<VerificationResponse> verifyCode(@Body VerificationRequest verificationRequest);

    @POST("admincenter/final-reset-password/")
    Call<VerificationResponse> finalResetPassword(@Body ResetPasswordRequest resetPasswordRequest);
}
