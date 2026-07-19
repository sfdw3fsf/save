import { api } from '@/config/http';
import { initKeys } from '@/utils/utils/query-key.utils';

export const dashboardReceivablesKeys = initKeys('download-excel-reservation');

export const downloadExcelFileApi = async (url: string, params: any, startDate?: number, endDate?: number) => {
  const request = {
    ...params.filter,
    ids: params.ids,
    startDate: startDate,
    endDate: endDate,
  };
  return api.get<Blob>(url, {
    params: request,
    responseType: 'blob',
  });
};
