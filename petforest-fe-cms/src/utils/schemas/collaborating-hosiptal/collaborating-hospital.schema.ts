import * as yup from 'yup';
import { yups } from '../yups';

export const hospitalSchema = yup.object().shape({
  email: yups.custom.email(undefined, 'invalid.email'),
});

export type hpsitalSchemaType = yup.InferType<typeof hospitalSchema>;
