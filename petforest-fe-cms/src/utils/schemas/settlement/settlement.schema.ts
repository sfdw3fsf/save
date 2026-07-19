import * as yup from 'yup';
import { yups } from '../yups';

export const settlementMethodSchema = yup.object().shape({
  card: yups.optional.amount(),
  cash: yups.optional.amount(),
  accountAmount: yups.optional.amount(),
});

export type SettlementMethodType = yup.InferType<typeof settlementMethodSchema>;
