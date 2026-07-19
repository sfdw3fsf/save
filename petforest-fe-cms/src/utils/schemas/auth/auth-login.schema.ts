import * as yup from 'yup';
import { yups } from '../yups';

export const authLoginSchema = yup.object().shape({
  username: yups.text('require.username', 'require.username'),
  password: yups.text('require.password', 'require.password'),
});

export type authLoginSchemaType = yup.InferType<typeof authLoginSchema>;
