import * as yup from 'yup';
import { PATTERNS } from '../constant/form/form-pattern.constant';
import { SERVICE_TRANSPORT_ACCOMPANIED_STATUS } from '../enums/reservation.enum';

type FormValidation = {
  value: number;
  key?: string;
};

const regexTest = (regex: RegExp, value?: string) => {
  return !value?.length ? true : regex.test(value);
};

export const yups = {
  text: (requiredKey?: string, testKey?: string) =>
    yup
      .string()
      .default('')
      .trim()
      .required(requiredKey ?? 'required.default'),

  required: {
    string: (requiredKey?: string) =>
      yup
        .string()
        .default('')
        .trim()
        .required(requiredKey ?? 'required.default'),
    boolean: (requiredKey?: string) =>
      yup
        .boolean()
        .oneOf([true], requiredKey ?? 'required.default')
        .required(requiredKey ?? 'required.default'),
    arrayOfString: (requiredKey?: string) =>
      yup
        .array()
        .default([])
        .of(yup.string())
        .min(1, requiredKey ?? 'required.default')
        .required(requiredKey ?? 'required.default'),
    arrayOfTrue: (length: number, requiredKey?: string) =>
      yup
        .array()
        .of(yup.boolean())
        .default(Array(Math.max(0, length)).fill(false))
        .test('termsAndCondsTest', requiredKey ?? 'required.default', (value) => value && value.every((item) => item === true))
        .required(requiredKey ?? 'required.default'),
    number: (validations?: { min?: FormValidation; max?: FormValidation; requiredKey?: string }) => {
      let schema = yup
        .number()
        .required(validations?.requiredKey ?? '')
        .typeError(validations?.requiredKey ?? '');
      if (validations?.min) {
        schema = schema.min(validations.min.value, validations.min.key ?? 'test.default');
      }
      if (validations?.max) {
        schema = schema.max(validations.max.value, validations.max.key ?? 'test.default');
      }
      return schema;
    },
  },
  optional: {
    boolean: yup.boolean().default(false),
    string: yup.string().default('').trim(),
    amount: () => yup.number().optional().min(0, 'invalid.amount').typeError('invalid.amount'),
    file: (maxSizeInMb: number, formats: string[]) =>
      yup
        .mixed()
        .test('fileSizeTest', 'test.file_size', (value) => !value || (value && (value as File).size <= maxSizeInMb * 1024 * 1024))
        .test('fileFormatTest', 'test.file_format', (value) => !value || (value && formats.includes((value as File).type))),
  },
  custom: {
    otp: (requiredKey?: string, testKey?: string) =>
      yup
        .string()
        .default('')
        .trim()
        .test('otpRegexTest', testKey ?? 'test.default', (value) => regexTest(PATTERNS.OTP, value))
        .when('$isOptionalOtp', ([isOptionalOtp], schema) => {
          return isOptionalOtp ? schema : schema.required(requiredKey ?? 'required.default');
        }),
    email: (requiredKey?: string, testKey?: string) =>
      yup
        .string()
        .default('')
        .trim()
        .test('emailRegexTest', testKey ?? 'test.default', (value) => regexTest(PATTERNS.EMAIL, value))
        .when('$isOptionalEmail', ([isOptionalEmail], schema) => {
          return isOptionalEmail ? schema : schema.required(requiredKey ?? 'required.default');
        }),
    password: (requiredKey?: string, testKey?: string) =>
      yup
        .string()
        .default('')
        .trim()
        .test('passwordRegexTest', testKey ?? 'test.default', (value) => regexTest(PATTERNS.PASSWORD, value))
        .required(requiredKey ?? 'required.default'),
    matchPassword: (ref: string, testKey?: string, requiredKey?: string) =>
      yup
        .string()
        .default('')
        .trim()
        .required(requiredKey ?? 'required.default')
        .oneOf([yup.ref(ref)], testKey ?? 'test.default'),
    funeralService: (requiredKey?: string) =>
      yup.string().test('required-when-active', requiredKey || '', function (value) {
        const { isActive } = this.parent;
        if (isActive) {
          return (value ?? '') !== '';
        }
        return true;
      }),

    funeralServiceAccompany: (requiredKey?: string) =>
      yup
        .mixed<SERVICE_TRANSPORT_ACCOMPANIED_STATUS>()
        .oneOf(Object.values(SERVICE_TRANSPORT_ACCOMPANIED_STATUS), 'Invalid number of visitors.')
        .test('required-when-active', requiredKey ?? '', function (value) {
          const { isActive } = this.parent;
          if (isActive) {
            return value !== undefined && value !== null;
          }
          return true;
        }),
  },
};
