import * as yup from 'yup';
import { yups } from './yups';

export const changePasswordSchema = yup.object().shape({
  newPassword: yups.custom.password('required.password', 'test.password'),
  confirmPassword: yups.custom.matchPassword('newPassword', 'test.match_password', 'required.new_password'),
});

export type ChangePasswordInput = yup.InferType<typeof changePasswordSchema>;
