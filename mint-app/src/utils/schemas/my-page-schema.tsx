import * as yup from 'yup';
import { yups } from './yups';

export const NewEmailSchema = yup.object().shape({
  email: yups.custom.email('required.email', 'test.email'),
});

export const CurrentPasswordSchema = yup.object().shape({
  password: yups.custom.password('required.password', 'test.password'),
});
export const AddEducationSchema = yup.object().shape({
  name: yup.string().required(),
  detail: yup.string().required(),
});
export const AddExperienceSchema = yup.object().shape({
  name: yup.string().required('required.this_is_essential'),
  detail: yup.string().required('required.this_is_essential'),
});
export const MemberInformationSchema = yup.object().shape({
  companyName: yups.required.string('required.this_is_essential'),

  fullName: yups.required.string('required.this_is_essential'),

  personInCharge: yups.required.string('required.this_is_essential'),

  internalEmail: yups.custom.email('required.email', 'test.email'),
  businessFieldCategoryIds: yup.array().min(1, 'required.this_is_essential').required(),
  // businessFieldCategoryIds: yups.required.arrayOfString(),
  // departmentName: yups.optional.string,
  // attachments: yups.optional.file(5, ['application/pdf', 'image/jpeg', 'image/png']),
});
export const ExpertInformationSchema = yup.object().shape({
  name: yups.required.string('required.this_is_essential'),
  schoolFrom: yups.required.string('required.this_is_essential'),
  affiliation: yups.required.string('required.this_is_essential'),
  personalHistory: yups.required.numberYear({ min: { value: 0 }, max: { value: new Date().getFullYear() } }),
  department: yups.required.string('required.this_is_essential'),
  // personInCharge: yups.required.string('required.this_is_essential'),
  detailedField: yups.required.string('required.this_is_essential'),
});
export type MemberInformationInput = yup.InferType<typeof MemberInformationSchema>;
