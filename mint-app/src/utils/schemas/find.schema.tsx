import * as yup from 'yup';
import { yups } from './yups';

// find password by phone number
export const findPasswordByPhoneNumberSchema = yup.object().shape({
  name: yups.required.string('required.find_pwd_by_phone_name'),
  phone: yups.required.string('required.find_pwd_by_phone_phone'),
  username: yups.required.string('required.find_pwd_by_phone_username'),
});
export type FindPasswordByPhoneNumberInput = yup.InferType<typeof findPasswordByPhoneNumberSchema>;

// find password by email
export const findPasswordByEmailSchema = yup.object().shape({
  name: yups.required.string('required.find_pwd_name'),
  email: yups.custom.email('required.find_pwd_email', 'test.email'),
  username: yups.required.string('required.find_pwd_username'),
});
export type FindPasswordByEmailInput = yup.InferType<typeof findPasswordByEmailSchema>;

// find id by phone number
export const findIdByPhoneNumberSchema = yup.object().shape({
  name: yups.required.string('required.find_id_by_phone_name'),
  phone: yups.required.string('required.find_id_by_phone_phone'),
});
export type FindIdByPhoneNumberInput = yup.InferType<typeof findIdByPhoneNumberSchema>;

// find id by email
export const findIdByEmailSchema = yup.object().shape({
  name: yups.required.string('required.find_id_by_email_name'),
  email: yups.custom.email('required.find_id_by_email_email', 'test.email'),
});
export type FindIdByEmailInput = yup.InferType<typeof findIdByEmailSchema>;

// otp verification
export const findVerificationSchema = yup.object().shape({
  otp: yups.custom.otp('required.otp', 'test.otp'),
});
export type FindVerificationInput = yup.InferType<typeof findVerificationSchema>;
