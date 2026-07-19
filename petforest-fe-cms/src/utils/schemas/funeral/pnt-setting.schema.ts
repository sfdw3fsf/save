import * as yup from 'yup';
import { yups } from '../yups';

export const pntSettingInfoSchema = yup.object().shape({
  name: yups.custom.roomType(),
  reservationPhotoId: yups.required.number(),
  files: yups.required.fileIds(),
});

export type PntSettingInfoType = yup.InferType<typeof pntSettingInfoSchema>;
