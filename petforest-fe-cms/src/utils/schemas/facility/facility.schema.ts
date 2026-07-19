import * as yup from 'yup';
import { yups } from '../yups';

export const facilitySchema = yup.object().shape({
  email: yups.custom.email(undefined, 'invalid.email'),
});

export type facilitySchema = yup.InferType<typeof facilitySchema>;
