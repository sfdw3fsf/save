import { FormMessageTranslateKey } from '@/components/core/form/form-message/FormMessage';
import { FieldCategoryDTO } from '@/data/account/dto/field-category.dto';
import * as yup from 'yup';
import { PATTERNS } from '../constants/form.constant';

type FormValidation = {
  value: number;
  key?: FormMessageTranslateKey;
};

const regexTest = (regex: RegExp, value?: string) => {
  return !value?.length ? true : regex.test(value);
};

export const yups = {
  required: {
    string: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .trim()
        .required(requiredKey ?? 'required.default'),
    stringNotTrim: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .required(requiredKey ?? 'required.default'),
    boolean: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .boolean()
        .default(false)
        .oneOf([true], requiredKey ?? 'required.default')
        .required(requiredKey ?? 'required.default'),
    arrayOfString: (requiredKey?: FormMessageTranslateKey) =>
      yup
        .array()
        .default([])
        .of(yup.string())
        .min(1, requiredKey ?? 'required.default')
        .required(requiredKey ?? 'required.default'),
    arrayOfTrue: (length: number, requiredKey?: FormMessageTranslateKey) =>
      yup
        .array()
        .of(yup.boolean())
        .default(Array(Math.max(0, length)).fill(false))
        .test('termsAndCondsTest', requiredKey ?? 'required.default', (value) => value && value.every((item) => item === true))
        .required(requiredKey ?? 'required.default'),
    number: (validations?: { min?: FormValidation; max?: FormValidation; requiredKey?: FormMessageTranslateKey }) => {
      let schema = yup
        .number()
        .required(validations?.requiredKey ?? 'required.default')
        .typeError('test.default');
      if (validations?.min) {
        schema = schema.min(validations.min.value, validations.min.key ?? 'test.default');
      }
      if (validations?.max) {
        schema = schema.max(validations.max.value, validations.max.key ?? 'test.default');
      }
      return schema;
    },
    numberYear: (validations?: { min?: FormValidation; max?: FormValidation; requiredKey?: FormMessageTranslateKey }) => {
      let schema = yup
        .number()
        .required(validations?.requiredKey ?? 'required.default')
        .typeError('test.numberYear');
      if (validations?.min) {
        schema = schema.min(validations.min.value, validations.min.key ?? 'test.default');
      }
      if (validations?.max) {
        schema = schema.max(validations.max.value, validations.max.key ?? 'test.default');
      }
      return schema;
    },
    file: (maxSizeInMb: number, formats: string[], requiredKey?: FormMessageTranslateKey) =>
      yup
        .mixed()
        .required(requiredKey ?? 'required.default')
        .test('fileSizeTest', 'test.file_size', (value) => !value || (value && (value as File).size <= maxSizeInMb * 1024 * 1024))
        .test('fileFormatTest', 'test.file_format', (value) => !value || (value && formats.includes((value as File).type))),
  },
  optional: {
    boolean: yup.boolean().default(false),
    string: yup.string().default('').trim(),
    number: yup.number(),
    file: (maxSizeInMb: number, formats: string[]) =>
      yup
        .mixed()
        .test('fileSizeTest', 'test.file_size', (value) => !value || (value && (value as File).size <= maxSizeInMb * 1024 * 1024))
        .test('fileFormatTest', 'test.file_format', (value) => !value || (value && formats.includes((value as File).type))),
    multipleFiles: (maxSizeInMb: number, formats: string[], maxFiles: number) =>
      yup
        .array()
        .of(
          yup
            .mixed()
            .test('fileSizeTest', 'test.file_size', (value) => !value || (value as File).size <= maxSizeInMb * 1024 * 1024)
            .test('fileFormatTest', 'test.file_format', (value) => !value || formats.includes((value as File).type)),
        )
        .test('maxFilesTest', 'test.default', (value) => !value || value.length <= maxFiles),
  },
  custom: {
    otp: (requiredKey?: FormMessageTranslateKey, testKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .trim()
        .test('otpRegexTest', testKey ?? 'test.default', (value) => regexTest(PATTERNS.OTP, value))
        .when('$isOptionalOtp', ([isOptionalOtp], schema) => {
          return isOptionalOtp ? schema : schema.required(requiredKey ?? 'required.default');
        }),
    email: (requiredKey?: FormMessageTranslateKey, testKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .trim()
        .test('emailRegexTest', testKey ?? 'test.default', (value) => regexTest(PATTERNS.EMAIL, value))
        .required(requiredKey ?? 'required.default'),
    password: (requiredKey?: FormMessageTranslateKey, testKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .test('passwordRegexTest', testKey ?? 'test.default', (value) => regexTest(PATTERNS.PASSWORD, value))
        .required(requiredKey ?? 'required.default'),
    matchPassword: (ref: string, testKey?: FormMessageTranslateKey, requiredKey?: FormMessageTranslateKey) =>
      yup
        .string()
        .default('')
        .required(requiredKey ?? 'required.default')
        .oneOf([yup.ref(ref)], testKey ?? 'test.default'),
    otherOption: (refName: string, id: number | string) =>
      yup.string().test('required', 'required.default', (value, context) => {
        const refValues = context.parent[refName] ?? [];
        if (!refValues.includes(id)) return true;
        return !!value;
      }),
    atLeastOnceSelect: (data: FieldCategoryDTO[], fieldName: string, otherFieldName: string) => {
      const schema = data.reduce((acc, item) => {
        const validIds = item.fields.map((i) => i.id);
        const arrayFieldName = `${fieldName}${item.id}`;
        const etcFieldName = `${otherFieldName}${item.id}`; // For "etc" field
        return {
          ...acc,
          [arrayFieldName]: yup.number().oneOf(validIds, 'test.default'),
          [etcFieldName]: yup.string().when(arrayFieldName, {
            is: (val: number) => val < 0,
            then: (schema) => schema.required('required.etc'),
            otherwise: (schema) => schema.optional(),
          }),
        };
      }, {});

      return yup
        .object()
        .shape(schema)
        .test({
          name: 'oneSelected',
          message: 'required.default',
          test: function (value: Record<string, number>) {
            const businessFieldKeys = data.map((item) => `${fieldName}${item.id}`);
            const filledFields = businessFieldKeys.filter((key) => value[key] !== undefined && value[key] !== null);
            return filledFields.length === 1; // Ensure only one has a value
          },
        });
    },
    atLeastOnceSelectInArray: (data: FieldCategoryDTO[], fieldName: string, otherFieldName: string) => {
      const schema = data.reduce((acc, item) => {
        const validIds = item.fields.map((i) => i.id);
        const arrayFieldName = `${fieldName}${item.id}`;
        const etcFieldName = `${otherFieldName}${item.id}`;
        return {
          ...acc,
          [arrayFieldName]: yup.array().of(yup.number().oneOf(validIds, 'test.default')).default([]),
          [etcFieldName]: yup.string().when(arrayFieldName, {
            is: (val: number[]) => val?.some((v) => v < 0), // Check if value is less than 0
            then: (schema) => schema.required('required.etc'),
            otherwise: (schema) => schema.optional(),
          }),
        };
      }, {});
      return yup
        .object()
        .shape(schema)
        .test({
          name: 'oneSelected',
          message: 'required.default',
          test: function (value: Record<string, number[]>) {
            const fieldKeys = data.map((item) => `${fieldName}${item.id}`); // Map field names
            const filledFields = fieldKeys.filter(
              (key) => value[key] !== undefined && value[key] !== null && value[key]?.length !== 0,
            );
            return filledFields.length >= 1; // Ensure at least one field is selected
          },
        });
    },
    numberNotNaN: () =>
      yup
        .number()
        .typeError('test.advisory_time')
        .nullable()
        .transform((value, originalValue) => (originalValue === '' ? null : value))
        .min(0, 'test.default'),
  },
};
