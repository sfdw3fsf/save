import { FormMessageTranslateKey } from '@/components/common/form/FormMessage';
import { Dayjs } from 'dayjs';
import * as yup from 'yup';
import { PATTERNS } from '../constants/form/form-pattern.constant';
import { ROOM_TYPE } from '../enums/funeral/funeral-mngt.enums';

import { DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS } from '../enums/funeral/reservation-mngt.enums';
import { ENUM_STATUS } from '../enums/status.enum';

type FormValidation = {
  value: number;
  key?: FormMessageTranslateKey;
};

const regexTest = (regex: RegExp, value?: string) => {
  return !value?.length ? true : regex.test(value);
};

export const yups = {
  text: (requiredKey?: FormMessageTranslateKey, testKey?: FormMessageTranslateKey) =>
    yup
      .string()
      .default('')
      .trim()
      .required(requiredKey ?? 'require.default'),

  required: {
    string: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .trim()
        .required(requiredKey ?? 'require.default'),
    status: yup
      .string()
      .default(ENUM_STATUS.INCOMPLETE)
      .oneOf(Object.keys(ENUM_STATUS), 'test.default')
      .trim()
      .required('require.default'),
    boolean: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .boolean()
        .oneOf([true], requiredKey ?? 'require.default')
        .required(requiredKey ?? 'require.default'),
    arrayOfString: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .array()
        .default([])
        .of(yup.string())
        .min(1, requiredKey ?? 'require.default')
        .required(requiredKey ?? 'require.default'),
    arrayOfTrue: (length: number, requiredKey?: FormMessageTranslateKey) =>
      yup
        .array()
        .of(yup.boolean())
        .default(Array(Math.max(0, length)).fill(false))
        .test('termsAndCondsTest', requiredKey ?? 'require.default', (value) => value && value.every((item) => item === true))
        .required(requiredKey ?? 'require.default'),
    number: (validations?: { min?: FormValidation; max?: FormValidation; requiredKey?: FormMessageTranslateKey }) => {
      let schema = yup
        .number()
        .required(validations?.requiredKey ?? 'require.default')
        .typeError('test.default');
      if (validations?.min) {
        schema = schema.min(validations.min.value, validations.min.key ?? 'test.default');
      }
      if (validations?.max) {
        schema = schema.max(validations.max.value, validations.max.key ?? 'test.default');
      }
      return schema;
    },
    fileIds: () =>
      yup
        .array()
        .of(yup.number())
        .min(0, 'test.reservation_photo_ids')
        .required('require.default')
        .default([] as number[]),
  },
  optional: {
    boolean: yup.boolean().default(false),
    date: yup.mixed<Dayjs>().optional(),
    string: yup.string().default('').trim(),
    amount: () => yup.number().optional().min(0, 'invalid.amount').typeError('invalid.amount'),
    file: (maxSizeInMb: number, formats: string[]) =>
      yup
        .mixed()
        .test('fileSizeTest', 'test.file_size', (value) => !value || (value && (value as File).size <= maxSizeInMb * 1024 * 1024))
        .test('fileFormatTest', 'test.file_format', (value) => !value || (value && formats.includes((value as File).type))),
  },
  custom: {
    otp: (requiredKey?: FormMessageTranslateKey, testKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .trim()
        .test('otpRegexTest', testKey ?? 'test.default', (value) => regexTest(PATTERNS.OTP, value))
        .when('$isOptionalOtp', ([isOptionalOtp], schema) => {
          return isOptionalOtp ? schema : schema.required(requiredKey ?? 'require.default');
        }),
    email: (requiredKey?: FormMessageTranslateKey, testKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .trim()
        .test('emailRegexTest', testKey ?? 'test.default', (value) => regexTest(PATTERNS.EMAIL, value))
        .when('$isOptionalEmail', ([isOptionalEmail], schema) => {
          return isOptionalEmail ? schema : schema.required(requiredKey ?? 'require.default');
        }),
    password: (requiredKey?: FormMessageTranslateKey, testKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .trim()
        .test('passwordRegexTest', testKey ?? 'test.default', (value) => regexTest(PATTERNS.PASSWORD, value))
        .required(requiredKey ?? 'require.default'),
    roomType: () =>
      yup.string().oneOf(Object.keys(ROOM_TYPE), 'test.default').default(ROOM_TYPE.NONE).required('require.default'),
    matchPassword: (ref: string, testKey?: FormMessageTranslateKey, requiredKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .trim()
        .required(requiredKey ?? 'require.default')
        .oneOf([yup.ref(ref)], testKey ?? 'test.default'),
    suppliesKind: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .array(
          yup.object().shape({
            kind: yup.number().required(requiredKey ?? 'required.default'),
          }),
        )
        .test(
          'all-items-have-kind',
          requiredKey ?? 'require.supplyMaterial',
          (supplies) => supplies?.every((supply) => supply.kind !== null && supply.kind !== undefined) || false,
        )
        .required(requiredKey ?? 'required.default'),
    roomNumber: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .number()
        .nullable()
        .test('roomNumber-required-when-active', requiredKey ?? 'require.room_number', function (value) {
          const { isActive } = this.parent;
          if (isActive) {
            return value !== undefined && value !== null;
          }
          return true;
        }),
    numberPeople: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .mixed()
        .nullable()
        .test('numberPeople-required-when-accompanied', requiredKey ?? 'require.number_people', function (value) {
          const { accompanimentStatus } = this.parent;
          const isActiveTransport = this.from?.[1]?.value?.isActiveTransport;
          if (!isActiveTransport) return true;

          if (accompanimentStatus === DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS.ACCOMPANY) {
            return value !== undefined && value !== null && value !== '';
          }
          return true;
        }),
    transportTime: (requiredKey?: FormMessageTranslateKey) =>
      yup.mixed().test('transportTime-required-when-not-accompanied', requiredKey ?? 'require.transport_time', function (value) {
        const { accompanimentStatus } = this.parent;
        const isActiveTransport = this.from?.[1]?.value?.isActiveTransport;
        if (!isActiveTransport) return true;
        if (accompanimentStatus === DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS.NOT_ACCOMPANY) {
          return value !== undefined && value !== null && value !== '';
        }
        return true;
      }),

    deliveryServiceAddress: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .test(
          'deliveryServiceAddress-required-when-not-accompanied',
          requiredKey ?? 'require.delivery_service_address',
          function (value) {
            const { accompanimentStatus } = this.parent;
            const isActiveTransport = this.from?.[1]?.value?.isActiveTransport;
            if (!isActiveTransport) return true;
            if (accompanimentStatus === DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS.NOT_ACCOMPANY) {
              return value !== undefined && value !== null && value !== '';
            }
            return true;
          },
        ),
    link: (requiredKey?: string, testKey?: string) =>
      yup
        .string()
        .default('')
        .trim()
        .test('isValidLink', testKey ?? 'invalid.url', (value) => {
          if (!value) return true;
          try {
            const url = new URL(value);
            return url.protocol === 'http:' || url.protocol === 'https:';
          } catch {
            return false;
          }
        })
        .required(requiredKey ?? 'require.default'),
  },
};
