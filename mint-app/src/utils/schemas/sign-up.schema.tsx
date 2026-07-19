import { FieldCategoryDTO } from '@/data/account/dto/field-category.dto';
import * as yup from 'yup';
import { yups } from './yups';

// step 1
export const signUpStep1Schema = yup.object().shape({
  phoneNumber: yups.required.string('required.phoneNumber'),
  email: yups.custom.email('required.email', 'test.email'),
  otp: yups.custom.otp(),
});
export type SignUpStep1Input = yup.InferType<typeof signUpStep1Schema>;

// step 2
export const signUpStep2Schema = yup.object().shape({
  username: yups.required.string('required.id'),
  password: yups.custom.password('required.password', 'test.password'),
  confirmPassword: yups.custom.matchPassword('password', 'test.match_password', 'required.match_password'),
});
export type SignUpStep2Input = yup.InferType<typeof signUpStep2Schema>;

// step 3
export const signUpIndivStep3Schema = yup.object().shape({
  companyName: yups.required.string(),
  fullName: yups.required.string(),
  personInCharge: yups.required.string(),
  internalEmail: yups.custom.email(),
  businessFieldCategoryIds: yups.required.arrayOfString(),
  attachments: yups.optional.file(5, ['application/pdf', 'image/jpeg', 'image/png']),
  departmentName: yups.optional.string,
  isAgreed: yups.required.arrayOfTrue(2),
});
export type SignUpIndivStep3Input = yup.InferType<typeof signUpIndivStep3Schema>;

export const signUpExpertStep3Schema = yup.object().shape({
  fullName: yups.required.string(),
  affiliation: yups.required.string(),
  personalHistory: yups.required.number({ min: { value: 0 }, max: { value: new Date().getFullYear() } }),
  contact: yups.required.string(),
  schoolFrom: yups.required.string(),
  department: yups.required.string(),
  detailedField: yups.required.string(),
  isAgreed: yups.required.arrayOfTrue(2),
  attachments: yups.optional.file(10, [
    'application/pdf',
    'application/msword',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
  ]),
  selectedBusinessFields: yups.required.boolean(),
});
export type SignUpExpertStep3Input = yup.InferType<typeof signUpExpertStep3Schema>;

export const businessFieldSchema = (data: FieldCategoryDTO[]) => {
  let schema: { [key: string]: yup.AnySchema } = {};

  data.forEach((item) => {
    const validIds = item.fields.map((i) => i.id);

    const fieldName = `field${item.id}`,
      arrayFieldName = `arrayField${item.id}`;

    schema = {
      ...schema,
      [fieldName]: yup.array().of(yup.number().oneOf(validIds, 'test.default')).default([]),
      [arrayFieldName]: yup.array().of(yup.number().oneOf(validIds, 'test.default')).default([]),
    };

    item.fields.forEach((i) => {
      if (!i.isOtherField) return;

      const fieldOtherName = `field_${item.id}_${i.id}`,
        arrayFieldOtherName = `arrayField_${item.id}_${i.id}`;

      schema = {
        ...schema,
        [fieldOtherName]: yups.custom.otherOption(fieldName, i.id),
        [arrayFieldOtherName]: yups.custom.otherOption(arrayFieldName, i.id),
      };
    });
  });

  return yup.object().shape(schema);
};

export const advisoryFieldSchema = (data: FieldCategoryDTO[]) => {
  let schema: { [key: string]: yup.AnySchema } = {};

  data.forEach((item) => {
    const validIds = item.fields.map((i) => i.id);

    const advisoryFieldName = `advisoryField${item.id}`;

    schema = {
      ...schema,
      [advisoryFieldName]: yup.array().of(yup.number().oneOf(validIds, 'test.default')).default([]),
    };

    item.fields.forEach((i) => {
      if (!i.isOtherField) return;

      const advisoryFieldOtherName = `advisoryField_${item.id}_${i.id}`;

      schema = {
        ...schema,
        [advisoryFieldOtherName]: yups.custom.otherOption(advisoryFieldName, i.id),
      };
    });
  });

  return yup.object().shape(schema);
};
