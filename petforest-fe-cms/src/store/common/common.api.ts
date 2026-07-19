import { api } from '@/config/http';
import { ArrangeOrdinal } from '@/utils/types/arrange-ordinal.type';

export const arrangeOrdinalApi = async (url: string, body: ArrangeOrdinal) => {
  return await api.patch(`${url}/re-ordinal`, body);
};
