import * as yup from 'yup';
import { yups } from './yups';

export const updatePasswordSchema = yup.object().shape({
  oldPassword: yups.custom.password('required.password', 'test.password'),
  newPassword: yups.custom.password('required.password', 'test.password'),
  confirmPassword: yups.custom.matchPassword('newPassword', 'test.match_password', 'required.match_password'),
});

export type UpdatePasswordInput = yup.InferType<typeof updatePasswordSchema>;
