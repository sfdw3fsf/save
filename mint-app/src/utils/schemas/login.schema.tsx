import * as yup from 'yup';
import { yups } from './yups';

export const loginSchema = yup.object().shape({
  username: yups.required.string('required.login_username'),
  password: yups.required.string('required.login_password'),
  saveId: yups.optional.boolean,
});

export type LoginInput = yup.InferType<typeof loginSchema>;
