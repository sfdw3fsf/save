import * as yup from 'yup';
import { yups } from '../yups';

export const ossuarySchema = yup.object().shape({
  childName: yups.text('require.ossuary.child', 'require.ossuary.child'),
  guardianName: yups.text('require.ossuary.guardian', 'require.ossuary.guardian'),
});

export type ossuarySchema = yup.InferType<typeof ossuarySchema>;
