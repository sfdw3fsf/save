import * as yup from 'yup';
import { yups } from '../yups';

export const lucetteInfoSchema = yup.object().shape({
  tycheDeliveryDate: yups.optional.date,
  petForestIndiaDate: yups.optional.date,
  jewelry: yups.optional.string,
  deliveryStatus: yups.required.status,
  funeralAddress: yups.optional.string,
  detailAddress: yups.optional.string,
});

export type LucetteInfoType = yup.InferType<typeof lucetteInfoSchema>;
