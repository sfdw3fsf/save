import * as yup from 'yup';
import { yups } from '../yups';

export const authChangePasswordSchema = yup.object().shape({
  oldPassword: yups.text('require.password', 'require.password'),
  newPassword: yups.text('require.password', 'require.password'),
});

export type authChangePasswordSchemaType = yup.InferType<typeof authChangePasswordSchema>;
