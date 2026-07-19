import * as yup from 'yup';
import { yups } from '../yups';

export const postSchema = yup.object().shape({
  link: yups.custom.link(),
});

export type postSchema = yup.InferType<typeof postSchema>;
