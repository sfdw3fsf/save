import { FieldCategoryDTO } from '@/data/account/dto/field-category.dto';
import * as yup from 'yup';
import { yups } from './yups';

// step 2

export const projectRequestStep2Schema = yup.object().shape({
  name: yups.required.string('required.project_name'),
  advisoryType: yups.required.string('required.advisory_type'),
  selectedMeetings: yups.required.boolean('required.this_is_essential'),
  advisoryTime: yups.custom.numberNotNaN(),
  selectedBusinessFields: yups.required.boolean(),
});

export type ProjectRequestStep2Input = yup.InferType<typeof projectRequestStep2Schema>;

export const projectBusinessFieldSchema = (data: FieldCategoryDTO[]) => {
  return yups.custom.atLeastOnceSelect(data, 'businessField', 'businessFieldEtc');
};

export const projectAdvisoryFieldSchema = (data: FieldCategoryDTO[]) => {
  return yups.custom.atLeastOnceSelectInArray(data, 'advisoryField', 'advisoryFieldEtc');
};

export const projectMedicalFieldSchema = (data: FieldCategoryDTO[]) => {
  return yups.custom.atLeastOnceSelectInArray(data, 'medicalField', 'medicalFieldEtc');
};

// step 3

export const projectRequestStep3Schema = yup.object().shape({
  contentDetail: yups.required.string('required.advisory_content_detail'),
  attachment: yups.optional.file(10, [
    'application/pdf',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
    'application/msword',
  ]),
});

export type ProjectRequestStep3Input = yup.InferType<typeof projectRequestStep3Schema>;

// step 4

export const agreedTermsSchema = yup.object().shape({
  agreedTerm: yups.required.boolean('required.term_of_use'),
  agreedPolicy: yups.required.boolean('required.policy'),
});
export type ProjectRequestStep4Input = yup.InferType<typeof agreedTermsSchema>;

// filter
export const filterProjectSchema = yup.object().shape({
  status: yup.array().of(yup.string()).default([]),
  advisoryDate: yup.array().of(yup.date().nullable()).length(2).default([null, null]),
});
export type FilterProjectInput = yup.InferType<typeof filterProjectSchema>;

// review
export const feedbacksSchema = yup.object().shape({
  title: yup.string().optional().default(''),
  content: yup.string().optional().default(''),
});
export type FeedbacksInput = yup.InferType<typeof feedbacksSchema>;
